package com.citrix.ceip;

public interface DataFetcher {
	
	/**
	 * Get CIS data by sql query and convert it to Model entity and save them to DB
	 */
	void getCISData() throws Exception;
	
	String getAppName();
	
}
