package com.javasolver.samples;

import java.util.ArrayList;

public class SyllablesInNumbers {
	
	class NameAndNumbers {
		String name;
		int syllables;
		
		public NameAndNumbers(String name,int syllables) {
			this.name = name;
			this.syllables = syllables;
		}
	}
	
	ArrayList<NameAndNumbers> nameAndNumbers = new ArrayList<NameAndNumbers>();
	
	public SyllablesInNumbers() {
		fillOutNameAndNumbers();
	}
	
	
	void add(String name,int syllables) {
		nameAndNumbers.add(new NameAndNumbers(name,syllables));
	}
	
	public String getName(int i) {
		return nameAndNumbers.get(i).name;
	}
	
	public int getSyllables(int i) {
		return nameAndNumbers.get(i).syllables;
	}
	
	public void fillOutNameAndNumbers() {
//		add("hundred",2);
//		add("thousand",2);
//		add("plus",1);
//		add("equals",2);
		
		// 0-9
		String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}; 
		int[] digitSyllables = {2,1,1,1,1,1,1,2,1,1};
		for(int i=0; i<digits.length; i++) {
			String digitName = digits[i];
			int digitS = digitSyllables[i];
			add(digitName,digitS);
		}
		
		// 10-19
		String[] teens = {"ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen" };
		int[] teenSyllables = { 1,3,1,2,2,2,2,3,2,2 };
		for(int i = 0; i < teens.length; i++) {
			String teenName = teens[i];
			int teenS = teenSyllables[i];
			add(teenName,teenS);
		}

		// 20-99
        String[] tens = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
        int[] tenSyllables = { 2,2,2,2,2,3,2,2 };
		
		for(int i = 0; i < tens.length; i++) {
			String tenName = tens[i];
			int tenS = tenSyllables[i];
			add(tenName,tenS);
			for(int j=1; j<digits.length; j++) {
				String name = tenName + " " + digits[j];
				int syllables = tenS + digitSyllables[j];
				add(name, syllables);
			}
		}
		
		// 100-999
		String hundredName = "hundred";
		int hundredS = 2;
		for(int i=1; i<digits.length; i++) {
			// "one" hundred
			String name = digits[i] + " " + hundredName;
			int syllables = digitSyllables[i] + hundredS;
			add(name,syllables); 
			// "one" hundred 1, 2, ..., 9
			for(int j=1; j<digits.length; j++) {
				String digitName = name + " " + digits[j];
				int digitS = syllables + digitSyllables[j];
				add(digitName,digitS);
			}
			// "one" hundred 10, 11, ..., 19
			for(int j=0; j<teens.length; j++) {
				String teenName = name + " " + teens[j];
				int teenS = syllables + teenSyllables[j];
				add(teenName,teenS);
			}
			// "one" hundred 20, 21, ..., 29, 30, 31,..., 99
			for(int j = 0; j < tens.length; j++) {
				String tenName = name + " " + tens[j];
				int tenS = syllables + tenSyllables[j];
				add(tenName,tenS);
				for(int k=1; k<digits.length; k++) {
					String digitName = tenName + " " + digits[k];
					int digitS = tenS + digitSyllables[k];
					add(digitName, digitS);
				}
			}
		}
	}
	
	public int getMax() {
		return nameAndNumbers.size();
	}
	
	public void print() {
		for(int i = 0; i < nameAndNumbers.size(); i++) {
			NameAndNumbers element = nameAndNumbers.get(i);
			System.out.println(i + ": " + element.name + " " + element.syllables);
		}
	}
	
	public int[] getAllSyllables() {
		int[] array = new int[nameAndNumbers.size()];
		for(int i = 0; i < nameAndNumbers.size(); i++) {
			NameAndNumbers element = nameAndNumbers.get(i);
			array[i] = element.syllables;
		}
		return array;
		
	}

	public static void main(String[] args) {
		SyllablesInNumbers syllablesInNumbers = new SyllablesInNumbers();
		syllablesInNumbers.print();
	}

}
