package com.javasolver.samples;

import javax.constraints.*;
import com.javasolver.JavaSolver;

public class CrackTheCode extends JavaSolver {
	
	// Define variables for the code XYZ
	Var X = csp.variable("X", 0, 9);
	Var Y = csp.variable("Y", 0, 9);
	Var Z = csp.variable("Z", 0, 9);
	
	public Var eq(Var var, int value) {
		return csp.linear(var, "=", value).asBool();
	}
	
	public Var correctNumbers(int x, int y, int z) {
		return eq(X,x).plus(eq(Y,y)).plus(eq(Z,z));
	}
	
	public Var correctNumbersInWrongPositions(int x, int y, int z) {
		return eq(Y,x).plus(eq(Z,x)).plus(eq(X,y)).plus(eq(Z,y)).plus(eq(X,z)).plus(eq(Y,z));
	}
	
	public void define() {
		try {
		
			csp.log("Post constraint: 682 – one number is correct and in the correct position");
			csp.post(correctNumbers(6,8,2),"=",1);
			
			csp.log("Post constraint: 645 – one number is correct but in the wrong position");
			csp.post(correctNumbers(6,4,5),"=",0);
			csp.post(correctNumbersInWrongPositions(6,4,5),"=",1);
			
			csp.log("Post constraint: 206 – two numbers are correct but in the wrong positions");
			csp.post(correctNumbers(2,0,6),"=",0);
			csp.post(correctNumbersInWrongPositions(2,0,6),"=",2);
			
			
			csp.log("Post constraint: 738 – nothing is correct");
			csp.post(correctNumbers(7,3,8),"=",0);
			csp.post(correctNumbersInWrongPositions(7,3,8),"=",0);
			
			csp.log("Post constraint: 780 – one number is correct but in the wrong position");
			csp.post(correctNumbers(7,8,0),"=",0);
			csp.post(correctNumbersInWrongPositions(7,8,0),"=",1);
		}
		catch(Exception ex) {
			csp.log("Cannot post the last constraint!");
		}
	}
	
	public static void main(String[] args) {
		CrackTheCode problem = new CrackTheCode();
		problem.define();
		problem.solveAll();
	}

}
