# Java Solver 2.3.1 &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; www.javasolver.com    
[![N|Solid](https://javasolvers.files.wordpress.com/2019/05/image.png?w=97)](http://jcp.org/en/jsr/detail?id=331) 
[Java Solver](http://javasolver.com) is a simple Java API for Modeling and Solving Optimization Problems using off-the-shelf Constraint and Linear Solvers. 
# Motivation
As a Java developer, you develop software that provides solutions to your business problems. When a problem has an optimization objective, your program should minimize/maximize this objective. There are plenty of great tools such as **[Constraint Programming (CP) Solvers](http://openjvm.jvmhost.net/CPSolvers/)** and **[Linear Programming (LP) Solvers](http://openjvm.jvmhost.net/LPSolvers/)** which may help you to define and solve such optimization problems. However, you don’t plan to become an optimization expert and/or learn specialized modeling languages. You just want to define your optimization problem in Java and see how one of these powerful solvers can find a good practical solution. If this is your motivation, then Java Solver is for you.
# Solution
Java Solver is an open source product that provides a minimalistic, simple-to-use Java API for modeling and solving optimization problems. It contains only one class JavaSolver, from which you need to inherit your own Solver. This class may have only one method **define()** that will use your own Java objects to define constrained variables and posting constraints on them. Java Solver uses the basic methods of the standard Constraint Programming API **[JSR-331](http://jsr331.org/)**. You don’t need to write a solving algorithm: instead, you simply call the default method **minimize()** or **maximize()** to find an optimal solution using any available CP/LP solver. You also may overload the method saveSolution() to save the found solution into your own Java objects. To switch between different solvers you just add their jar-files to the classpath. That’s the intent. To see how it’s actually done, look at the introductory [example](https://javasolvers.wordpress.com/introductory-example/).
# Availability
Java Solver is available through public [GitHub](https://github.com/OpenRulesSupport/javasolver) and [MVN](https://mvnrepository.com/search?q=javasolver) repositories. You can use if for free for both commercial and open source applications under the terms of the open source [GNU Lesser General Public License (LGPL)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.en.html).

# Support
Java Solver is created and supported by Dr. Jacob Feldman, the specification and maintenance lead of the JCP standard “[JSR-331](http://jsr331.org/)“. You may direct your technical support questions directly to jacobfeldman@openrules.com or you also may post them at the [JSR-331 Support Forum](https://groups.google.com/forum/#!forum/jsr331). If you need help with development of optimization decision models, you may contact [OpenRules](http://openrules.com) [Support](mailto:support@openrules.com) that provides consulting services for using Java Solver in conjunction with with [OpenRules Decision Manager](http://OpenRulesDecisionManager.com).

# Download
You may  download the latest Java Solver sources and executables from the public [GibHub repository](https://github.com/OpenRulesSupport/javasolver) – just click on the button “Clone or download” and select “Download ZIP”. When you unzip the downloaded file *"javasolver-master.zip"*, you will see 3 folders:
- **com.javasolver** – it includes the source code of Java Solver
- **javasolver-all** – it includes the "pom.xml" file with all Java Solver dependencies 
- **com.javasolver.samples** – it includes samples of constraint satisfaction problems ready to be executed with constraint solvers using constraint or linear solvers. 

The first two folders are only for your reference if you want to understand deeper how Java Solver is implemented.The third folder is teh only one that you will actually use to learn Java Solver and to model and solve your own optimization problems.

# Installation
After you download the folder "**com.javasolver.samples**", in general you *do not need any installation*. This folder contains ready to be executed examples of constraint satisfaction and optimization problems. You may just run the corresponding batch-files to execute various examples. During the first run all necessary software packages including JSR331 will be downloaded automatically from the [Maven Repository](https://mvnrepository.com/search?q=javasolver). 

**Constraint Solvers**. There are two underlying constraint solvers
* [Sugar](https://github.com/OpenRulesSupport/jsr331/tree/master/org.jcp.jsr331.sugar)
* [Constrainer](https://github.com/OpenRulesSupport/jsr331/tree/master/org.jcp.jsr331.constrainer) 
* [JSetL](http://www.clpset.unipr.it/jsetl/)

which do not require any additonal installation. Just double-click on the provided batch files such as "*runSendMoreMoney.bat*" to execute the sample problem ["SendMoreMoney"](https://github.com/OpenRulesSupport/javasolver/blob/master/com.javasolver.samples/src/main/java/com/javasolver/samples/SendMoreMoney.java). 
However, if you want to try an alternative constraint solver Choco2, first you need to execute (double-click on) the following file:
- com.javasolver.samples/lib/choco2/installChoco2.bat

It is necessary because the current JSR331 implementations of these open source solvers rely on their old versions, but we plan to switch to the latest version soon.

**Linear Solvers**. The majority of currently available linear solvers (open source or commercial) use the executable files of the corresponding solvers that cannot be included into the Maven Repository. It means that you need to install these solvers from their original repositories and make sure that your Environment variable PATH includes the path to their executables. Here are the names of the executable and URLs from where you may download linear solvers:
- COIN: clp.exe, https://projects.coin-or.org/Clp/
- SCIP: scip.exe, https://scip.zib.de/index.php/
- GLPK: glpsol.exe, https://www.gnu.org/software/glpk/
- CPLEX: cplex.exe, http://www-01.ibm.com/software/integration/optimization/cplex-optimizer/
- GUROBI: gurobi.exe, http://www.gurobi.com/

However, the linear solver CLP (https://github.com/coin-or/Clp) can be used without an additional installation thanks to the library clp-java (https://github.com/quantego/clp-java). It can work only with real variables and doesn't support MIP.

# Run Examples
All examples can be found in the folder "src/main/java/com/javasolver/samples/". They include both linear and non-linear constraint satisfaction and optimization problems. The folder "com.javasolver.samples" includes several bat-files that can be used to execute the provided sample-problems using different solvers. Let's consider a very simple ProblemZoo described in this introductory [example](https://javasolvers.wordpress.com/introductory-example/). Here is its Java implementation:
~~~
public class ProblemZoo extends JavaSolver {
	public void define() {
		Var numberOf30Buses = csp.variable("Number Of 30 seats buses", 0, 30);
		Var numberOf40Buses = csp.variable("Number Of 40 seats buses", 0, 30);
		int[] seats = new int[] { 30, 40 };
		Var[] vars = new Var[] { numberOf30Buses, numberOf40Buses };
		Var totalNumberOfSeats = csp.scalProd("Total number of seats", seats, vars);
		csp.post(totalNumberOfSeats, ">=", 300);
		int[] costs = new int[] { 400, 500 };
		Var totalCost = csp.scalProd("Total cost", costs, vars);
		setObjective(totalCost);
	}
	public static void main(String[] args) {
		ProblemZoo problem = new ProblemZoo();
		problem.define();
		problem.minimize();
	}
}
~~~
You can execute this problem with a double-click on the file "runZoo.bat" that looks like below:
~~~
set CLASS_NAME=com.javasolver.samples.ProblemZoo
rem set SOLVER=Scip
rem set SOLVER=GLPK
set SOLVER=Constrainer
rem set SOLVER=Sugar
@echo off
cd %~dp0
call .\run
pause
~~~
As you can see, it can be executed with a constraint solver such as Constrainer or a linear solver such as Scip or GLPK - just uncomment out one of them (remove/add "rem"). Some problems (such as InsideOutsideProduction) can be solved only with linear problems, while other problems with non-linear constraints can be solved only with constraint solvers. All provided bat-files use the common file "run.bat". You may similarly create a batch file for your own optimization problem and find the most appropriate solver to solve it.

To switch between solvers, you only need to reset the variable SOLVER inside these bat-files. No changes in the original Java code are required. 

# Using Java Solver in your Maven Projects
Java Solver and JSR331 are mavenized and are available for automatic download from the public [MVN Repository](https://mvnrepository.com/search?q=javasolver). To add Java Solver to your Maven project, you should simply add the following Java Solver dependency to your pom.xml file:
~~~
	<dependencies>
		<dependency>
			<groupId>com.javasolver</groupId>
			<artifactId>javasolver-all</artifactId>
			<version>2.1.0</version>
		</dependency>
	</dependencies>
~~~
It includes dependencies to all constraint and linear solvers currently included in the JSR331:
**Open source constraint solvers**: 
-	[Sugar™](http://bach.istc.kobe-u.ac.jp/sugar/) (version 2.1.3, BSD-3-Clause license) 
-	[Constrainer™](http://sourceforge.net/projects/openl-tablets/?source=directory) (GNU LGPL license)
-	[Choco™](http://choco.sourceforge.net/) (version 2, BSD license) 
-	[JSetL™](http://cmt.math.unipr.it/jsetl.html) (GNU GPL license)

**Open Source LP Solvers**:
-	[SCIP](http://scip.zib.de/)
-	[GLPK](http://www.gnu.org/software/glpk/)
-	[COIN](https://projects.coin-or.org/Clp/)
-	[LP_SOLVE](http://lpsolve.sourceforge.net/5.0/)
-	[OJALGO](http://ojalgo.org/).

**Commercial LP Solvers** (you need to acquire commercial licenses and executables):
-	[IBM CPLEX](http://www-01.ibm.com/software/integration/optimization/cplex-optimizer/)
-	[GUROBI](http://www.gurobi.com/)

More implementations will be included in the standard installation as they become available.
