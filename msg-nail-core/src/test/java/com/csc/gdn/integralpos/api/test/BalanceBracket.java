package com.csc.gdn.integralpos.api.test;

import java.util.Stack;

public class BalanceBracket {
	
	public static void main(String[] args) {
		String a = "[[(({{}}]]))";
		//String isBalanced = isBalanced(a);
		//System.out.println(isBalanced);
	}

	static boolean isBalanced(String expression) {
		char[] arrayBracket = expression.toCharArray();
		Stack<Character> stack = new Stack<Character>();

		for (char c : arrayBracket) {
			switch (c) {
			case '(':
				stack.push(')');
				break;
			case '{':
				stack.push('}');
				break;
			case '[':
				stack.push(']');
				break;
			default:
				if (stack.isEmpty() || c != stack.peek())
					return false;
				stack.pop();
			}
		}

		return stack.isEmpty();
	}
}
