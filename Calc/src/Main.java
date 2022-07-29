import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Calculator
// This is my first project in Java
// it takes full equation - operators +, -, *, /, () are allowed
// double parentheses ex. ((2+1)+3) not allowed 
// the program checks whether input contains digits and operators, does not check for the accuracy of the expression..


	public class Main {
		//array to store all operators available in the program
	static char[] ops = {'+', '-', '/', '*'};
	
	//boolean method
	static boolean isItChar(char i){
		//new boolean to store the answer
		boolean contains = false;
		for(char c : ops) {  //for every character in ops[] 
			if(c==i) {   //if the char i is the same as one in the ops[] means it is an operator, thus assign true and break out of the loop
				contains=true;
				break;
			}
		}
		return contains;
	}
	
	//method to check whether the equation contains parentheses with return type Integer list
	//equation has to be in the form of String array
	static List<Integer> hasPths(String[] equation) {
		//create new list to store the position of the parentheses
	List<Integer> posPths = new ArrayList<Integer>();
	//loop through the equation
	for(int i=0; i<equation.length; i++) {
		//if the parenthesis is found, save it in the list
		if(equation[i].contains("(") || equation[i].contains(")")) {
			posPths.add(i);
		}
		//if there is 2 parentheses in the list -> () -> break the loop 
		if(posPths.size()==2) {
			break;
		}
	}
	//return the list
	return posPths;
	}
	
	//method for checking whether the equation contains * or /
	static List<Integer> hasDorM(String[] equation) {
	List<Integer> posOfDM = new ArrayList<Integer>();
	for(int i=0; i<equation.length; i++) {
		//adds the indexes of all * and / operators
		if(equation[i].contains("*") || equation[i].contains("/")) {
			posOfDM.add(i);
		}
	}
	//return the list
	return posOfDM;
	}
	
	//to check whether the entered equation contains any letter or is empty
	static boolean containsAnyLetterOrIsEmpty(String str) {
		//check if it is empty
		if(str == null || str.isEmpty()) {
			return true;
		}
		//check for letters
		for(int i=0; i<str.length(); i++) {
			if(Character.isLetter(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	//to check whether the entered equation contains only allowed operators and digits
	static boolean containsIllegalChar(String str) {
		int j=0;
		for(int i=0; i<str.length(); i++) {
			if(Character.isDigit(str.charAt(i))) {
				j += 1;
			} else if(isItChar(str.charAt(i))) {
				j += 1;
			} else if(str.charAt(i) == '(') {
				j += 1;
			} else if(str.charAt(i) == ')') {
				j += 1;
			}
			
		}
		//if the element is a digit or an allowed operator variable j is incremented
		//if all the elements in the equation are valid, value of j will be the same as the length of the equation
		if(j==str.length()) {
			return false;
		} else {
		return true;
		}
	}

	public static void main(String[] args) {
		
		//Input the equation
		System.out.println("Enter a equation (only +, -, *, / allowed");
		try (Scanner exp = new Scanner(System.in)) {
			String calculate = exp.nextLine();
			
			//remove whitespace from the entered String
			String calc = calculate.replaceAll(" ", "");
			
			
			if(containsAnyLetterOrIsEmpty(calc)) {  //checking if it contains any letter or is empty
				System.out.println("Incorrect input.");
			} else if(containsIllegalChar(calc)) {  //making sure only digits and operators are in the String
				System.out.println("Incorrect input.");
			}  else {
				
			//separate numbers and operators, store it in String array
			String[] sepCalc = calc.split("(?<=[+-/*()])|(?=[+-/*()])");
			
			//new Integer list to check whether the equation contains parentheses through a function
			List<Integer> posPths = new ArrayList<Integer>();
			posPths = hasPths(sepCalc);
		
			//the method returns list with indexes of parentheses in the equation
			//if list is empty then there is no parentheses 
			if(!posPths.isEmpty() == true) { //if the list is not empty
			while(!posPths.isEmpty()) {  
					
					//position of first and last bracket
				    int firstB = posPths.get(0);
				    int lastB = posPths.get(1);
				    
				    //string builder to contain the equation in the parentheses
				    String equas = ""; 
					StringBuilder eq = new StringBuilder(equas);
					
					//add to the string builder through the loop (including parentheses)
				    for(int i=firstB; i<=lastB; i++) {
				    	eq.append(sepCalc[i]);
				    	
				    }
				   //string builder to string
				    String toString = eq.toString();
				    //substring to create equation in the parentheses without them
				    //ex. eq = (2+5),  toEq = 2+5
				    String toEq = toString.substring(1, toString.length()-1);
				   
				    //sol to store solution, then to String solut it so can be replaceable in the main equation  
					int sol = calculate(toEq);
					String solut = String.valueOf(sol);
					
					//replace the equation in the parentheses with solution
					//ex. from: 3*(2+5) to 3*10
					calc = calc.replace(eq, solut);

					//reassign sepCalc to store modified equation  
					sepCalc = calc.split("(?<=[+-/*()])|(?=[+-/*()])");
					//reassign the list that stores the indexes of brackets to check if there is more
					//if there is more the loop will go again
					posPths = hasPths(sepCalc);
					
				}
			}
				//now we know there is no more parentheses so we can move on to multiplying and dividing 
				//create a new Integer list to store positions of * and / 
				List<Integer> posOfDM = new ArrayList<Integer>();
				posOfDM = hasDorM(sepCalc);
				if(!posOfDM.isEmpty()) {  //if the list is not empty:
					
					//this time the list has all of the operators thus the loop with size of the list
					for(int i=0; i<posOfDM.size(); i++) {
						//position of the first operator
						String mid = sepCalc[posOfDM.get(i)];
						//position of the number before it
						String prev = sepCalc[posOfDM.get(i)-1];
						//position of the number after it
						String next = sepCalc[posOfDM.get(i)+1];
						//string builder to store these two numbers and the sign
						String equas = ""; 
						StringBuilder eq = new StringBuilder(equas);
						eq.append(prev);
						eq.append(mid);
						eq.append(next);
						//from string builder to string
						String toString = eq.toString();
						
						//calculate the expression
						int sol = calculate(toString);
						//from int to String to replace it in calc
						String solut = String.valueOf(sol);
						//replace this equation with solution in the main equation
						// ex. 2+5*10  ->  2+50  
						calc = calc.replace(toString, solut);
						
					}
					
					
				}
				//print what's already been done
				System.out.println(calc);
				
				//initialise int to store the solution
				int solution = 0;
				//here the program checks whether there is more to be calculated
				//if it still contains the operators + or - there is more
				if(calc.contains("+")) {
					solution = calculate(calc);
					
				} else if(calc.contains("-")) {
					solution = calculate(calc);
					
				} else {  //if there is no operators means we already have a solution
					solution = Integer.valueOf(calc);
				}
						
				//print the solution
				System.out.println("Solution: " +solution);
			}
		}
		}
	

	private static int calculate(String calc) {
		//initialise sign to store operator, value that will store the first value and sol for solution
		char sign = 0;
		int value = 1;
		int sol=0;
		
		//string builder for assembling a number
		String lastNum = ""; 
		StringBuilder last = new StringBuilder(lastNum);
 
		//ex. 15+14
		for(int i=0; i<calc.length(); i++) { 
			 //checking whether the element is a char or digit
			 boolean isChar = isItChar(calc.charAt(i));
			 
			 
			 if(!isChar) {
				 //if it is not a char append the digit to the string builder
				 //after 2 loops last = 15
				 last.append(calc.charAt(i));
				} else {
					//if it is a sign, then 'last' variable contains a number
					sign = calc.charAt(i); //store a operator
					//this will only run the first time, like with the current example
					//but if there is more, ex. 15+14-7 it will not run, because we will not need 'last', just solution from the first loop, which is 29
					if(value!=sol) {
						value = Integer.parseInt(last.toString());//value == 15
					} 
					//reset 'last'
					last.setLength(0);
					//increase i to find next number
					i++;

					//loop to find the next number, will end if there is no more operators after
					while(i<calc.length()) {
						//if next element in the string is not an operator
						if(isItChar(calc.charAt(i))==false) {
							//add to 'last'
						last.append(calc.charAt(i));
						} else {
							//if it finds operator the loop will break
							break;
						}
						//only increment i if it is lower than the length of the equation
						if(i<calc.length()) {
						i++;
						}
					}
					//decrease i (if the operator was found it will be needed for next loop)
					i--;
					//newNum to store first/second number
					int newNum = Integer.parseInt(last.toString()); //14
					switch(sign) { //switch for the correct solution
					case '+': sol = value+newNum;break;
					case '-': sol = value-newNum;break;
					case '/': sol = value/newNum;break;
					case '*': sol = value*newNum;break;
				}
					//set the first value to solution
					value = sol;
			 
			 
		 }
		 }
		//return solution
		return sol;
	}

}
