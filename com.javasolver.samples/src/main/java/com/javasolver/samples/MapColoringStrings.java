package com.javasolver.samples;

import javax.constraints.*;

import com.javasolver.JavaSolver;

/**
 * A map-coloring problem involves choosing colors for the countries on a map in
 * such a way that at most four colors are used and no two neighboring countries
 * are the same color. For our example, we will consider six countries: Belgium,
 * Denmark, France, Germany, Netherlands, and Luxembourg. The colors can be
 * blue, white, red or green.
 */

public class MapColoringStrings extends JavaSolver {

	static final String[] colors = { "red", "green", "blue", "yellow", "pink" };

	public void define() {
		try {
			// Variables
			VarString Belgium = csp.variableString("Belgium", colors);
			VarString Denmark = csp.variableString("Denmark", colors);
			VarString France = csp.variableString("France", colors);
			VarString Germany = csp.variableString("Germany", colors);
			VarString Netherlands = csp.variableString("Netherlands", colors);
			VarString Luxembourg = csp.variableString("Luxembourg", colors);
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
			
//			Var[] vars = new Var[] { 
//					Belgium.getInt(),
//					Denmark.getInt(),
//					France.getInt(),
//					Germany.getInt(),
//					Netherlands.getInt(),
//					Luxembourg.getInt()
//			};
//			setObjective(csp.sum(vars));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void saveSolution(Solution solution) {
		solution.log();
		for (int i = 0; i < csp.getVarStrings().length; i++) {
			String country = csp.getVarStrings()[i].getName();
			csp.log(country + ": " + colors[solution.getValue(country)]);
		}
	}

	public static void main(String[] args) {
		MapColoringStrings map = new MapColoringStrings();
		map.define();
		map.solve();
		//map.minimize();
		//map.maximize();
	}
}
