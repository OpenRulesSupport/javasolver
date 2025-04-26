package com.javasolver.samples;
import javax.constraints.*;

public class BoxOptimization { //extends Problem {
    public void define() {
        // Create the problem
        Problem p = ProblemFactory.newProblem("BoxOptimization");

        // Define variables L, W, H with domains from 1 to 100
        Var L = p.variable("L", 1, 100);
        Var W = p.variable("W", 1, 100);
        Var H = p.variable("H", 1, 100);

        // Add volume constraint L * W * H >= 9000
        p.post(L.multiply(W).multiply(H), ">=", 9000);

        // Add strap measure constraint L + 2(W + H) <= 100
        p.post(L.plus(W.multiply(2)).plus(H.multiply(2)), "<=", 100);

        // Define the objective function (minimize strap measure)
        Var strapMeasure = L.plus(W.multiply(2)).plus(H.multiply(2));
        p.add("Objective", strapMeasure);
        Solver solver = p.getSolver();
        Solution solution = solver.findOptimalSolution(Objective.MINIMIZE, strapMeasure);

        // Print the solution
        if (solution != null) {
            solution.log();
            System.out.println("Optimal Box Dimensions:");
            System.out.println("Length (L): " + solution.getValue("L"));
            System.out.println("Width (W): " + solution.getValue("W"));
            System.out.println("Height (H): " + solution.getValue("H"));
        } else {
            System.out.println("No solution found.");
        }
    }

    public static void main(String[] args) {
        BoxOptimization problem = new BoxOptimization();
        problem.define();
    }
}