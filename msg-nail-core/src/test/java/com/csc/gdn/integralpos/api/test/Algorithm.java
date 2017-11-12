package com.csc.gdn.integralpos.api.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Algorithm {
	
	public static void main(String[] args){
		
		/** Revert string */
		//String input = "abcdefg";
		//String resultOfReverse = reverse(input);
		//System.out.println(input);
		//System.out.println(resultOfReverse);
		
		/** Remove duplicate element */
		//int[] arrayData = {1,2,3,4,5,3,2,1};
		//int[] resultArray = removeDuplicateElement(arrayData);
		//System.out.println(resultArray);
		
				
		/** Bubble Sort */
		int[] arrayData = {1,2,3,4,5,3,2,1};
		int[] arraySorted = bubbleSort(arrayData);
		printArray(arraySorted);
		System.out.println();
		printArray(removeDuplicateElement(arraySorted));
		arraySorted = removeDuplicateElement(arraySorted);
		System.out.println();
		counterDecrease(arraySorted);
	}
	
	public static String reverse(String input){
		char[] s = input.toCharArray();
		int n = s.length;
		int halfLength = n/2;
		for(int i = 0 ; i < halfLength ; i++){
			char temp = s[i];
			s[i] = s[n-i-1];
			s[n-i-1] = temp;
		}
		return new String(s);
	}
	
	public static int[] removeDuplicateElement(int[] arrayInput){
		
		int end = arrayInput.length;
		Set<Integer> setData = new HashSet<Integer>();
		
		for(int i = 0 ; i < arrayInput.length ; i++){
			setData.add(arrayInput[i]);
		}
		
		int[] result = new int[setData.size()]; 
		int index = 0 ;
		Iterator it = setData.iterator();
		while(it.hasNext()){
			result[index] = (int) it.next();
			index++;
		}
		
		return result;
	}
	
	static int[] bubbleSort(int[] a){
		for ( int i = 0 ; i < a.length ; i++){
			for(int j = 0 ; j < a.length - 1 ; j ++){
				if(a[j] < a[i] && j > i){
					swap(a , i , j);
				}
			}
		}
		
		return a;
	}
	
	static int[] swap(int[] a ,int i , int j){
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		
		return a;
	}
	
	static void printArray(int[] array){
		for(int i = 0 ; i < array.length ; i++){
			System.out.print(array[i] + " ");
		}
	}
	
	static void counterDecrease(int[] array){
		for ( int i = array.length -1  ; i >= 0; i--){
			System.out.print(array[i] + "");
		}
	}
	


}
