package com.fetchRewards.example.dao;

import org.springframework.stereotype.Repository;

@Repository("simpleImpl")
public class VersionCompSimpleImpl implements VersionComp {

	public class VersionCompSimpleImplException extends RuntimeException {
		VersionCompSimpleImplException(String p_str) {
			super("Improper version number " + p_str);
		}
	}
	private static final char ZERO = '0';
	private static final char DOT = '.';

	/**
	 * Simple logic wherein we compare each individual number of str1 to that of str2.
	 * If first number of str1 > first number of str2 we say version1 is after version2.
	 * This class ignores that the rest of the version string could have non-numeric 
	 * characters and does not do rigorous checking but there is only one scan done.
	 * 
	 */
	@Override
	public String compareStrings(String p_str1, String p_str2) {

	    int num1 = 0, num2 = 0;
	    int len1 = p_str1.length();
	    int len2 = p_str2.length();
	     
	    for (int i=0,j=0; (i<len1 || j < len2); ) {
	    	//Scan first string
	        while (i < len1 && p_str1.charAt(i) != DOT) {
	        	if (isNumeric(p_str1.charAt(i))){
	        		num1 = num1 * 10 + (p_str1.charAt(i++) - ZERO); 
	        	}
	        	else {
	        		throw new VersionCompSimpleImplException(p_str1);
	        	}	        	
	        } 
	        
	        // Scan second string
	        while (j < len2 && p_str2.charAt(j) != DOT) {	        
	        	if (isNumeric(p_str2.charAt(j))){
	        		num2 = num2 * 10 + (p_str2.charAt(j++) - ZERO); 
	        	}
	        	else {
	        		throw new VersionCompSimpleImplException(p_str2);
	        	}
	        }
	  	  
	        if (num1 < 0) {
	        	throw new VersionCompSimpleImplException(p_str1);
	        }
	        if (num2 < 0) {
	        	throw new VersionCompSimpleImplException(p_str2);
	        }
	
	        if (num1 > num2) { 
	            return VersionComp.getResult(p_str1, p_str2, "\"after\"");
	        }
	        else if (num1 < num2) { 
	        	return VersionComp.getResult(p_str1, p_str2, "\"before\"");
	        }
	        else {
	        	num1 = num2 = 0;
	        	i++;
	        	j++;
	        }	  	         
	    } 
	    return VersionComp.getResult(p_str1, p_str2, "\"equal\"");		
		
	}
	
	/**
	 * Private method to return numeric or not 
	 * @param c
	 * @return
	 */
	private boolean isNumeric(char c) {
		if (c >= '0' && c <= '9') {
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
