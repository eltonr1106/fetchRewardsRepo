package com.fetchRewards.example.dao;

public interface VersionComp {

	public String compareStrings(String p_str1, String p_str2);
	
	
	/**
	 * Obtains the resultant string for a condition.
	 * @param p_str1
	 * @param p_str2
	 * @param p_cond
	 * @return
	 */
	static String getResult(String p_str1, String p_str2, String p_cond) {
		StringBuilder sb = new StringBuilder();
		sb.append(p_str1);
		sb.append(" is ");		
		sb.append(p_cond);
		sb.append(" ");
		sb.append(p_str2);
		return sb.toString();
	}
	
}
