# javasolver
This is a very simple but powerful Java API for Constraint and Linear Solvers supported from http://javasolver.com.

Java Solver is an open source product that provides a minimalistic, simple-to-use Java API for modeling and solving optimization problems. It contains only one class JavaSolver, from which you need to inherit your own Solver. This class may have only one method define() that will use your own Java objects to define constrained variables and posting constraints on them. Java Solver uses the basic methods of the standard Constraint Programming API JSR-331 (https://jsr331.org/). 

You don’t need to write a solving algorithm: instead, you simply call the default method minimize() or maximize() to find an optimal solution using any available CP/LP solver. You also may overload the method saveSolution() to save the found solution into your own Java objects. To switch between different solvers you just add their jar-files to the classpath. That’s the intent. To see how it’s actually done, look at the introductory example (https://javasolvers.wordpress.com/introductory-example/)
