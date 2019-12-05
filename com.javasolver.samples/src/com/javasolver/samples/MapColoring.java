package com.javasolver.samples;

import javax.constraints.Solution;
import javax.constraints.Var;

import com.javasolver.JavaSolver;

/**
 * A map-coloring problem involves choosing colors for the countries on a map in
 * such a way that at most four colors are used and no two neighboring countries
 * are the same color. For our example, we will consider six countries: Belgium,
 * Denmark, France, Germany, Netherlands, and Luxembourg. The colors can be
 * blue, white, red or green.
 */

public class MapColoring extends JavaSolver {

	static String[] colors = { "red", "green", "blue", "yellow" };
	static String[] countries = {"Belgium","Denmark","France","Germany","Netherlands","Luxembourg"};

	public void define() {
			// Variables
			int n = colors.length-1;
			Var Belgium = csp.variable("Belgium", 0, n);
			Var Denmark = csp.variable("Denmark", 0, n);
			Var France  = csp.variable("France", 0, n);
			Var Germany = csp.variable("Germany", 0, n);
			Var Netherlands = csp.variable("Netherlands", 0, n);
			Var Luxembourg = csp.variable("Luxembourg", 0, n);
			// Constraints
			csp.post(France, "!=", Belgium);
			csp.post(France, "!=", Luxembourg);
			csp.post(France, "!=", Germany);
			csp.post(Luxembourg, "!=", Germany);
			csp.post(Luxembourg, "!=", Belgium);
			csp.post(Belgium, "!=", Netherlands);
			csp.post(Belgium, "!=", Germany);
			csp.post(Germany, "!=", Netherlands);
			csp.post(Germany, "!=", Denmark);
	}
	
	public void saveSolution(Solution solution) {
		solution.log();
		for (String country : countries) {
			csp.log(country + ": " + colors[solution.getValue(country)]);
		}
	}

	public static void main(String[] args) {
		MapColoring map = new MapColoring();
		map.define();
		map.solve();
	}
}
