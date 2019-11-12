package com.fetchRewards.example.dao;

import com.fetchRewards.example.dao.VersionCompSimpleImpl.VersionCompSimpleImplException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Test;



class VersionCompSimpleImplTest {

	private VersionComp tester = new VersionCompSimpleImpl();
	@Test
	void testEqualVersions() {
		checkResult("1.2.3", "1.2.3", '=');
		checkResult("4.5.6.7.9", "4.5.6.7.9", '=');
		checkResult("4.5.0", "4.5", '=');
		checkResult("1.5.2", "1.5.2.0", '=');
		checkResult("0.1", "0.1.", '=');
		checkResult("1", "1.0", '=');
		checkResult("1.2.0", "1.2", '=');
	}
	
	@Test
	void testAfterVersions() {
		checkResult("5.1", "0.1.", '>');
		checkResult("8.5.6.7.9", "4.5.6.7.9", '>');
		checkResult("100.5.6.7.9", "9.5.6.7.9", '>');
		checkResult("1.2.3.4.5.0", "1.2", '>');	
		checkResult("1.2..3", "1.2", '>');
		checkResult("1.2", "1..3", '>');					
	}
	
	@Test
	void testBeforeVersions() {
		checkResult("1.0", "2.0", '<');
		checkResult("1.2.3.4.5.6.7.8.9", "9.8.7.6.5.4.3.2.1", '<'); 
		checkResult("1", "1.0.2.3.4.5.10", '<');
		checkResult("", "4.5.6.7.9", '<'); 
		checkResult("1.0", "2.-3", '<');
		checkResult("1./0", "2.2", '<');
		
	}
	
	@Test 
	void testError() {
		checkError("abcd", "0.1.2.3");
		checkError("0.1.2.3", "pqrs");
		checkError("-1.0", "2.0");
		checkError("1.0", "2/3");
		
	}

	private void checkError(String p_str1, String p_str2) {
		try {
			tester.compareStrings(p_str1, p_str2);		
			assertThrows(VersionCompSimpleImplException.class, () -> {
			});
		}
		catch (VersionCompSimpleImplException e) {
			System.out.println(e.toString() + " for strings "+ p_str1					
					 		+ " and " + p_str2); 
		}
		
	}
	private void checkResult(String p_str1, String p_str2, char p_code) {
		String result = tester.compareStrings(p_str1, p_str2);
		System.out.println(result);
		assertEquals(buildResultString(p_str1, p_str2, p_code), result);
	
	
	}
	
	
	private final String buildResultString(String p_str1, String p_str2, char code) {
		StringBuilder sb = new StringBuilder();
		sb.append(p_str1);
		sb.append(" is ");
		sb.append("");
		if (code == '>')
			sb.append("\"after\"");
		else if (code == '<')
			sb.append("\"before\"");
		else if (code == '=') 
			sb.append("\"equal\"");
		else return null;
		sb.append(" ");
		sb.append(p_str2);
		return sb.toString();
	}

}
