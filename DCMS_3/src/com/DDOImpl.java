package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

@WebService(endpointInterface="com.DCMSInterface")

public class DDOImpl {

	private static int ID = 10000;
	private LogManager logger = null;
	public static Map<String,List<Record>> database=new HashMap<String,List<Record>>();
	List<Record> records;
	Record recobj;
	
	 public DDOImpl() throws IOException 
	    {
	    	super();
			logger = new LogManager("ddo-server.log");
		}

	    public synchronized int genID()
		{
			return ID++;
		}
	    
	    public String createTRecord(String ManagerID, String firstName, String lastName, String address, String phone,
				String specialization, String location) 
		{
			try{	
				String key=lastName.substring(0,1);
				String id = "TR"+genID();
				recobj=new TeacherRecord(firstName, lastName, address, phone, specialization, location, id);
				
				synchronized(this) {
					if(DDOImpl.database.containsKey(key)){
						records=DDOImpl.database.get(key);
						records.add(recobj);
						DDOImpl.database.put(key, records);
					}
					else{
						records=new ArrayList<Record>();
						records.add(recobj);
						DDOImpl.database.put(key, records);
					}
					logger.writeLog("Inserted Teacher Record Number : "+ ((TeacherRecord)recobj).Record_ID);
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.writeLog("Error occured while trying to insert teacher record number : "+((TeacherRecord)recobj).Record_ID);
				return "Failed";
			}
			return "Success";
		}

		public String createSRecord(String ManagerID, String firstName, String lastName, String courseRegistered,
				String status, String statusDate)
		{
			try{	
				String key=lastName.substring(0,1);
				recobj=new StudentRecord(firstName, lastName, "SR"+genID(), courseRegistered, status, statusDate);
				
				synchronized(this){
					if(database.containsKey(key)){
						records=database.get(key);
						records.add(recobj);
						database.put(key, records);
					
					}
					else{
						records=new ArrayList<Record>();
						records.add(recobj);
						database.put(key, records);
					}
					logger.writeLog("Inserted Student Record Number : "+((StudentRecord)recobj).Record_ID);
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.writeLog("Error occured while trying to insert student record number : "+((StudentRecord)recobj).Record_ID);
				return "Failed";
			}
			return "Success";
		}
	
}
