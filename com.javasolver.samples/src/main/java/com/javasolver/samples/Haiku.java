/*
 * https://dmcommunity.org/2019/10/23/challenge-nov-2019-numerical-haiku/
 *  
 * Solve a CSP model to get all English-language haikus of the form
 *   A
 * + B
 * = C
 * where A, B, C are integers.
 */

package com.javasolver.samples;

import javax.constraints.*;
import com.javasolver.JavaSolver;

public class Haiku extends JavaSolver {
	
	SyllablesInNumbers syllablesInNumbers = new SyllablesInNumbers();
	
	public void define() {
		
		//Define variables
		int max = syllablesInNumbers.getMax();
		Var X = csp.variable("X", 0, max);
		Var Y = csp.variable("Y", 0, max);
		Var Z = csp.variable("Z", 0, max);
		
		int[] allSyllables = syllablesInNumbers.getAllSyllables();
		try {
			
			Var sylX = csp.element(allSyllables, X);
			csp.add("sylX",sylX);
			Var sylY = csp.element(allSyllables, Y);
			csp.add("sylY",sylY);
			Var sylZ = csp.element(allSyllables, Z);
			csp.add("sylZ",sylZ);
			
			// Post: # of syllables in the first line equals to 5
			csp.post(sylX, "=", 5);
			
			// Post: # of syllables in the second line = 7 (1 for 'plus')
			csp.post(sylY.plus(1), "=", 7);
			
			// Post: # of syllables in the third line = 5 (2 for 'equals')
			csp.post(sylZ.plus(2), "=", 5);
			
			// Post: X + Y = Z
			csp.post(X.plus(Y), "=", Z);
			
		}
		catch(Exception ex) {
			csp.log("Cannot post the last constraint!");
			System.exit(-1);
		}
	}
	
	public static void main(String[] args) {
		Haiku problem = new Haiku();
		problem.define();
		problem.solveAll();
	}
	
	public String explain(Solution s, String var) {
		return "  [" + syllablesInNumbers.getName(s.getValue(var)) 
		+ " - " + syllablesInNumbers.getSyllables(s.getValue(var)) + " syllables]";
	}
	
	public void saveSolution(Solution s) {	
		s.log();
		System.out.println("  " + s.getValue("X") + explain(s,"X"));
		System.out.println("+ " + s.getValue("Y")  + explain(s,"Y"));
		System.out.println("= " + s.getValue("Z")  + explain(s,"Z"));
		System.out.println("");
	}
}





