package com.javasolver.samples;

import javax.constraints.Var;

import com.javasolver.Optimization;

/*
    Problem "Zoo, Buses, and Kids"	
	300 kids need to travel to the London zoo. 
	The school may rent 40 seats and 30 seats buses for 500 and 400 £. 
	How many buses of each to minimize cost? 

 */

public class ProblemZoo extends Optimization {

	public void define() {

		Var numberOf30Buses = csp.variable("Number Of Buses With 30 Seats", 0, 30);
		Var numberOf40Buses = csp.variable("Number Of Buses With 40 Seats", 0, 30);
		int[] seats = new int[] { 30, 40 };
		Var[] vars = new Var[] { numberOf30Buses, numberOf40Buses };
		Var numberOfAllSeats = csp.scalProd("Number Of All Seats", seats, vars);
		csp.post(numberOfAllSeats, ">=", 300);
		int[] costs = new int[] { 400, 500 };
		Var totalCost = csp.scalProd("Total Cost", costs, vars);
		setObjective(totalCost);
	}

	public static void main(String[] args) {
		ProblemZoo problem = new ProblemZoo();
		problem.define();
		problem.minimize();
	}
}
