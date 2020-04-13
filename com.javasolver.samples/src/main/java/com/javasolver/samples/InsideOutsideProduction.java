package com.javasolver.samples;

/*
A manufacturer has to decide how much of each demanded product should be produced
internally and how much should be bought and resold. The company wants to minimize the
overall production cost of meeting the demand. <br>
Let's assume the company wants to produce 3 products that may consume 2 resources in the
company's factories. <br>
Here the problem data:</p>
<table border="1" width="100%" bordercolor="#000000">
<tr>
<td width="16%" align="center">Products</td>
<td width="16%" align="center">Demand</td>
<td width="17%" align="center">Inside Cost</td>
<td width="17%" align="center">Outside Cost</td>
<td width="17%" align="center">Resource-1 Consumption</td>
<td width="17%" align="center">Resource-2 Consumption</td>
</tr>
<tr>
<td width="16%" align="center">product-1</td>
<td width="16%" align="center">100</td>
<td width="17%" align="center">0.6</td>
<td width="17%" align="center">0.8</td>
<td width="17%" align="center">0.5</td>
<td width="17%" align="center">0.2</td>
</tr>
<tr>
<td width="16%" align="center">product-2</td>
<td width="16%" align="center">200</td>
<td width="17%" align="center">0.8</td>
<td width="17%" align="center">0.9</td>
<td width="17%" align="center">0.4</td>
<td width="17%" align="center">0.4</td>
</tr>
<tr>
<td width="16%" align="center">product-3</td>
<td width="16%" align="center">300</td>
<td width="17%" align="center">0.3</td>
<td width="17%" align="center">0.4</td>
<td width="17%" align="center">0.3</td>
<td width="17%" align="center">0.6</td>
</tr>
</table>
<p>Demand - customers' demand in units of each product. </p>
<p>Inside cost - cost of the product unit produced in the company's factories.</p>
<p>Outside cost - cost of the product unit bought from another company.</p>
<p>Products produced by inside production have to satisfy resource constraints; each
product consumes a certain amount of each resource.� The company manager must take
into account the capacity constraints:� resource-1 has capacity 20; resource-2 has
capacity 40.</p>
*/

import javax.constraints.Objective;
import javax.constraints.Problem;
import javax.constraints.ProblemFactory;
import javax.constraints.Solution;
import javax.constraints.Solver;
import javax.constraints.VarReal;

import com.javasolver.JavaSolver;

public class InsideOutsideProduction extends JavaSolver {
	
	public void define() {

			//products nomenclature
		    String[] products = {"P1", "P2", "P3"};
			//resources nomenclature
			String[] resources = {"R1", "R2"};
//			int KLUSKI=0, CAPPELINI = 1, FETTUCINI = 2;
//			int FLOUR = 0, EGGS = 1;
			//consumption matrix: consumption[i][j] is a consumption of resources[i] for products[j]
			double[][] consumption = {
					{0.5, 0.4, 0.3},
			        {0.2, 0.4, 0.6}
			};
			// insideCost[i] is a production cost of product[i]
			double[] insideCosts = {0.6, 0.8, 0.3};
			// outsideCost[i] is a cost of unit of product[i] being produced outside the company
			double[] outsideCosts = {0.8, 0.9, 0.4};
			// customers demands for product[i]
			double[] demand = {100, 200, 300};

			//amount of resources available
			int[] capacity = {20, 40};
			
			VarReal[] insideVars = new VarReal[] {
					csp.variableReal(products[0] + "Inside", 0, demand[0]),
					csp.variableReal(products[1] + "Inside", 0, demand[1]),
					csp.variableReal(products[2] + "Inside", 0, demand[2])
			};

			VarReal[] outsideVars = new VarReal[] {
					csp.variableReal(products[0] + "Outside", 0, demand[0]),
					csp.variableReal(products[1] + "Outside", 0, demand[1]),
					csp.variableReal(products[2] + "Outside", 0, demand[2])
			};

			//an objective function - the total cost of production
			VarReal insideCost = csp.scalProd(insideCosts, insideVars); 
			insideCost.setName("inCost");
		    csp.add(insideCost);
		    VarReal outsideCost = csp.scalProd(outsideCosts, outsideVars);
			outsideCost.setName("outCost");
		    csp.add(outsideCost);
		    VarReal totalCost = csp.sum(insideCost,outsideCost);
		    totalCost.setName("TotalCost");
		    csp.add(totalCost); 

		    //capacity constraints
			for (int r=0; r < resources.length; r++){
				//VarReal resourceConsumption = csp.scalProd(consumption[r], insideVars);
				//csp.post(resourceConsumption,"<=",capacity[r]);
			    csp.post(consumption[r],insideVars,"<=",capacity[r]);
			}

			//meeting customers demand constraints
			for (int d = 0; d < products.length; d++){
				VarReal sum = csp.sum(insideVars[d],outsideVars[d]);
				sum.setName("demand"+(d+1));
				csp.add(sum);
			    csp.post(sum,">=",demand[d]); 
			}
			
			setObjectiveReal(totalCost);

	}

	public static void main(String[] args) {
		InsideOutsideProduction problem = new InsideOutsideProduction();
		problem.define();
		problem.minimize();
	}
}