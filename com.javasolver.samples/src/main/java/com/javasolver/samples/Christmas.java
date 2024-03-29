package com.javasolver.samples;

import javax.constraints.Solution;
import javax.constraints.Var;

import com.javasolver.JavaSolver;

/*
https://dmcommunity.org/challenge-jan-2023/
This model defines a set of people, a set of giftVars, and the HAPPINESS level and cost of each gift.
The objective is to maximize the total HAPPINESS, subject to the BUDGET constraint 
that the total cost of the giftVars must be less than or equal to the BUDGET, 
and the constraint that each person can only receive one gift.

Here is a sample of test data:

PEOPLE:  �Alice�, �Bob�, �Carol�, �Dave�, �Eve�
GIFTS: �Book�, �Toy�, �Chocolate�, �Wine�, �Flowers�
GIFT COSTS: 10, 20, 5, 15, 7
HAPPINESS:
  �Book�: [3, 2, 5, 1, 4]
  �Toy�: [5, 2, 4, 3, 1]
  �Chocolate�: [1, 3, 4, 5, 2]
  �Wine�: [2, 5, 3, 4, 1]
  �Flowers�: [4, 3, 1, 2, 5]
BUDGET: 50
 */

public class Christmas extends JavaSolver {
	// Data
	String[] PEOPLE = { "Alice", "Bob", "Carol", "Dave", "Eve" };
	String[] GIFTS = { "Book", "Toy", "Chocolate", "Wine", "Flowers" };
	int[] COSTS = { 10, 20, 5, 15, 7 };
	int BUDGET = 50;
//	int[][] HAPPINESS = new int[][] { 
//		{ 3, 2, 5, 1, 4 }, 
//		{ 5, 2, 4, 3, 1 }, 
//		{ 1, 3, 4, 5, 2 }, 
//		{ 2, 5, 3, 4, 1 },
//		{ 4, 3, 1, 2, 5 } 
//	};
	int[][] HAPPINESS = new int[][] { // of people getting gifts
		{3,5,1,2,4},
		{2,2,3,5,3},
		{5,4,4,3,1},
		{1,3,5,4,2},
		{4,1,2,1,5}
	};

	public void define() {
		try {
			Var[] giftVars = csp.variableArray("gift",0, GIFTS.length-1, PEOPLE.length);
			Var[] costVars = csp.variableArray("cost",min(COSTS), max(COSTS), GIFTS.length);

			// Define COST and HAPPINESS constraints
			Var[] happinessVars = new Var[PEOPLE.length];
			for (int i = 0; i < PEOPLE.length; i++) {
				csp.postElement(COSTS, giftVars[i], "=", costVars[i]);
				happinessVars[i] = csp.variable("happiness-"+i,min(HAPPINESS[i]),max(HAPPINESS[i]));
				csp.postElement(HAPPINESS[i], giftVars[i], "=", happinessVars[i]);
			}
			
			// BUDGET constraint
			Var totalCost = csp.sum(costVars);
			totalCost.setName("Total Cost");
			csp.add(totalCost);
			csp.post(totalCost, "<=", BUDGET);
			csp.log(totalCost.toString());
			
			// Define objective
			Var totalHappinessVar = csp.sum(happinessVars);
			totalHappinessVar.setName("Total Happiness");
			csp.add(totalHappinessVar);
			setObjective(totalHappinessVar);
			csp.log(totalHappinessVar.toString());
		} catch (Exception e) {
			throw new RuntimeException("Problem is overconstrained");
		}
	}

	public void saveSolution(Solution solution) {
		solution.log();
		System.out.println("==== RESULTS ====");
		for (int i = 0; i < GIFTS.length; i++) {
			int value = solution.getValue("gift-" + i);
			System.out.println(PEOPLE[i] + " => " + GIFTS[value]);
		}
		System.out.println("Total Happiness: " + solution.getValue("Total Happiness"));
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Christmas model = new Christmas();
		model.define();
		model.maximize();
		long finish = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (finish - start) + " milliseconds");
	}

	public int max(int[] array) {
		int max = array[0];
		for (int element : array){
			if (element > max)
				max = element;
		}
		return max;
	}
	public int min(int[] array) {
		int min = array[0];
		for (int element : array){
			if (element < min)
				min = element;
		}
		return min;
	}
}
