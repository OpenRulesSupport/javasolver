package com.javasolver.samples;

/*
https://www.linkedin.com/pulse/stiglers-diet-problem-revisited-pyopl-roberto-rossi-qgbge/
*/

import java.util.ArrayList;
import java.util.List;

import javax.constraints.Solution;
import javax.constraints.VarReal;
import com.javasolver.JavaSolver;

class Nutrient {
	String name;
	double dailyIntake;
	
	public Nutrient(String name, double dailyIntake) {
		super();
		this.name = name;
		this.dailyIntake = dailyIntake;
	}

	public String getName() {
		return name;
	}

	public double getDailyIntake() {
		return dailyIntake;
	}
	
}

 class Food {
	
	String		commodity;
	String 		units;
	double 		price;
	double[] 	nutrients;
	
	public Food(String commodity, String units, double price, double[] nutrients) {
		super();
		this.commodity = commodity;
		this.units = units;
		this.price = price;
		this.nutrients = nutrients;
	}

	public String getCommodity() {
		return commodity;
	}

	public String getUnits() {
		return units;
	}

	public double getPrice() {
		return price;
	}

	public double[] getNutrients() {
		return nutrients;
	}
	
}

public class DietProblem extends JavaSolver {
	
	List<Nutrient> nutrients;
	List<Food> food;
	double MAX = java.lang.Double.POSITIVE_INFINITY-1;
	
	public DietProblem(List<Nutrient> nutrients, List<Food> food ) {
		this.nutrients = nutrients;
		this.food = food;
	}

	public void define() {
		
		// problem unknowns
		VarReal[] foodVars = new VarReal[food.size()];
		for(int i = 0; i < food.size(); i++) {
			Food f = food.get(i);
			foodVars[i] = csp.variableReal(f.getCommodity(), 0, MAX); 
		}
		
		// objective 
		VarReal totalAnnualCost = csp.sum(foodVars);
		totalAnnualCost.setName("TotalAnnualCost");
	    csp.add(totalAnnualCost);
	    setObjectiveReal(totalAnnualCost);
	    
	    // min daily intake constraints
	    VarReal[][] consumedNutrients = new VarReal[nutrients.size()][food.size()];
		for(int i = 0; i < nutrients.size(); i++) {
			Nutrient nutrient = nutrients.get(i);
			for(int j = 0; j < food.size(); j++) {
				Food f = food.get(j);
				consumedNutrients[i][j] = csp.variableReal("consumedNutrients["+i+","+j+"]", 0, MAX);
				csp.post(consumedNutrients[i][j], "=", foodVars[j].multiply(f.getNutrients()[i]));
			}
			VarReal totalConsumedNutrients = csp.variableReal("totalConsumedNutrients-" + nutrient.getName(),0,MAX);
			csp.post(totalConsumedNutrients, "=", csp.sum(consumedNutrients[i]));
			System.out.println("post " + totalConsumedNutrients + " >= " + nutrient.getDailyIntake()*365);
			csp.post(totalConsumedNutrients,">=",nutrient.getDailyIntake()*365);
		}

	}
	
	public void showSolution(Solution solution) {
		if (solution == null)
			return;
		System.out.println("\nAnnual food:");
		for(int i = 0; i < food.size(); i++) {
			Food f = food.get(i);
			String commodity = f.getCommodity();
			double value = solution.getValueReal(commodity);
			if (value > 0.0) {
				System.out.println(commodity + " = $" + format(value));
			}
		}
		System.out.println("Total Annual Cost = $" + format(solution.getValueReal("TotalAnnualCost")));
		
		System.out.println("\nNutrients per day:");
		for(int i = 0; i < nutrients.size(); i++) {
			Nutrient n = nutrients.get(i);
			String name = "totalConsumedNutrients-" + n.getName();
			double value = solution.getValueReal(name);
			System.out.println(n.getName() + ": " + format(value/365) + " (min " + n.getDailyIntake() + ")");
			
		}
		
		csp.getSolver().logStats();
	}


	public static void main(String[] args) {
		
		// Nutrient minimums.
	    List<Nutrient> nutrients = new ArrayList<Nutrient>();
	    Nutrient nut = new Nutrient ("Calories (kcal)", 3.0);
	    nutrients.add(nut);
	    nutrients.add(new Nutrient ("Protein (g)", 70.0));
	    nutrients.add(new Nutrient ("Calcium (g)", 0.8));
	    nutrients.add(new Nutrient ("Iron (mg)", 12.0));
	    nutrients.add(new Nutrient ("Vitamin A (kIU)", 5.0));
	    nutrients.add(new Nutrient ("Vitamin B1 (mg)", 1.8));
	    nutrients.add(new Nutrient ("Vitamin B2 (mg)", 2.7));
	    nutrients.add(new Nutrient ("Niacin (mg)", 18.0));
	    nutrients.add(new Nutrient ("Vitamin C (mg)", 75.0));

	    List<Food> food = new ArrayList<>();
	    food.add(new Food ("Wheat Flour (Enriched)", "10 lb.", 36,
	        new double[] {44.7, 1411, 2, 365, 0, 55.4, 33.3, 441, 0}));
	    food.add(new Food (
	        "Macaroni", "1 lb.", 14.1, new double[] {11.6, 418, 0.7, 54, 0, 3.2, 1.9, 68, 0}));
	    food.add(new Food ("Wheat Cereal (Enriched)", "28 oz.", 24.2,
	        new double[] {11.8, 377, 14.4, 175, 0, 14.4, 8.8, 114, 0}));
	    food.add(new Food (
	        "Corn Flakes", "8 oz.", 7.1, new double[] {11.4, 252, 0.1, 56, 0, 13.5, 2.3, 68, 0}));
	    food.add(new Food (
	        "Corn Meal", "1 lb.", 4.6, new double[] {36.0, 897, 1.7, 99, 30.9, 17.4, 7.9, 106, 0}));
	    food.add(new Food (
	        "Hominy Grits", "24 oz.", 8.5, new double[] {28.6, 680, 0.8, 80, 0, 10.6, 1.6, 110, 0}));
	    food.add(
	        new Food ("Rice", "1 lb.", 7.5, new double[] {21.2, 460, 0.6, 41, 0, 2, 4.8, 60, 0}));
	    food.add(new Food (
	        "Rolled Oats", "1 lb.", 7.1, new double[] {25.3, 907, 5.1, 341, 0, 37.1, 8.9, 64, 0}));
	    food.add(new Food ("White Bread (Enriched)", "1 lb.", 7.9,
	        new double[] {15.0, 488, 2.5, 115, 0, 13.8, 8.5, 126, 0}));
	    food.add(new Food ("Whole Wheat Bread", "1 lb.", 9.1,
	        new double[] {12.2, 484, 2.7, 125, 0, 13.9, 6.4, 160, 0}));
	    food.add(new Food (
	        "Rye Bread", "1 lb.", 9.1, new double[] {12.4, 439, 1.1, 82, 0, 9.9, 3, 66, 0}));
	    food.add(new Food (
	        "Pound Cake", "1 lb.", 24.8, new double[] {8.0, 130, 0.4, 31, 18.9, 2.8, 3, 17, 0}));
	    food.add(new Food (
	        "Soda Crackers", "1 lb.", 15.1, new double[] {12.5, 288, 0.5, 50, 0, 0, 0, 0, 0}));
	    food.add(
	        new Food ("Milk", "1 qt.", 11, new double[] {6.1, 310, 10.5, 18, 16.8, 4, 16, 7, 177}));
	    food.add(new Food ("Evaporated Milk (can)", "14.5 oz.", 6.7,
	        new double[] {8.4, 422, 15.1, 9, 26, 3, 23.5, 11, 60}));
	    food.add(
	        new Food ("Butter", "1 lb.", 30.8, new double[] {10.8, 9, 0.2, 3, 44.2, 0, 0.2, 2, 0}));
	    food.add(new Food (
	        "Oleomargarine", "1 lb.", 16.1, new double[] {20.6, 17, 0.6, 6, 55.8, 0.2, 0, 0, 0}));
	    food.add(new Food (
	        "Eggs", "1 doz.", 32.6, new double[] {2.9, 238, 1.0, 52, 18.6, 2.8, 6.5, 1, 0}));
	    food.add(new Food ("Cheese (Cheddar)", "1 lb.", 24.2,
	        new double[] {7.4, 448, 16.4, 19, 28.1, 0.8, 10.3, 4, 0}));
	    food.add(new Food (
	        "Cream", "1/2 pt.", 14.1, new double[] {3.5, 49, 1.7, 3, 16.9, 0.6, 2.5, 0, 17}));
	    food.add(new Food (
	        "Peanut Butter", "1 lb.", 17.9, new double[] {15.7, 661, 1.0, 48, 0, 9.6, 8.1, 471, 0}));
	    food.add(new Food (
	        "Mayonnaise", "1/2 pt.", 16.7, new double[] {8.6, 18, 0.2, 8, 2.7, 0.4, 0.5, 0, 0}));
	    food.add(new Food ("Crisco", "1 lb.", 20.3, new double[] {20.1, 0, 0, 0, 0, 0, 0, 0, 0}));
	    food.add(new Food ("Lard", "1 lb.", 9.8, new double[] {41.7, 0, 0, 0, 0.2, 0, 0.5, 5, 0}));
	    food.add(new Food (
	        "Sirloin Steak", "1 lb.", 39.6, new double[] {2.9, 166, 0.1, 34, 0.2, 2.1, 2.9, 69, 0}));
	    food.add(new Food (
	        "Round Steak", "1 lb.", 36.4, new double[] {2.2, 214, 0.1, 32, 0.4, 2.5, 2.4, 87, 0}));
	    food.add(
	        new Food ("Rib Roast", "1 lb.", 29.2, new double[] {3.4, 213, 0.1, 33, 0, 0, 2, 0, 0}));
	    food.add(new Food (
	        "Chuck Roast", "1 lb.", 22.6, new double[] {3.6, 309, 0.2, 46, 0.4, 1, 4, 120, 0}));
	    food.add(
	        new Food ("Plate", "1 lb.", 14.6, new double[] {8.5, 404, 0.2, 62, 0, 0.9, 0, 0, 0}));
	    food.add(new Food ("Liver (Beef)", "1 lb.", 26.8,
	        new double[] {2.2, 333, 0.2, 139, 169.2, 6.4, 50.8, 316, 525}));
	    food.add(new Food (
	        "Leg of Lamb", "1 lb.", 27.6, new double[] {3.1, 245, 0.1, 20, 0, 2.8, 3.9, 86, 0}));
	    food.add(new Food (
	        "Lamb Chops (Rib)", "1 lb.", 36.6, new double[] {3.3, 140, 0.1, 15, 0, 1.7, 2.7, 54, 0}));
	    food.add(new Food (
	        "Pork Chops", "1 lb.", 30.7, new double[] {3.5, 196, 0.2, 30, 0, 17.4, 2.7, 60, 0}));
	    food.add(new Food (
	        "Pork Loin Roast", "1 lb.", 24.2, new double[] {4.4, 249, 0.3, 37, 0, 18.2, 3.6, 79, 0}));
	    food.add(new Food (
	        "Bacon", "1 lb.", 25.6, new double[] {10.4, 152, 0.2, 23, 0, 1.8, 1.8, 71, 0}));
	    food.add(new Food (
	        "Ham, smoked", "1 lb.", 27.4, new double[] {6.7, 212, 0.2, 31, 0, 9.9, 3.3, 50, 0}));
	    food.add(new Food (
	        "Salt Pork", "1 lb.", 16, new double[] {18.8, 164, 0.1, 26, 0, 1.4, 1.8, 0, 0}));
	    food.add(new Food ("Roasting Chicken", "1 lb.", 30.3,
	        new double[] {1.8, 184, 0.1, 30, 0.1, 0.9, 1.8, 68, 46}));
	    food.add(new Food (
	        "Veal Cutlets", "1 lb.", 42.3, new double[] {1.7, 156, 0.1, 24, 0, 1.4, 2.4, 57, 0}));
	    food.add(new Food (
	        "Salmon, Pink (can)", "16 oz.", 13, new double[] {5.8, 705, 6.8, 45, 3.5, 1, 4.9, 209, 0}));
	    food.add(new Food (
	        "Apples", "1 lb.", 4.4, new double[] {5.8, 27, 0.5, 36, 7.3, 3.6, 2.7, 5, 544}));
	    food.add(new Food (
	        "Bananas", "1 lb.", 6.1, new double[] {4.9, 60, 0.4, 30, 17.4, 2.5, 3.5, 28, 498}));
	    food.add(
	        new Food ("Lemons", "1 doz.", 26, new double[] {1.0, 21, 0.5, 14, 0, 0.5, 0, 4, 952}));
	    food.add(new Food (
	        "Oranges", "1 doz.", 30.9, new double[] {2.2, 40, 1.1, 18, 11.1, 3.6, 1.3, 10, 1998}));
	    food.add(new Food (
	        "Green Beans", "1 lb.", 7.1, new double[] {2.4, 138, 3.7, 80, 69, 4.3, 5.8, 37, 862}));
	    food.add(new Food (
	        "Cabbage", "1 lb.", 3.7, new double[] {2.6, 125, 4.0, 36, 7.2, 9, 4.5, 26, 5369}));
	    food.add(new Food (
	        "Carrots", "1 bunch", 4.7, new double[] {2.7, 73, 2.8, 43, 188.5, 6.1, 4.3, 89, 608}));
	    food.add(new Food (
	        "Celery", "1 stalk", 7.3, new double[] {0.9, 51, 3.0, 23, 0.9, 1.4, 1.4, 9, 313}));
	    food.add(new Food (
	        "Lettuce", "1 head", 8.2, new double[] {0.4, 27, 1.1, 22, 112.4, 1.8, 3.4, 11, 449}));
	    food.add(new Food (
	        "Onions", "1 lb.", 3.6, new double[] {5.8, 166, 3.8, 59, 16.6, 4.7, 5.9, 21, 1184}));
	    food.add(new Food (
	        "Potatoes", "15 lb.", 34, new double[] {14.3, 336, 1.8, 118, 6.7, 29.4, 7.1, 198, 2522}));
	    food.add(new Food (
	        "Spinach", "1 lb.", 8.1, new double[] {1.1, 106, 0, 138, 918.4, 5.7, 13.8, 33, 2755}));
	    food.add(new Food ("Sweet Potatoes", "1 lb.", 5.1,
	        new double[] {9.6, 138, 2.7, 54, 290.7, 8.4, 5.4, 83, 1912}));
	    food.add(new Food ("Peaches (can)", "No. 2 1/2", 16.8,
	        new double[] {3.7, 20, 0.4, 10, 21.5, 0.5, 1, 31, 196}));
	    food.add(new Food (
	        "Pears (can)", "No. 2 1/2", 20.4, new double[] {3.0, 8, 0.3, 8, 0.8, 0.8, 0.8, 5, 81}));
	    food.add(new Food (
	        "Pineapple (can)", "No. 2 1/2", 21.3, new double[] {2.4, 16, 0.4, 8, 2, 2.8, 0.8, 7, 399}));
	    food.add(new Food ("Asparagus (can)", "No. 2", 27.7,
	        new double[] {0.4, 33, 0.3, 12, 16.3, 1.4, 2.1, 17, 272}));
	    food.add(new Food (
	        "Green Beans (can)", "No. 2", 10, new double[] {1.0, 54, 2, 65, 53.9, 1.6, 4.3, 32, 431}));
	    food.add(new Food ("Pork and Beans (can)", "16 oz.", 7.1,
	        new double[] {7.5, 364, 4, 134, 3.5, 8.3, 7.7, 56, 0}));
	    food.add(new Food (
	        "Corn (can)", "No. 2", 10.4, new double[] {5.2, 136, 0.2, 16, 12, 1.6, 2.7, 42, 218}));
	    food.add(new Food (
	        "Peas (can)", "No. 2", 13.8, new double[] {2.3, 136, 0.6, 45, 34.9, 4.9, 2.5, 37, 370}));
	    food.add(new Food (
	        "Tomatoes (can)", "No. 2", 8.6, new double[] {1.3, 63, 0.7, 38, 53.2, 3.4, 2.5, 36, 1253}));
	    food.add(new Food ("Tomato Soup (can)", "10 1/2 oz.", 7.6,
	        new double[] {1.6, 71, 0.6, 43, 57.9, 3.5, 2.4, 67, 862}));
	    food.add(new Food (
	        "Peaches, Dried", "1 lb.", 15.7, new double[] {8.5, 87, 1.7, 173, 86.8, 1.2, 4.3, 55, 57}));
	    food.add(new Food (
	        "Prunes, Dried", "1 lb.", 9, new double[] {12.8, 99, 2.5, 154, 85.7, 3.9, 4.3, 65, 257}));
	    food.add(new Food ("Raisins, Dried", "15 oz.", 9.4,
	        new double[] {13.5, 104, 2.5, 136, 4.5, 6.3, 1.4, 24, 136}));
	    food.add(new Food (
	        "Peas, Dried", "1 lb.", 7.9, new double[] {20.0, 1367, 4.2, 345, 2.9, 28.7, 18.4, 162, 0}));
	    food.add(new Food ("Lima Beans, Dried", "1 lb.", 8.9,
	        new double[] {17.4, 1055, 3.7, 459, 5.1, 26.9, 38.2, 93, 0}));
	    food.add(new Food ("Navy Beans, Dried", "1 lb.", 5.9,
	        new double[] {26.9, 1691, 11.4, 792, 0, 38.4, 24.6, 217, 0}));
	    food.add(new Food ("Coffee", "1 lb.", 22.4, new double[] {0, 0, 0, 0, 0, 4, 5.1, 50, 0}));
	    food.add(new Food ("Tea", "1/4 lb.", 17.4, new double[] {0, 0, 0, 0, 0, 0, 2.3, 42, 0}));
	    food.add(
	        new Food ("Cocoa", "8 oz.", 8.6, new double[] {8.7, 237, 3, 72, 0, 2, 11.9, 40, 0}));
	    food.add(new Food (
	        "Chocolate", "8 oz.", 16.2, new double[] {8.0, 77, 1.3, 39, 0, 0.9, 3.4, 14, 0}));
	    food.add(new Food ("Sugar", "10 lb.", 51.7, new double[] {34.9, 0, 0, 0, 0, 0, 0, 0, 0}));
	    food.add(new Food (
	        "Corn Syrup", "24 oz.", 13.7, new double[] {14.7, 0, 0.5, 74, 0, 0, 0, 5, 0}));
	    food.add(new Food (
	        "Molasses", "18 oz.", 13.6, new double[] {9.0, 0, 10.3, 244, 0, 1.9, 7.5, 146, 0}));
	    food.add(new Food ("Strawberry Preserves", "1 lb.", 20.5,
	        new double[] {6.4, 11, 0.4, 7, 0.2, 0.2, 0.4, 3, 0}));

		DietProblem problem = new DietProblem(nutrients,food);
		problem.define();
		Solution solution = problem.minimize();
		problem.showSolution(solution);
	}
	
}