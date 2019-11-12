package com.fetchRewards.example.dao;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.stereotype.Repository;


@Repository("defaultImpl")
public class VersionCompImplWithAdditionalChecks implements VersionComp {
	public class VersionCompImplWithAdditionalChecksException extends RuntimeException {
		VersionCompImplWithAdditionalChecksException(String p_str) {
			super("Improper version number " + p_str);
		}
	}

	/**
	 * More rigorous error checking than the simple case. We throw errors if 
	 * any of the strings contain non numeric characters or negative numbers.
	 * This involves 2 scans. Once we scan the strings and store the numbers
	 * in arraylists and then we scan the individual arraylists.
	 * 
	 */
	
	@Override
	public String compareStrings(String p_str1, String p_str2) {
		
		StringTokenizer st1 = new StringTokenizer(p_str1,".");
		StringTokenizer st2 = new StringTokenizer(p_str2,".");
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		int numElements1 = 0;
		int numElements2 = 0;
		String result = null;
		try {
			numElements1 = addToList(st1, list1);
		}
		catch (NumberFormatException ex) {
			throw new VersionCompImplWithAdditionalChecksException(p_str1);
		}
		try {
			numElements2 = addToList(st2, list2);
		}
		catch (NumberFormatException ex) {
			throw new VersionCompImplWithAdditionalChecksException(p_str2);
		}
		/**
		 * If number of elements in list1 is greater than number of elements in list2
		 * we compare each element from list1 to list2 till we reach number of 
		 * elements in list2. If integer1 from list1 is greater than integer2 from 
		 * list2 that implies that string1 is after string2 and vice-versa if integer1 is 
		 * less than integer2. If they are equal then we go to the next element in
		 * the list. If we reach the end of the for loop that implies that string1 is 
		 * after string2 provided the extra chars in string1 are not all 0s. If they are
		 * 0s it would imply that they are equal.
		 */
		if (numElements1 > numElements2) {				
			// Example 1.4.1.3 and 1.3.4 or 2.5.3 and 1.8
			result = loopLists(list1, list2, p_str1, p_str2, numElements2);
			if (result == null) {
				// Example 1.1.0.1 and 1.1 or 1.1.0.0 and 1.1
				boolean foundNonZero = false;
				for (int i=numElements2; i<numElements1; i++) {
					Integer item = list1.get(i);
					if (item != 0) {
						foundNonZero = true;
						break;
					}
				}
				if (foundNonZero) {
					// Example 1.1.0.1 and 1.1
					result = VersionComp.getResult(p_str1, p_str2, "\"after\"");
				}
				else {
					// Example 1.1.0.0 and 1.1
					result = VersionComp.getResult(p_str1, p_str2, "\"equal\"");
				}
			}

		}
		else {	
			/**
			 * Will take care when numElements1 <= numElements2
			 * Similar logic as above
			 */
			// Example 1.5 and 2.1.4 or 2.5.3 and 2.7.1.5 or 1.2 and 2.4 or 1.2 and 1.2
			result = loopLists(list1, list2, p_str1, p_str2, numElements1);
			if (result == null) {
				// Example 2.2 and 2.2.0.0 or 2.2 and 2.2.1.3
				boolean foundNonZero = false;
				// We won't get into below logic if numElements1 == numElements2
				for (int i=numElements1; i<numElements2; i++) {
					Integer item = list2.get(i);
					if (item != 0) {
						foundNonZero = true;
						break;
					}
				}
				if (foundNonZero) {
					// Example 2.2 and 2.2.1.3
					result = VersionComp.getResult(p_str1, p_str2, "\"before\"");
				}
				else {
					// Example 2.2 and 2.2.0.0
					result = VersionComp.getResult(p_str1, p_str2, "\"equal\"");
				}
			}				
		}
						
		return result;

	}
	
	/**
	 * Method to add separate each numeric element and add it to the list.
	 * Numbers greater than or equal to zero are added to the list. If
	 * we encounter a negative number we throw a NumberFormatException.
	 * @param p_tokenizer
	 * @param p_list
	 * @return
	 * @throws NumberFormatException
	 */
	private int addToList(StringTokenizer p_tokenizer, ArrayList<Integer> p_list) 
			throws NumberFormatException{
		int numTokens = 0;
		while(p_tokenizer.hasMoreTokens()) {
			String str = p_tokenizer.nextToken();
			Integer i = Integer.valueOf(str);
			if (i >= 0) {
				p_list.add(i);
				numTokens++;
			}
			else {
				throw new NumberFormatException();
			}			
		}
		return numTokens;
	}
	
	
	
	/**
	 * Helper method to loop through the lists for a given number of elements. 
	 * String1 > string2 if integer1 > integer2 and vice-versa. If all elements in the 
	 * lists are equal, we return null string.
	 * @param p_list1
	 * @param p_list2
	 * @param p_str1
	 * @param p_str2
	 * @param p_num
	 * @return
	 */
	private String loopLists(ArrayList<Integer> p_list1, ArrayList<Integer> p_list2,
			String p_str1, String p_str2, int p_num) {
		String result = null;
		for (int i=0; i<p_num; i++) { 
			Integer integer1 = (Integer)p_list1.get(i);
			Integer integer2 = (Integer)p_list2.get(i);
			if (integer1 > integer2) {
				result = VersionComp.getResult(p_str1, p_str2, "\"after\"");
				break;
			}
			else if (integer1 < integer2) {
				result = VersionComp.getResult(p_str1, p_str2, "\"before\"");
				break;
			}
		}
		return result;	
	}
}
