package com.javasolver.samples;

import javax.constraints.Solution;

/*-------------------------------------------------------------
 Solve the puzzle:

   S E N D
 + M O R E
 ---------
 M O N E Y

 where different letters represent different digits.
 ------------------------------------------------------------ */

import javax.constraints.Var;

import com.javasolver.JavaSolver;

public class SendMoreMoney extends JavaSolver {
	
	// Problem Definition
	public void define() {
		// define variables
		Var S = csp.variable( "S",1, 9);
		Var E = csp.variable( "E",0, 9);
		Var N = csp.variable( "N",0, 9);
		Var D = csp.variable( "D",0, 9);
		Var M = csp.variable( "M",1, 9);
		Var O = csp.variable( "O",0, 9);
		Var R = csp.variable( "R",0, 9);
		Var Y = csp.variable( "Y",0, 9);

		// Post "all different" constraint
		Var[] vars = new Var[] { S, E, N, D, M, O, R, Y };
		csp.postAllDiff(vars);
		
		// Define constraint SEND + MORE = MONEY 
		int coef[] = { 1000, 100, 10, 1, 1000, 100, 10, 1, -10000, -1000, -100, -10, -1 };
		Var[] sendmoremoney = new Var[] { S, E, N, D, M, O, R, E, M, O, N, E, Y};
		Var scalProduct = csp.scalProd(coef,sendmoremoney);
		csp.post(scalProduct, "=", 0);
	}
		
	// This method is called after the Optimization's method solve() finds a solution
	public void saveSolution(Solution s) {	
		s.log();
		log("  "+s.getValue("S")+s.getValue("E")+s.getValue("N")+s.getValue("D"));
		log("+ "+s.getValue("M")+s.getValue("O")+s.getValue("R")+s.getValue("E"));
		log("=======");
		log(" "+s.getValue("M")+s.getValue("O")+s.getValue("N")+s.getValue("E")+s.getValue("Y"));
	}
		
		
	public static void main(String[] args) {
		SendMoreMoney sendMoreMoney = new SendMoreMoney();
		sendMoreMoney.define();
		sendMoreMoney.solve();
	}
}
