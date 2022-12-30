package com.javasolver.samples;

import java.util.Arrays;
import java.util.List;

import javax.constraints.Solution;
import javax.constraints.Var;

import com.javasolver.JavaSolver;

/*
https://dmcommunity.org/challenge-jan-2023/
This model defines a set of people, a set of gifts, and the HAPPINESS level and cost of each gift.
The objective is to maximize the total HAPPINESS, subject to the BUDGET constraint 
that the total cost of the gifts must be less than or equal to the BUDGET, 
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

public class ChristmasModelOO extends JavaSolver {
	// Data
	String[] PEOPLE = { "Alice", "Bob", "Carol", "Dave", "Eve" };
	String[] GIFTS = { "Book", "Toy", "Chocolate", "Wine", "Flowers" };
	int[] COSTS = { 10, 20, 5, 15, 7 };
	int BUDGET = 50;
	int[][] HAPPINESS = new int[][] { 
		{ 3, 2, 5, 1, 4 }, 
		{ 5, 2, 4, 3, 1 }, 
		{ 1, 3, 4, 5, 2 }, 
		{ 2, 5, 3, 4, 1 },
		{ 4, 3, 1, 2, 5 } 
	};
	
	int minCost = Arrays.stream(COSTS).min().getAsInt();
	int maxCost = Arrays.stream(COSTS).max().getAsInt();
	
	Person[] people;
	int totalCost = 0;
	int totalHappiness = 0;
	
	public void define() {
		try {
			people = new Person[PEOPLE.length];
			for (int i = 0; i < PEOPLE.length; i++) {
				people[i] = new Person(i);
			}
			Var[] costVars = new Var[people.length];
			Var[] happinessVars = new Var[people.length];
			for (int i = 0; i < people.length; i++) {
				costVars[i] = people[i].getCostVar();
				happinessVars[i] = people[i].getHappinessVar();
			}
			// BUDGET constraint
			Var totalCost = csp.sum("TotalCost",costVars);
			csp.post(totalCost, "<=", BUDGET);
			// Define objective
			Var totalHappiness = csp.sum("TotalHappiness",happinessVars);
			setObjective(totalHappiness);
		} catch (Exception e) {
			throw new RuntimeException("Problem is overconstrained");
		}
	}

	public void saveSolution(Solution solution) {
		for (int i = 0; i < people.length; i++) {
			int value = solution.getValue(GIFTS[i]);
			people[i].setGift(GIFTS[value]);
		}
		totalCost = solution.getValue("TotalCost");
		totalHappiness = solution.getValue("TotalHappiness");
	}
	
	public void printResults() {
		System.out.println("==== RESULTS ====");
		for (int i = 0; i < people.length; i++) {
			System.out.println(people[i]);
		}
		System.out.println("Total Cost: " + totalCost);
		System.out.println("Total Happiness: " + totalHappiness);
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ChristmasModelOO model = new ChristmasModelOO();
		model.define();
		model.maximize();
		model.printResults();
		long finish = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (finish - start) + " milliseconds");
	}

	class Person {
		int index;
		String name;
		String gift;
		
		Var giftVar;
		Var costVar;
		Var happinessVar;
		
		public Person(int index) {
			name = PEOPLE[index];
			giftVar = csp.variable(GIFTS[index],0, GIFTS.length-1);
			// Cost constraint
			costVar = csp.variable("cost-"+index,minCost, maxCost);
			csp.postElement(COSTS, giftVar, "=", costVar);
			// Happiness constraint
			int[] happiness = HAPPINESS[index];
			int happinessMin = Arrays.stream(happiness).min().getAsInt();
			int happinessMax = Arrays.stream(happiness).max().getAsInt();
			happinessVar = csp.variable("happiness-"+index,happinessMin,happinessMax);
			csp.postElement(happiness, giftVar, "=", happinessVar);
		}

		public Var getHappinessVar() {
			return happinessVar;
		}

		public Var getGiftVar() {
			return giftVar;
		}

		public Var getCostVar() {
			return costVar;
		}
		
		public void setGift(String gift) {
			this.gift = gift;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", gift=" + gift + "]";
		}
		
	}
}
