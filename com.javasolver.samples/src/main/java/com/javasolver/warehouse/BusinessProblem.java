package com.javasolver.warehouse;

/**
 * Let's assume that a company plans to create a network of warehouses to supply
 * its existing stores. Let's suppose that the company already has a
 * number of suitable sites for building warehouses and thus it wants to know
 * whether or not to create a warehouse on each such site. 
 * 
 * For each site chosen, the company wants to determine the optimal capacity for 
 * the warehouse. The company considers the average merchandise turnover as 
 * identical from one store to another. However, the distance among locations 
 * and the transportation infrastructure both lead to varying transportation happinessVars 
 * for each pair consisting of a store and a warehouse.
 * 
 * The objective is to minimize total cost by determining for each warehouse
 * which stores should be suppled by it and what its capacity should be. The
 * total cost is the sum of supply happinessVars plus the happinessVars of building each
 * warehouse.
 */

public class BusinessProblem {
	
	String[] suppliers = {"Supplier-1","Supplier-2","Supplier-3","Supplier-4","Supplier-5"};
	
	int numberOfStores = 10;
	
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
	
	public String[] getSuppliers() {
		return suppliers;
	}
	
	public int getNumberOfSuppliers() {
		return getSuppliers().length;
	}
	
	public int getNumberOfStores() {
		return 10;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
