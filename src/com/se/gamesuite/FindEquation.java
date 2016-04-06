package com.se.gamesuite;

import java.util.*;

/*
 * Class: To find the equation 
 * which has a real answer. 
 * Generally, choosing any equation randomly
 * with binary operations + , - , /, *
 * Author: IT 2013
 */
public class FindEquation {
	
	private String equation;
	private int answer;
	
	private ArrayList<Integer> arguments;
	public final int MAX_NUM_OF_INTEGERS = 5;
	public final int MIN_VALUE = 3;
	public final int MAX_VALUE = 1000;
	public final int NUM_BIN_OP = 4;
	
	public FindEquation() {
		equation = "";
		answer = 0;
		arguments.clear();
		
		char binaryOperation[] = {'+', '-', '*', '/'};
		//This is where an equation having
		// a valid answer is created
		loop:
		for(int i = 0; i < MAX_NUM_OF_INTEGERS; ++i) {
			int cur = getRandomNumber(MIN_VALUE, MAX_VALUE);
			if(i != 0) {
				char op = binaryOperation[getRandomNumber(0, NUM_BIN_OP)];
				int isValidAnswer = evaluateAnswer(answer, op, cur);
				if(isValidAnswer == -1) {
					//Run this loop again
					--i;
					continue loop;
				}
				answer = isValidAnswer;
				arguments.add(cur);
				equation += op + " " + cur + " ";
			} else {
				answer = cur;
				arguments.add(cur);
				equation += cur + " ";
			}
		    
		}
	}
	
	//Evaluate the answer again
	//Manage All the cases
	public int evaluateAnswer(int ans, char op, int cur) {
		if(op == '+') {
			return ans + cur;
		} else if(op == '-') {
			return ans - cur;
		} else if(op == '*') {
			return ans * cur;
		} else if(op == '/') {
			if(cur == 0 || ans % cur != 0) {
				return -1;
			}
			return ans / cur;
		}
		return ans;
	}
	
	// Get the random number between lo and hi
	public int getRandomNumber(int lo, int hi) {
		Random rand = new Random();
		return rand.nextInt(hi) + lo;
	}
	
	public int getAnswer() {
		return this.answer;
	}
	
	public ArrayList<Integer> getArguments() {
		return this.arguments;
	}
	
	public String getEquation() {
		return this.equation;
	}
}