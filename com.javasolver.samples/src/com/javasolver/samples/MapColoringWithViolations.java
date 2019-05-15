package com.javasolver.samples;

import javax.constraints.Solution;
import javax.constraints.Var;

import com.javasolver.JavaSolver;

/**
 * A map-coloring problem involves choosing colors for the countries on a map in
 * such a way that at most four colors are used and no two neighboring countries
 * are the same color. For our example, we will consider six countries: Belgium,
 * Denmark, France, Germany, Netherlands, and Luxembourg. The colors can be
 * blue, white, red or green but white color is missing. Use 3 colors considering 
 * that some constraint can be violated with the relative violation costs:
 * France - Luxembourg:  257
 * Luxembourg - Germany: 904
 * Luxembourg - Belgium: 568
 * The objective is to minimize the total constraint violation .
 */

public class MapColoringWithViolations extends JavaSolver {
	
	static String[] colors = { "red", "green", "blue" };
	static String[] countries = {"Belgium","Denmark","France","Germany","Netherland","Luxembourg"};

	public void define() {
		// Variables
		int n = colors.length-1;
		Var Belgium = csp.variable("Belgium",0, n);
		Var Denmark = csp.variable("Denmark",0, n);
		Var France  = csp.variable("France",0, n);
		Var Germany = csp.variable("Germany",0, n);
		Var Netherlands = csp.variable("Netherland",0, n);
		Var Luxembourg = csp.variable("Luxembourg",0, n);
		Var[] vars = new Var[]{ Belgium, Denmark, France, Germany, Netherlands, Luxembourg };
		
		// Hard Constraints
		csp.post(France,"!=",Belgium);
		csp.post(France,"!=",Germany);
		csp.post(Belgium,"!=",Netherlands);
		csp.post(Belgium,"!=",Germany);
		csp.post(Germany,"!=",Netherlands);
		csp.post(Germany,"!=",Denmark);
		
		// Soft Constraints
		Var[] weightVars = {
			csp.linear(France,"=",Luxembourg).asBool().multiply(257),
			csp.linear(Luxembourg,"=",Germany).asBool().multiply(904),
			csp.linear(Luxembourg,"=",Belgium).asBool().multiply(568)
		};
		// Optimization objective
		Var totalViolations = csp.sum(weightVars);
		totalViolations.setName("Total Constraint Violations");
		setObjective(totalViolations);
	}
	
	public void saveSolution(Solution solution) {
			solution.log();
			for (String country : countries) {
				csp.log(country + ": " + colors[solution.getValue(country)]);
			}
	}

	public static void main(String[] args) {
		MapColoringWithViolations mc = new MapColoringWithViolations();
		mc.define();
		mc.minimize();
	}
}
