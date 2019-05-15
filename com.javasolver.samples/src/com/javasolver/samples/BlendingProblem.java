package com.javasolver.samples;

import javax.constraints.Var;

import com.javasolver.JavaSolver;

public class BlendingProblem extends JavaSolver {
	
	public void define() {

		String oilTypes[] = { "Crude1", "Crude2", "Crude3" };
		String gasTypes[] = { "Super", "Regular", "Diesel" };
		int demands[] = { 3000, 2000, 1000 };
		int capacities[] = { 5000, 5000, 5000 };
		int gasOctane[] = { 10, 8, 6 };
		int oilOctane[] = { 12, 6, 8 };
		int gasLead[] = { 1, 2, 1 };
		//double oilLead[] = {0.5,2,3};
		int oilLead[] = { 0, 2, 3 };
		int gasPrices[] = { 70, 60, 50 };
		int oilPrices[] = { 45, 35, 25 };
		int maxProduction = 14000;
		int prodCost = 4;
		
		Var[][] blendsTable = new Var[3][3];

		// It would be 9 variables corresponding to the appropriate blending values
		int N = oilTypes.length * gasTypes.length;
		Var[] blends = new Var[N];
		int[] income = new int[N];
		for (int i = 0; i < oilTypes.length; i++) {
			for (int j = 0; j < gasTypes.length; j++) {
				Var var = csp.variable(oilTypes[i] + "-" + gasTypes[j], 0,
						maxProduction);
				blendsTable[i][j] = var;
				blends[gasTypes.length * i + j] = var;
				income[gasTypes.length * i + j] = gasPrices[j]
						- oilPrices[i] - prodCost;
			}
		}
		// and three variables corresponding to the amount of gasoline
		// consumed for advertisement purposes.
		Var[] adv = new Var[gasTypes.length];
		for (int i = 0; i < gasTypes.length; i++) {
			Var var = csp.variable("adv[" + gasTypes[i] + "]", 0,
					maxProduction);
			adv[i] = var;
		}

		// cost function to be maximized (pure income)
		int[] coef1 = new int[income.length + adv.length];
		Var[] vars1 = new Var[income.length + adv.length];
		int n = 0;
		for (int i = 0; i < income.length; i++) {
			coef1[n] = income[i];
			vars1[n] = blends[i];
			n++;
		}
		for (int i = 0; i < adv.length; i++) {
			coef1[n] = -1;
			vars1[n] = adv[i];
			n++;
		}
		Var costFunc = csp.scalProd(coef1, vars1);
		csp.add("costFunc", costFunc);

		// demand constraints
		for (int j = 0; j < gasTypes.length; j++) {
			int[] coef2 = new int[oilTypes.length + 1];
			Var[] vars2 = new Var[oilTypes.length + 1];
			n = 0;
			for (int i = 0; i < oilTypes.length; i++) {
				coef2[n] = 1;
				vars2[n] = blendsTable[i][j];
				n++;
			}
			coef2[n] = -10;
			vars2[n] = adv[j];
			csp.post(coef2, vars2, "=", demands[j]);
		}

		// purchase limitation
		for (int i = 0; i < oilTypes.length; i++) {
			Var[] tmp = new Var[gasTypes.length];
			for (int j = 0; j < gasTypes.length; j++) {
				tmp[j] = blendsTable[i][j];
			}
			csp.post(tmp, "<=", capacities[i]);
		}

		// quality constraints:
		for (int j = 0; j < gasTypes.length; j++) {
			Var[] tmp = new Var[oilTypes.length];
			int[] octaneDiff = new int[oilTypes.length];
			int[] leadDiff = new int[oilTypes.length];
			for (int i = 0; i < oilTypes.length; i++) {
				tmp[i] = blendsTable[i][j];
				octaneDiff[i] = oilOctane[i] - gasOctane[j];
				leadDiff[i] = oilLead[i] - gasLead[j];
			}
			// 1. by octane rating
			csp.post(octaneDiff, tmp, ">=", 0);
			// 2. by leading content
			csp.post(leadDiff, tmp, "<=", 0);
		}
		// capacity limitation
		csp.post(blends, "<=", maxProduction);
		
		// objective
		setObjective(costFunc);
		
		//setTimeLimit(120);
	}

	public static void main(String[] args) {
		BlendingProblem problem = new BlendingProblem();
		problem.define();
		problem.maximize();
	}

}
