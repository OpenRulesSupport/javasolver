package com.javasolver.samples;

import javax.constraints.*;

import com.javasolver.JavaSolver;

public class Coins extends JavaSolver {

    public void define() {
        // Define the coin denominations and their values in cents
        int[] coinDenominations = { 1, 2, 5, 10, 20, 50, 100 };
        int[] coinValues = { 1, 2, 5, 10, 20, 50, 100 };

        // Define the target amount in cents (1 Euro = 100 cents)
        int targetAmount = 100;

        // Create an array of variables to represent the number of each coin
        // denomination used
        Var[] coinCounts = new Var[coinDenominations.length];
        for (int i = 0; i < coinDenominations.length; i++) {
            coinCounts[i] = csp.variable("Coin_" + coinValues[i], 0, 100);
        }
        Var totalValue = csp.scalProd(coinDenominations, coinCounts);
        csp.post(totalValue, "=", targetAmount);
    }

    public static void main(String[] args) {
        Coins problem = new Coins();
        problem.define();
        problem.solveAll();
    }
}
