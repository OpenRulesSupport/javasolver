package com.javasolver.samples;

import javax.constraints.*;

import com.javasolver.JavaSolver;

public class XYZ extends JavaSolver {
	
	public void define() {

		// Define variables
		Var x = csp.variable("X", 1, 10);
		Var y = csp.variable("Y", 1, 10);
		Var z = csp.variable("Z", 1, 10);
		
		// Post constraints
		csp.post(x, "<", y); // X < Y
		csp.post(x.plus(y), "=", z); // X + Y = Z
		
		// Define objective
		Var objective = x.multiply(3).multiply(y).minus(z.multiply(4)); // 3XY-4Z
		csp.add("Objective",objective);
		setObjective(objective);
	}
	
	public static void main(String[] args) {
		XYZ problem = new XYZ();
		problem.define();
//		problem.setValueSelector(ValueSelectorType.MAX);
//		problem.setMaxNumberOfSolutions(10);
		problem.minimize();
		problem.maximize();
		problem.solveAll();
	}

}
