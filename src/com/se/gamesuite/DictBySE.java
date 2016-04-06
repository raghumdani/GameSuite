package com.se.gamesuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class DictBySE {
	private int numberOfWords;
	private TreeSet<String> tm;
	private ArrayList<String> words;
	private String wordSelected;
	public final int CHAR_SIZE = 256;
	
	public DictBySE() {
		//This is the constructor
		tm = new TreeSet<String>();
		words = new ArrayList<String>();
		wordSelected = "";
	}
	
	//Overloaded constructor
	public void addFile(BufferedReader br) {
		try {
		    for(String line = br.readLine(); line != null; line = br.readLine()) {
		       tm.add(line);
		       words.add(line);
		    }
		    
		} catch(IOException e) {
			e.printStackTrace();
		}
		numberOfWords = words.size();
	}
	
	/*
	 * This function shuffles the word and 
	 * returns the array of string
	 * any meaningful word qualifies to be a right 
	 * answer .. Yo Yo
	 */
	public String getShuffledWord() {
		if(numberOfWords == 0) {
			System.out.println("Number of Words is ZERO/ DO see LOGCAT PLEASE.");
			return "Please Make sure Dictionary is not empty";
		} 
		Random rand = new Random();
		String wordSelected = "";
		//Select a random untill you find some word
		// This is usually fast
		while(wordSelected.length() < 5) {
			wordSelected = words.get(rand.nextInt(numberOfWords));
		}
		
		this.wordSelected = wordSelected;
		return scramble(rand, wordSelected);
	}
	
	public boolean validateString(String inputByUser) {
		int n = inputByUser.length();
		
		//Check if number of characters are same
		if(n != wordSelected.length()) return false;
		
		//Now hash the characters and check if hashes are
		//Equal - This is to make sure, characters are 
		// same as in the input
		
		int hashOfSelectedWord[] = new int[CHAR_SIZE];
		int hashOfInputWord[] = new int[CHAR_SIZE];
		
		//Convert into arrays
		char s[] = wordSelected.toCharArray();
		char in[] = inputByUser.toCharArray();
		
		for(int i = 0; i < s.length; ++i) {
			if(isUpper(s[i])) {
				s[i] = lower(s[i]);
			}
		}
		
		for(int i = 0; i < in.length; ++i) {
			if(isUpper(in[i])) {
				in[i] = lower(in[i]);
			}
		}
		
		for(int i = 0; i < s.length; ++i) {
			hashOfSelectedWord[charToInt(s[i])]++;
		}
		for(int i = 0; i < in.length; ++i) {
			hashOfInputWord[charToInt(in[i])]++;
		}
		
		for(int i = 0; i < CHAR_SIZE; ++i) {
			if(hashOfSelectedWord[i] != hashOfInputWord[i]) {
				return false;
			}
		}
		
		//Check if this word is meaningful
		if(tm.contains(inputByUser)) {
			return true;
		}
		return false;
	}
	
	//check if character is uppercase
	public boolean isUpper(char ch) {
		return (int)ch >= (int)'A' && (int)ch <= (int)'Z';
	}
	
	//Check if character is lowercase
	public char lower(char ch) {
		int x = (int)ch - 'A';
		return (char)('a' + x);
	}
	
	// Convert a character into int value
	int charToInt(char x) {
		return (int)x;
	}
	
	//Shufle the characters in a string
	private String scramble(Random r, String input) {
		char s[] = input.toCharArray();
		
		//Fisher Yates Shuffle Algorithm
		for(int i = 0; i < s.length - 1; ++i) {
			int j = r.nextInt(s.length - 1);
			//swapping the letters
			char temp = s[j]; s[j] = s[i]; s[i] = temp;
		}
		
		return new String(s);
	}
}
