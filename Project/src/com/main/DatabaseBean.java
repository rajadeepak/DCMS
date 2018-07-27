package com.main;

import java.util.HashMap;

public class DatabaseBean {
	
	private static DatabaseBean instance = new DatabaseBean();
	
	private DatabaseBean(){}
	
	public static DatabaseBean getInstance(){
		return instance;
	}
	
	public HashMap<String,Long> lastHeartbeat = new HashMap<String,Long>();
	
}
