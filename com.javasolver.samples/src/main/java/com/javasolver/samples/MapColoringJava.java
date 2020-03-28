package com.javasolver.samples;

import java.util.ArrayList;

/**
 * A map-coloring problem involves choosing colors for the countries on a map in
 * such a way that at most four colors are used and no two neighboring countries
 * are the same color. For our example, we will consider six countries: Belgium,
 * Denmark, France, Germany, Netherlands, and Luxembourg. The colors can be
 * blue, white, red or green.
 */

public class MapColoringJava {

	static String[] colors = { "red", "green", "blue", "yellow" };
	static String[] countryNames = {"Belgium","Denmark","France","Germany","Netherlands","Luxembourg"};
	
	class Country {
		String name;
		String color;
		ArrayList<String> neighbours = new ArrayList<String>();
		
		public Country(String name, String ...neighbours) {
			this.name = name;
			this.color = null;
			for(String neighbour : neighbours) {
				this.neighbours.add(neighbour);
			}
		}
		
		public boolean isNeighbour(Country country) {
			for(int i = 0; i < neighbours.size(); i++) {
				if (neighbours.get(i).equals(country.name))
					return true;
			}
			return false;
		}
	}
	
	Country[] countries = new Country[countryNames.length];
	
	public void define() {
		countries[0] = new Country("Belgium", "France","Netherlands","Germany");
		countries[1]  = new Country("Denmark", "Germany");
		countries[2]  = new Country("France", "Belgium","Luxembourg","Germany");
		countries[3]  = new Country("Germany", "France","Belgium","Luxembourg","Denmark","Netherlands");
		countries[4]  = new Country("Netherlands", "Belgium","Germany");
		countries[5]  = new Country("Luxembourg", "France","Belgium","Germany");
	}
	
	public void solve() {
		
		for(Country country : countries) {
			for(String color : colors) {
				if (!isColorUsedByNeighbourOf(color,country)) {
					country.color = color;
					System.out.println(country.name + " = " + country.color);
					break;
				}
			}
		}
	}
	
	public boolean isColorUsedByNeighbourOf(String color, Country country) {
		for(Country otherCountry : countries) {
			if (country == otherCountry) continue;
			if (country.isNeighbour(otherCountry) && color.equals(otherCountry.color) ) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		MapColoringJava map = new MapColoringJava();
		map.define();
		map.solve();
	}
}
