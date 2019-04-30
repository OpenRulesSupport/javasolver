package com.javasolver;

import javax.constraints.*;
import javax.constraints.impl.search.selectors.ValueSelectorMax;

public abstract class JavaSolver {

	protected Problem csp; // used by all subclasses
	protected Var objectiveVar;
	protected int maxNumberOfSolutions;
	protected int timeLimitInSec; 

	/**
	 * Creates an instance of JavaSolver with 
	 * an embedded JSR-331 constraint satisfaction problem "csp"
	 */
	public JavaSolver() {
		csp = ProblemFactory.newProblem(getClass().getName());
		log("=== JavaSolver problem: "+getClass().getName());
		objectiveVar = null;
		maxNumberOfSolutions = 0;
		timeLimitInSec = 0;
	}
	
	public void log(String text) {
		csp.log(text);
	}
	
	/**
	 * Defines an optimization problem
	 */
	public abstract void define(); 
	
	public void setObjective(Var objectiveVar) {
		this.objectiveVar = objectiveVar;
	}
	
	public Var getObjective() {
		return objectiveVar;
	}
	
	public int getMaxNumberOfSolutions() {
		return maxNumberOfSolutions;
	}

	public void setMaxNumberOfSolutions(int maxSolutions) {
		this.maxNumberOfSolutions = maxSolutions;
	}

	/**
	 * @return a JSR-331 constraint satisfaction problem
	 */
	public Problem csp() {
		return csp;
	}

	/**
	 * Solves the optimization problem. If an objective is set, then it will try to find an optimal solution.
	 * If an objective is not set (null), then it will try to find a feasible solution.
	 * @param objectiveType: Objective.MINIMIZE or Objective.MAXIMIZE
	 * @param timeLimitInSec
	 * @return true is an optimal solution is found and false otherwise
	 */
	public Solution solve(Objective objectiveType,int timeLimitInSec) {
		return solve(objectiveType,timeLimitInSec,VarSelectorType.INPUT_ORDER,ValueSelectorType.MIN);
	}
	
	public Solution solve(Objective objectiveType,int timeLimitInSec, VarSelectorType varSelector, ValueSelectorType valueSelector) { 
		Solver solver = csp.getSolver();
		solver.traceSolutions(true);
		if (timeLimitInSec > 0)
			solver.setTimeLimit(timeLimitInSec*1000); 
//		solver.traceExecution(true);
		solver.traceSolutions(true);
//		solver.addStrategyLogVariables(); 
		SearchStrategy strategy = solver.getSearchStrategy();
//		strategy.setVarSelectorType(VarSelectorType.INPUT_ORDER);
		strategy.setVarSelectorType(varSelector);
		strategy.setValueSelectorType(valueSelector);
		solver.setSearchStrategy(csp.getVars(), new ValueSelectorMax());
//		Solution solution = solver.findSolution(); 
		Var objective = getObjective();
		Solution solution = null;
		if (objective != null) {
			log("=== Find Optimal Solution:");
			solution = solver.findOptimalSolution(objectiveType,objectiveVar);
		}
		else {
			log("=== Find a Feasible Solution:");
			solution = solver.findSolution();
		}
		solver.logStats();
		if (solution != null) {
			saveSolution(solution);
		} else {
			log("No Solutions");
		}
		return solution;
	}
	
	public Solution[] solveAll(Objective objectiveType,int timeLimitInSec) { 

		log("=== SOLVE:");
		Solver solver = csp.getSolver();
		solver.traceSolutions(true);
		if (timeLimitInSec > 0)
			solver.setTimeLimit(timeLimitInSec*1000); 
//		solver.traceExecution(true);
		solver.traceSolutions(true);
//		solver.addStrategyLogVariables(); 
//		SearchStrategy strategy = solver.getSearchStrategy();
//		strategy.setVarSelectorType(VarSelectorType.INPUT_ORDER);
//		solver.setSearchStrategy(getVars(), new ValueSelectorMax());
//		Solution solution = solver.findSolution();
		if (maxNumberOfSolutions > 0)
			solver.setMaxNumberOfSolutions(maxNumberOfSolutions);
		Solution[]	solutions = solver.findAllSolutions();
		solver.logStats();
		if (solutions == null || solutions.length == 0) {
			log("No Solutions found");
		}
		return solutions;
	}
	
	public Solution solve(int timeLimitInSec) { // PROBLEM RESOLUTION
		return solve(Objective.MINIMIZE,timeLimitInSec);
	}
	
	public Solution solve(Objective objectiveType) { // PROBLEM RESOLUTION
		return solve(objectiveType,0);
	}
	
	/**
	 * Solves the optimization problem using Objective.MINIMIZE
	 */
	public Solution solve() { 
		return solve(Objective.MINIMIZE,0); 
	}
	
	public Solution minimize() { 
		return optimize(Objective.MINIMIZE);
	}
	
	public Solution maximize() { 
		return optimize(Objective.MAXIMIZE);
	}
	
	public void setTimeLimit(int timeLimitInSec) {
		this.timeLimitInSec = timeLimitInSec; 
	}
		
	public Solution optimize(Objective objectiveType) {
		Solver solver = csp.getSolver();
		solver.traceSolutions(true);
		if (timeLimitInSec > 0)
			solver.setTimeLimitGlobal(timeLimitInSec*1000);
		Var objective = getObjective();
		Solution solution = null;
		if (objective != null) {
			log("=== Find Optimal Solution:");
			solution = solver.findOptimalSolution(objectiveType,objectiveVar);
		}
		else {
			log("=== Find a Feasible Solution:");
			solution = solver.findSolution();
		}
		solver.logStats();
		if (solution != null) {
			saveSolution(solution);
		} else {
			log("No Solutions");
		}
		return solution;
	}

	/**
	 * Problem specific method that save value from the solution to business objects
	 * This method is called after the JavaSolver's method solve(), minimize() 
	 * or maximize() finds a solution
	 * @param solution
	 */
	public void saveSolution(Solution solution) {
		solution.log();
	}

}