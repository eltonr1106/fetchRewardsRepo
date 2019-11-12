package com.fetchRewards.example.model;

public class VersionHolder {

	private String version1;
	private String version2;
	
	public VersionHolder() {
		
	}
	
	public VersionHolder(String p_version1, String p_version2) {
		this.version1 = p_version1;
		this.version2 = p_version2;
	}

	/**
	 * @return the version1
	 */
	public String getVersion1() {
		return version1;
	}

	/**
	 * @param version1 the version1 to set
	 */
	public void setVersion1(String version1) {
		this.version1 = version1;
	}

	/**
	 * @return the version2
	 */
	public String getVersion2() {
		return version2;
	}

	/**
	 * @param version2 the version2 to set
	 */
	public void setVersion2(String version2) {
		this.version2 = version2;
	}
}
