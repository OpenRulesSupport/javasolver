package com.javasolver.samples;

import javax.constraints.*;

public class CopilotXYZ {
    public void define() {
        // Create the problem
        Problem p = ProblemFactory.newProblem("CopilotXYZ");

        // Define variables X, Y, Z with domains from 1 to 10
        Var X = p.variable("X", 1, 10);
        Var Y = p.variable("Y", 1, 10);
        Var Z = p.variable("Z", 1, 10);

        // Add constraints X > Y and X - Y = Z
        p.post(X, ">", Y);
        p.post(X.minus(Y), "=", Z);

        // Define the objective function 3XY - 4Z
        Var XY = X.multiply(Y);
        Var objective = XY.multiply(3).minus(Z.multiply(4));

        // Minimize the objective function
        p.add("Objective", objective);
        Solver solver = p.getSolver();
        Solution solution = solver.findOptimalSolution(Objective.MAXIMIZE, objective);

        // Print the solution
        if (solution != null) {
            solution.log();
        } else {
            System.out.println("No solution found.");
        }
    }

    public static void main(String[] args) {
        CopilotXYZ problem = new CopilotXYZ();
        problem.define();
    }
}


