package com.javasolver;

import javax.constraints.Objective;
import javax.constraints.Problem;
import javax.constraints.ProblemFactory;
import javax.constraints.SearchStrategy;
import javax.constraints.Solution;
import javax.constraints.Solver;
import javax.constraints.ValueSelectorType;
import javax.constraints.Var;
import javax.constraints.VarSelectorType;
import javax.constraints.VarReal;

public class JavaSolver {
	
	static final String RELEASE = "Java Solver Release 2.3.3 (build of Apr 23, 2025)";

	protected Problem csp; // used by all subclasses
	protected Var objectiveVar;
	protected VarReal objectiveVarReal;
	protected int maxNumberOfSolutions;
	protected int timeLimitInSec; 

	/**
	 * Creates an instance of JavaSolver with 
	 * an embedded JSR-331 constraint satisfaction problem "csp"
	 */
	public JavaSolver() {
		csp = ProblemFactory.newProblem(getClass().getName());
		log(RELEASE);
		log("Problem: "+getClass().getName());
		objectiveVar = null;
		maxNumberOfSolutions = 0;
		timeLimitInSec = 0;
	}
	
	/**
	 * Logs (displays) the text to a log files defined using log4j.properties
	 * @param text
	 */
	public void log(String text) {
		csp.log(text);
	}
	
	/**
	 * Defines an optimization problem
	 */
	public void define() {
		log("This method defines an optimization problem");
	}
	
	/**
	 * Sets an optimization objective as Var used by methods minimize() or maximize()
	 * @param objectiveVar
	 */
	public void setObjective(Var objectiveVar) {
		this.objectiveVar = objectiveVar;
	}
	
	/**
	 * 
	 * @return an objective - integer constrained var
	 */
	public Var getObjective() {
		return objectiveVar;
	}
	
	/**
	 * Sets an optimization objective as VarReal used by methods minimize() or maximize()
	 * @param objectiveVar
	 */
	public void setObjectiveReal(VarReal objectiveVar) {
		this.objectiveVarReal = objectiveVar;
	}
	
	/**
	 * 
	 * @return an objective - real constrained var
	 */
	public VarReal getObjectiveReal() {
		return objectiveVarReal;
	}
	
	/**
	 * @return a JSR-331 constraint satisfaction problem
	 */
	public Problem csp() {
		return csp;
	}

	/**
	 * Solves the constraint satisfaction problem. 
	 * @return true is an solution is found and false otherwise
	 */
	public Solution solve() { 
		log("Find a Feasible Solution:");
		Solver solver = csp.getSolver();
		solver.traceSolutions(true);
		if (timeLimitInSec > 0)
			solver.setTimeLimit(timeLimitInSec*1000); 
//		solver.traceExecution(true);
//		solver.addStrategyLogVariables(); 
		Solution solution = solver.findSolution();
		solver.logStats();
		if (solution != null) {
			saveSolution(solution);
		} else {
			log("No Solutions");
		}
		return solution;
	}
	
	public Solution[] solveAll() {  

		log("Find All Solutions:");
		Solver solver = csp.getSolver();
		solver.traceSolutions(true);
		if (timeLimitInSec > 0)
			solver.setTimeLimit(timeLimitInSec*1000); 
		if (maxNumberOfSolutions > 0)
			solver.setMaxNumberOfSolutions(maxNumberOfSolutions);
		Solution[]	solutions = solver.findAllSolutions();
		solver.logStats();
		if (solutions == null || solutions.length == 0) {
			log("No Solutions found");
		}
		else
			saveSolutions(solutions);
		return solutions;
	}
	
	/**
	 * Solves the optimization problem using Objective.MINIMIZE
	 */
	public Solution minimize() { 
		log("Minimize: "+getObjective());
		return optimize(Objective.MINIMIZE);
	}
	
	public Solution maximize() { 
		log("Maximize: "+getObjective());
		return optimize(Objective.MAXIMIZE);
	}
	
	/**
	 * Solves the optimization problem using the objective type Objective.MINIMIZE or Objective.MAXIMIZE
	 * @param objectiveType
	 * @return
	 */
	public Solution optimize(Objective objectiveType) {
		Solver solver = csp.getSolver();
		solver.traceSolutions(true);
		if (timeLimitInSec > 0)
			solver.setTimeLimitGlobal(timeLimitInSec*1000);
		Var objective = getObjective();
		Solution solution = null;
		if (objective != null) {
			solution = solver.findOptimalSolution(objectiveType,objective);
		}
		else { // try VarReal
			VarReal objectiveReal = getObjectiveReal();
			if (objectiveReal != null) {
				solution = solver.findOptimalSolution(objectiveType,objectiveReal);
			}
			else {
				log("=== Find a Feasible Solution:");
				solution = solver.findSolution();
			}
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
	 * Sets a time limit for the solution search
	 * @param timeLimitInSec a time limit in seconds
	 */
	public void setTimeLimit(int timeLimitInSec) {
		this.timeLimitInSec = timeLimitInSec; 
	}
	
	/**
	 * This method forces Java Solver to consider no more possible solutions than "maxSolutions"
	 * @param maxSolutions
	 */
	public void setMaxNumberOfSolutions(int maxSolutions) {
		this.maxNumberOfSolutions = maxSolutions;
	}
	
	/**
	 * Sets a variable selector used during the solution search. By default, the varSelectorType is 
	 * VarSelectorType.INPUT_ORDER - selects variables in order they were defined in the method define().
	 * Other useful selector types:
	 * VarSelectorType.MIN_VALUE - selects variables with smallest lower bound first
	 * VarSelectorType.MAX_VALUE - selects variables with largest lower bound first
	 * VarSelectorType.MIN_DOMAIN - selects variables with the minimal size of domain
	 * VarSelectorType.MIN_DOMAIN_MIN_VALUE - selects variables with the minimal size of domain. The smallest lower bound serves as tie break
	 * VarSelectorType.RANDOM - selects variables randomly
	 * @param varSelectorType
	 */
	public void setVarSelector(VarSelectorType varSelectorType) { 
		Solver solver = csp.getSolver();
		SearchStrategy strategy = solver.getSearchStrategy();
		strategy.setVarSelectorType(varSelectorType);
	}
	
	/**
	 * Sets a value selector used during the solution search. By default, the valueSelectorType is 
	 * VarSelectorType.INPUT_ORDER - selects variables in order they were defined in the method define().
	 * Other useful selector types:
	 * ValueSelectorType.MIN - try values in increasing order
	 * ValueSelectorType.MAX - try values in decreasing order
	 * ValueSelectorType.MIDDLE - try values in the middle of domain
	 * ValueSelectorType.RANDOM - try a random value
	 * @param valueSelectorType
	 */
	public void setValueSelector(ValueSelectorType valueSelectorType) { 
		Solver solver = csp.getSolver();
		SearchStrategy strategy = solver.getSearchStrategy();
		strategy.setValueSelectorType(valueSelectorType);
	}

	/**
	 * Problem specific method that saves values from the solution to business objects
	 * This method is called after the JavaSolver's method solve(), minimize() 
	 * or maximize() finds a solution
	 * @param solution
	 */
	public void saveSolution(Solution solution) {
		solution.log();
	}
	
	/**
	 * Problem specific method that saves values from all found solution to business objects
	 * This method is called after the JavaSolver's method solveAll()
	 * @param solutions
	 */
	public void saveSolutions(Solution[] solutions) {
		for(Solution solution : solutions) {
			saveSolution(solution);
		}
	}

}