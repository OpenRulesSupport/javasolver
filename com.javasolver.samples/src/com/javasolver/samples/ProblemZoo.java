package com.javasolver.samples;

import javax.constraints.Var;

import com.javasolver.JavaSolver;

/*
    Problem "Zoo, Buses, and Kids"	
	300 kids need to travel to the London zoo. 
	The school may rent 40 seats and 30 seats buses for 500 and 400 £. 
	How many buses of each to minimize cost? 

 */

public class ProblemZoo extends JavaSolver {

	public void define() {

		Var numberOf30Buses = csp.variable("Number Of 30 seats buses", 0, 30);
		Var numberOf40Buses = csp.variable("Number Of 40 seats buses", 0, 30);
		int[] seats = new int[] { 30, 40 };
		Var[] vars = new Var[] { numberOf30Buses, numberOf40Buses };
		Var totalNumberOfSeats = csp.scalProd("Total number of seats", seats, vars);
		csp.post(totalNumberOfSeats, ">=", 300);
		int[] costs = new int[] { 400, 500 };
		Var totalCost = csp.scalProd("Total cost", costs, vars);
		setObjective(totalCost);
	}

	public static void main(String[] args) {
		ProblemZoo problem = new ProblemZoo();
		problem.define();
		problem.minimize();
	}
}
