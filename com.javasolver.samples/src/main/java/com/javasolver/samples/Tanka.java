/*
 *  Converted to JavaSolver from haiku.py created by Nathan Brixius https://github.com/natebrix/csp_haiku/blob/master/haiku.py
 *  
 * Solve a CSP model to get all English-language haikus of the form
 *   A
 * + B
 * = C
 * where A, B, C are integers less than 10000.
 */

package com.javasolver.samples;

import javax.constraints.*;
import com.javasolver.JavaSolver;

public class Tanka extends JavaSolver {
	
	SyllablesInNumbers syllablesInNumbers = new SyllablesInNumbers();
	
	public void define() {
		
		//Define variables
		int max = syllablesInNumbers.getMax();
		Var A = csp.variable("A", 0, max);
		Var B = csp.variable("B", 0, max);
		Var C = csp.variable("C", 0, max);
		Var D = csp.variable("D", 0, max);
		Var E = csp.variable("E", 0, max);
		
		int[] allSyllables = syllablesInNumbers.getAllSyllables();
		try {
			// 5-7-5-7-7
			Var sylA = csp.element(allSyllables, A);
			csp.add("sylA",sylA);
			Var sylB = csp.element(allSyllables, B);
			csp.add("sylB",sylB);
			Var sylC = csp.element(allSyllables, C);
			csp.add("sylC",sylC);
			Var sylD = csp.element(allSyllables, D);
			csp.add("sylD",sylD);
			Var sylE = csp.element(allSyllables, E);
			csp.add("sylE",sylE);
			
			// Post # of syllables in the first line equals to 5
			csp.post(sylA, "=", 5);
			
			// Post # of syllables in the second line = 7 (1 for 'plus')
			csp.post(sylB.plus(1), "=", 7);
			
			// Post # of syllables in the third line = 5 (1 for 'plus')
			csp.post(sylC.plus(1), "=", 5);
			
			// Post # of syllables in the fourth line = 7 (1 for 'plus')
			csp.post(sylD.plus(1), "=", 7);
			
			// Post # of syllables in the fifth line = 7 (2 for 'equals')
			csp.post(sylE.plus(2), "=", 7);
			
			csp.log("Post: A + B + C + D = E");
			csp.post(A.plus(B).plus(C).plus(D), "=", E);
			
		}
		catch(Exception ex) {
			csp.log("Cannot post the last constraint!");
			System.exit(-1);
		}
	}
	
	public static void main(String[] args) {
		Tanka problem = new Tanka();
		problem.define();
		problem.setMaxNumberOfSolutions(1000);
		problem.solveAll();
	}
	
	public String explain(Solution s, String var) {
		return "  [" + syllablesInNumbers.getName(s.getValue(var)) 
		  + " - " + syllablesInNumbers.getSyllables(s.getValue(var)) + " syllables]";
	}
	
	public void saveSolution(Solution s) {	
		s.log(10);
		System.out.println("  " + s.getValue("A") + explain(s,"A"));
		System.out.println("+ " + s.getValue("B") + explain(s,"B"));
		System.out.println("+ " + s.getValue("C") + explain(s,"C"));
		System.out.println("+ " + s.getValue("D") + explain(s,"D"));
		System.out.println("= " + s.getValue("E") + explain(s,"E"));
		System.out.println(""); 
	}
}


