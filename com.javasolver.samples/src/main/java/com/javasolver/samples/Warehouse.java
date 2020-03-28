package com.javasolver.samples;


import javax.constraints.*;

import com.javasolver.JavaSolver;

/**
 * Let's assume that a company plans to create a network of warehouses to supply
 * its existing stores. Let's suppose that the company already has a
 * number of suitable sites for building warehouses and thus it wants to know
 * whether or not to create a warehouse on each such site. 
 * 
 * For each site chosen, the company wants to determine the optimal capacity for 
 * the warehouse. The company considers the average merchandise turnover as 
 * identical from one store to another. However, the distance among locations 
 * and the transportation infrastructure both lead to varying transportation costs 
 * for each pair consisting of a store and a warehouse.
 * 
 * The objective is to minimize total cost by determining for each warehouse
 * which stores should be suppled by it and what its capacity should be. The
 * total cost is the sum of supply costs plus the costs of building each
 * warehouse.
 */

public class Warehouse extends JavaSolver {
	
	// Test data
	int nbStores = 10;
	int nbSuppliers = 5;
	int buildingCost = 30;
	int[] capacities = new int[] { 1, 4, 2, 1, 3 };
	int[][] costMatrix = new int[][] { 
		{ 20, 24, 11, 25, 30 },
		{ 28, 27, 82, 83, 74 }, 
		{ 74, 97, 71, 96, 70 },
		{ 2, 55, 73, 69, 61 }, 
		{ 46, 96, 59, 83, 4 },
		{ 42, 22, 29, 67, 59 }, 
		{ 1, 5, 73, 59, 56 },
		{ 10, 73, 13, 43, 96 }, 
		{ 93, 35, 63, 85, 46 },
		{ 47, 65, 55, 71, 95 } 
	};
			
	Var[] suppliers;
	Var[] open;
	Var[] costs;
	Var totalCost;

	public void define() {
		try {
			suppliers = csp.variableArray("supplier",0, nbSuppliers - 1, nbStores);
			open = csp.variableArray("open",0, 1, nbSuppliers);
			costs = new Var[nbStores];
			int maxCost = 0;
			int maxSumCost = 0;
			for (int i = 0; i < nbStores; i++) {
				for (int j = 0; j < costMatrix[i].length; j++) {
					if (maxCost < costMatrix[i][j])
						maxCost = costMatrix[i][j];
				}
				costs[i] = csp.variable("cost-"+i,0,maxCost);
				csp.postElement(costMatrix[i], suppliers[i], "=", costs[i]);
				csp.postElement(open, suppliers[i], "=", 1);
				maxSumCost += maxCost;
			}
			// cardinality constraint
			for (int j = 0; j < nbSuppliers; j++) {
				csp.postCardinality(suppliers, j, "<=", capacities[j]);
			}
			// totalCost= sum(cost) + sum(open)*buildCost
			Var sumCost = csp.sum(costs);
			sumCost.setName("sumCost");
			csp.post(sumCost,"<=",maxSumCost);
			Var sumOpen = csp.sum(open);
			sumOpen.setName("sumOpen");
			csp.post(sumOpen,"<=",nbSuppliers);
			totalCost = sumOpen.multiply(buildingCost).plus(sumCost);
			totalCost.setName("TotalCost");
			csp.add(totalCost);
			setObjective(totalCost);

		} catch (Exception e) {
			csp.log("Error in prolem definition: " + e);
			throw new RuntimeException("Cannot create a problem");
		}
	}

	public void saveSolution(Solution solution) {
		solution.log();

		StringBuffer str = new StringBuffer();
		str.append("suppliers: ");
		for (int i = 0; i < suppliers.length; i++){
			int value = solution.getValue("supplier-"+i);
			str.append(value + " ");
		}
		csp.log(str.toString());

		str = new StringBuffer();
		str.append("costs: ");
		for (int i = 0; i < costs.length; i++) {
			int value = solution.getValue("cost-"+i);
			str.append(value + " ");
		}
		csp.log(str.toString());

		str = new StringBuffer();
		str.append("open: ");
		for (int i = 0; i < open.length; i++) {
			int value = solution.getValue("open-"+i);
			str.append(value + " ");
		}
		csp.log(str.toString());

		csp.log("TotalCost: " +solution.getValue("TotalCost"));
	}

	public static void main(String[] args) {

		long startMS = System.currentTimeMillis();

		Warehouse wh = new Warehouse();
		wh.define();
		wh.minimize();

		long finishMS = System.currentTimeMillis();
		System.out.println("Runtime: " + (finishMS - startMS) + " Millis");

	}

}
