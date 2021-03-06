package com.se.gamesuite;

import java.util.*;

import android.util.Log;

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
	
	private String TAG = "FindEquation";
	private ArrayList<Integer> arguments;
	public final int MAX_NUM_OF_INTEGERS = 3;
	public final int MIN_VALUE = 3;
	public final int MAX_VALUE = 1000;
	public final int NUM_BIN_OP = 4;
	public final int NOT_GOOD = (~0);
	
	public FindEquation() {
		equation = "";
		answer = 0;
		arguments = new ArrayList<Integer>();
		
		char binaryOperation[] = {'+', '-', '*', '/'};
		//This is where an equation having
		// a valid answer is created
		for(int i = 0; i < MAX_NUM_OF_INTEGERS; ++i) {
			int cur = getRandomNumber(MIN_VALUE, MAX_VALUE);
			
			Log.d(TAG, "The current random number is " + cur);
			if(i != 0) {
				int nn = getRandomNumber(0, NUM_BIN_OP);
				
				Log.d(TAG,  "The current operation is " + nn);
				char op = binaryOperation[nn];
				int isValidAnswer = evaluateAnswer(answer, op, cur);
				if(isValidAnswer == -1) {
					//Run this loop again
					--i;
					continue;
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
	
	// This function is used to check the validity of
	// The input field
	
	private boolean isNotValid(char x) {
		return (x < '0' || x > '9') && 
				(x != '-' && x != '+' && x != '*' && x != '/' && x != ' ' && x != '\t' && x != '\n');
	}
	
	// Check if the answer entered is correct or not
	// The heart of equation checking
	public boolean validateAnswer(String eq) {
		
		int res = calc(eq);
		if(res == NOT_GOOD) return false;
		if(res != this.answer) return false;
		
		Log.d(TAG, "Input String: " + eq);
		String neq = "";
		for(int i = 0; i < eq.length(); ++i) {
			if(eq.charAt(i) < '0' || eq.charAt(i) > '9') {
				if(eq.charAt(i) != '+' && eq.charAt(i) != '-' && eq.charAt(i) != '*' && eq.charAt(i) != '/') {
					continue;
				}
				neq += eq.charAt(i);
			} else {
				neq += eq.charAt(i);
			}
		}
		
		String num = "";
		ArrayList <Integer> arg = new ArrayList <Integer>();
				
		for(int i = 0; i < neq.length(); ++i) {
			if(neq.charAt(i) <= '9' && neq.charAt(i) >= '0') {
				num += neq.charAt(i);
			} else {
				arg.add(Integer.parseInt(num));
				num = "";
			}
		}
		if(num.length() != 0) {
			arg.add(Integer.parseInt(num));
		}
		if(arg.size() != arguments.size() ) {
			return false;
		}
		for(int i = 0; i < arg.size(); ++i) {
			if(!arguments.contains(arg.get(i))) {
				return false;
			}
		}
		Log.d(TAG, "The evaluated answer is " + res + " whereas the answer of jury is " + this.answer); 
		return true;
	}
	
	//For testing purposes
	public int calc(String eq) {
		for(int i = 0; i < eq.length(); ++i) {
			if(isNotValid(eq.charAt(i))) {
				return NOT_GOOD;
			}
		}
		Log.d(TAG, "Input String: " + eq);
		String neq = "";
		for(int i = 0; i < eq.length(); ++i) {
			if(eq.charAt(i) < '0' || eq.charAt(i) > '9') {
				if(eq.charAt(i) != '+' && eq.charAt(i) != '-' && eq.charAt(i) != '*' && eq.charAt(i) != '/') {
					continue;
				}
				neq += eq.charAt(i);
			} else {
				neq += eq.charAt(i);
			}
		}
		int res = 0, args = 0;
		char curOP = '[';
		String num = "";
		
		for(int i = 0; i < neq.length(); ++i) {
			if(neq.charAt(i) <= '9' && neq.charAt(i) >= '0') {
				num += neq.charAt(i);
			} else {
				if(num.equals("")) {
					return NOT_GOOD;
				}
				if(args == 0) {
					res = Integer.parseInt(num);
					num = "";
				} else {
					res = evaluateAnswer(res, curOP, Integer.parseInt(num));
					num = "";
				}
				
				args++;
				curOP = neq.charAt(i);
			}
		}
		if(num.length() != 0) {
			if(args == 0) {
				res = Integer.parseInt(num);
			} else {
				res = evaluateAnswer(res, curOP, Integer.parseInt(num));
			}
		}
		return res;
	}
	
	// Get the random number between lo and hi
	public int getRandomNumber(int lo, int hi) {
		Random rand = new Random();
		hi = hi - lo;
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