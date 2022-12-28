package com.javasolver.samples;

import java.util.Arrays;
import java.util.Collections;

import javax.constraints.*;

import org.apache.logging.log4j.core.impl.ThreadContextDataInjector.ForCopyOnWriteThreadContextMap;

import com.javasolver.JavaSolver;

/*
https://dmcommunity.org/challenge-jan-2023/
This model defines a set of people, a set of giftVars, and the HAPPINESS level and cost of each gift.
The objective is to maximize the total HAPPINESS, subject to the budget constraint 
that the total cost of the giftVars must be less than or equal to the budget, 
and the constraint that each person can only receive one gift.

Here is a sample of test data:

PEOPLE:  “Alice”, “Bob”, “Carol”, “Dave”, “Eve”
GIFTS: “Book”, “Toy”, “Chocolate”, “Wine”, “Flowers”
GIFT COSTS: 10, 20, 5, 15, 7
HAPPINESS:
  “Book”: [3, 2, 5, 1, 4]
  “Toy”: [5, 2, 4, 3, 1]
  “Chocolate”: [1, 3, 4, 5, 2]
  “Wine”: [2, 5, 3, 4, 1]
  “Flowers”: [4, 3, 1, 2, 5]
BUDGET: 50
 */

public class Christmas extends JavaSolver {

	// Data
	String[] PEOPLE = { "Alice", "Bob", "Carol", "Dave", "Eve" };
	String[] GIFTS = { "Book", "Toy", "Chocolate", "Wine", "Flowers" };
	int[] COSTS = { 10, 20, 5, 15, 7 };

	int nbPeople = PEOPLE.length;
	int nbGifts = GIFTS.length;
	int budget = 50;
	int minCost = COSTS[0];
	int maxCost = COSTS[0];
	
	int[][] HAPPINESS = new int[][] { 
		{ 3, 2, 5, 1, 4 }, 
		{ 5, 2, 4, 3, 1 }, 
		{ 1, 3, 4, 5, 2 }, 
		{ 2, 5, 3, 4, 1 },
		{ 4, 3, 1, 2, 5 } 
	};

	Var[] giftVars;
	Var[] costVars;
	Var[] happinessVars;

	public void define() {
		try {
			giftVars = csp.variableArray("gift",0, nbGifts - 1, nbPeople);
			for (int cost : COSTS){
				if (cost > maxCost)
					maxCost = cost;
				if (cost < minCost)
					minCost = cost;
			}
			costVars = csp.variableArray("cost",minCost, maxCost, nbGifts);

			// Define HAPPINESS vars
			happinessVars = new Var[nbPeople];
			int maxHappiness = 0;
			int maxSumHappiness = 0;
			
			for (int i = 0; i < nbPeople; i++) {
				csp.log(giftVars[i].toString());
				for (int j = 0; j < HAPPINESS[i].length; j++) {
					if (maxHappiness < HAPPINESS[i][j])
						maxHappiness = HAPPINESS[i][j];
				}
				happinessVars[i] = csp.variable("HAPPINESS-"+i,0,maxHappiness);
				csp.add(happinessVars[i]);
				csp.postElement(HAPPINESS[i], giftVars[i], "=", happinessVars[i]);
				csp.log(happinessVars[i].toString());
				csp.postElement(COSTS, giftVars[i], "=", costVars[i]);
				csp.log(costVars[i].toString());
				maxSumHappiness += maxHappiness;
			}
			
			// budget constraint
			Var totalCost = csp.sum(costVars);
			totalCost.setName("Total Cost");
			csp.add(totalCost);
			csp.post(totalCost, "<=", budget);
			csp.log(totalCost.toString());
			
			// Define objective
			Var totalHappinessVar = csp.sum(happinessVars);
			totalHappinessVar.setName("Total Happiness");
			csp.add(totalHappinessVar);
			setObjective(totalHappinessVar);
			csp.log(totalHappinessVar.toString());
		} catch (Exception e) {
			csp.log("Error in prolem definition: " + e);
			throw new RuntimeException("Cannot create a problem");
		}
	}

	public void saveSolution(Solution solution) {
		solution.log();

		StringBuffer str = new StringBuffer();
		str.append("giftVars: ");
		for (int i = 0; i < giftVars.length; i++) {
			int value = solution.getValue("gift-" + i);
			str.append(value + " ");
			csp.log(PEOPLE[i] + " received " + GIFTS[value] );
		}
		csp.log(str.toString());

		csp.log("TotalHappiness: " + solution.getValue("Total Happiness"));
	}

	public static void main(String[] args) {

		long startMS = System.currentTimeMillis();

		Christmas model = new Christmas();
		model.define();
		model.maximize();

		long finishMS = System.currentTimeMillis();
		System.out.println("Runtime: " + (finishMS - startMS) + " Millis");

	}

}
