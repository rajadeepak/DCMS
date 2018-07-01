package com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jws.WebService;




@WebService(endpointInterface="com.DCMSInterface")

public class LVLImpl {

	private static int ID = 10000;
	private LogManager logger = null;
	public static Map<String,List<Record>> database=new HashMap<String,List<Record>>();
	List<Record> records;
	Record recobj;
	private int MTLPort = 1412;
    private static int LVLPort = 7875;
    private int DDOPort = 7825;
    ExecutorService exec = Executors.newFixedThreadPool(10);
	 public LVLImpl() throws IOException 
	    {
	    	super();
			logger = new LogManager("lvl-server.log");
		}

	    public synchronized int genID()
		{
			return ID++;
		}
	    
	    public String createTRecord(String ManagerID, String firstName, String lastName, String address, String phone,
				String specialization, String location) 
		{
	    	String id = "";
			try{	
				String key=lastName.substring(0,1);
				id = "TR"+genID();
				recobj=new TeacherRecord(firstName, lastName, address, phone, specialization, location, id);
				
				synchronized(this) {
					if(LVLImpl.database.containsKey(key)){
						records=LVLImpl.database.get(key);
						records.add(recobj);
						LVLImpl.database.put(key, records);
					}
					else{
						records=new ArrayList<Record>();
						records.add(recobj);
						LVLImpl.database.put(key, records);
					}
					logger.writeLog("Inserted Teacher Record Number : "+ ((TeacherRecord)recobj).Record_ID);
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.writeLog("Error occured while trying to insert teacher record number : "+((TeacherRecord)recobj).Record_ID);
				return "Failed";
			}
			return id;
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
		
		public String editRecord(String ManagerID, String recordID, String fieldName, String newValue) 
		{
			boolean flag = false;

			for(Map.Entry<String, List<Record>> entry : database.entrySet())
				for(Record e: entry.getValue())
				{
					if(e.Record_ID.equals(recordID))
					{
						flag = true;
						if(recordID.startsWith("SR"))
						{
							if(fieldName.equalsIgnoreCase("Status"))
							{
								if(!newValue.equalsIgnoreCase("active") && !newValue.equalsIgnoreCase("inactive"))
								{
									logger.writeLog(fieldName +"could not be updated to "+ newValue +" value must be either active/inactive");
									return "Failed";
								}
								else
									((StudentRecord)e).Status=newValue;
							}
							else if(fieldName.equalsIgnoreCase("StatusDate"))
								((StudentRecord)e).date=newValue;
							else if(fieldName.equalsIgnoreCase("CoursesRegistered")) 
								((StudentRecord)e).CoursesRegistered=newValue;
							else 
							{
								logger.writeLog("invalid field:  "+fieldName +" for the StudentRecord");
								return "Failed";
							}
						}
						else 
						{
							if(fieldName.equalsIgnoreCase("location")) 
							{
								if(!newValue.equalsIgnoreCase("lvl") && !newValue.equalsIgnoreCase("mtl") && !newValue.equalsIgnoreCase("ddo"))
								{
									logger.writeLog(fieldName +"could not be updated to "+ newValue +" value must be either mtl/ddo/lvl");
									return "Failed";
								}
								else
									((TeacherRecord)e).Location=newValue;
							}
							else if(fieldName.equalsIgnoreCase("address"))
								((TeacherRecord)e).Address=newValue;
							else if(fieldName.equalsIgnoreCase("phone"))
								((TeacherRecord)e).Phone=newValue;
							else 
							{
								logger.writeLog("invalid field:  "+fieldName +" for the TeacherRecord");
								return "Failed";
							}
						}
						
					}
				}
			if(!flag)
			{
				logger.writeLog("RecordID:  "+recordID +" Not Found in this server");
				return "Failed";
			}
				logger.writeLog("RecordID:  "+recordID +" updated the field"+fieldName+" to new value: "+newValue);
		        return "Success";
		}
		
		public String getRecordCounts(String ManagerID)
		{
			String s="LVL "+LVLImpl.database.size()+" ";
			
			try {
				byte[] message = "getRecordCounts".getBytes();
				byte[] buffer1 = new byte[1000];
				byte[] buffer2 = new byte[1000];
				
				Future<String> mtlResponse = exec.submit(new Callable<String>() {

					public String call() throws Exception {
						DatagramSocket ds = new DatagramSocket();
						InetAddress aHost = InetAddress.getByName("localhost");
						DatagramPacket request = new DatagramPacket(message, message.length, aHost, MTLPort);
						ds.send(request);
						System.out.println("Request sent : "+request);
						DatagramPacket reply1 = new DatagramPacket(buffer1, buffer1.length);
						ds.receive(reply1);
						String response1 = new String(reply1.getData());
						System.out.println("Response received: "+response1);
						String a = response1.trim();
						ds.close();
						return a;
					}
					
					
				});
				
				Future<String> ddoResponse = exec.submit(new Callable<String>() {

					@Override
					public String call() throws Exception {
						DatagramSocket ds = new DatagramSocket();
						InetAddress aHost = InetAddress.getByName("localhost");
						DatagramPacket request = new DatagramPacket(message, message.length, aHost, DDOPort);
						ds.send(request);
						DatagramPacket reply2 = new DatagramPacket(buffer2, buffer2.length);
						ds.receive(reply2);
						String response2= new String(reply2.getData());
						System.out.println("Response received: "+response2);
						ds.close();
						String b = response2.trim();
						return b;
					}
				});
				
				s = s + mtlResponse.get() + ddoResponse.get();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			logger.writeLog("Current system records "+s);
			return s;
		}
		
		public String transferRecord(String managerID, String recordID, String remoteServerName)
		{
			if(!checkRecordID(recordID))
			{
				logger.writeLog("ManagerID: "+managerID+". Transfer Record Failed. Record ID : "+recordID+" Not Found");
				return "failed";
			}
			
			String fullRecord;
			int port;
			fullRecord = fetchRecord(recordID);
			String msg = "transferRecord::" + managerID + "::" + fullRecord;
			
			System.out.println(msg);
			if(remoteServerName.equalsIgnoreCase("MTL"))
				port = MTLPort;
			else
				port = LVLPort;
			
			DatagramSocket ds = null;

			
			try {
				byte[] message = msg.getBytes();
				byte[] buffer = new byte[1000];
				
				ds = new DatagramSocket();
	            InetAddress aHost = InetAddress.getByName("localhost");
	            DatagramPacket request = new DatagramPacket(message, message.length, aHost, port);
	            ds.send(request);
	            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
	            ds.receive(reply);
	            String response = new String(reply.getData());
	            ds.close();
	            
	            if(response.trim().equalsIgnoreCase("success"))
	            	deleteRecord(recordID);
	            
	            else {
	            	logger.writeLog("ManagerID: "+managerID+". Transfer Record Failed. Record ID : "+recordID+" Transfer Failed");
	    			return "failed";
	            }
	            
	            logger.writeLog("ManagerID: "+managerID+". Transfer Record Success. Record ID : "+recordID+" is now in : "+ remoteServerName);
	           
			}catch(SocketException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			finally {
				if(ds!=null)
					ds.close();
			}
			return "success";
			
		}

		public static void main(String[] args) throws Exception {
			// TODO Auto-generated method stub
			DatagramSocket ddo = null;
			try{
				
				ddo = new DatagramSocket(LVLPort);
				while(true){
					String bloop = "";
					byte[] buffer = new byte[1000];
					DatagramPacket request = new DatagramPacket(buffer, buffer.length);
					ddo.receive(request);
					
					if(data(buffer).toString().equals("getRecordCounts"))
						bloop = "LVL "+ String.valueOf(database.size()) + ", "; 
					
					else if(data(buffer).toString().contains("transferRecord"))
					{
						LVLImpl obj = new LVLImpl();
						String bleep = data(buffer).toString();
						String parts[] = bleep.split("::");
						if(parts[2].startsWith("TR"))
							bloop = obj.createTRecord(parts[1], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
						else
							bloop = obj.createSRecord(parts[1], parts[3], parts[4], parts[5], parts[6], parts[7]);
					}
					
					byte[] blah = bloop.getBytes();
					DatagramPacket reply = new DatagramPacket(blah,blah.length, request.getAddress(), request.getPort());
					ddo.send(reply);
					
				}
			}catch(SocketException e){
				System.out.println("Socket Exception: "+e);
			}
			catch(IOException e){
				System.out.println("IO Exception: "+e);
			}
			finally{
				if(ddo != null)
					ddo.close();
			}
		}
		
		private static StringBuilder data(byte[] a) {
			// TODO Auto-generated method stub
			
			
			if(a==null)
			return null;
			StringBuilder ret=new StringBuilder();
			int i=0;
			while(a[i] !=0) {
				
				ret.append((char) a[i]);
				i++;
			}
			return ret;
		}
		
		
		public void deleteRecord(String recordID)
		{
			 Iterator<Entry<String,List<Record>>> it = database.entrySet().iterator();
			 while(it.hasNext()){
				 Entry<String,List<Record>> entry = it.next();
				 List<Record> recordList = (ArrayList<Record>) entry.getValue();
				 
				 synchronized(this){
					 Iterator listIt = recordList.iterator();
					 
					 while(listIt.hasNext()){
						 Record record = (Record) listIt.next();
						 if(record.Record_ID.equals(recordID))
							 listIt.remove();
					 }
				 }
			 }
		}
		
		public String fetchRecord(String recordID)
		{
			String bloop = "";
			String fname, lname, address, phone, spec, location, courses, status, date;
			for(Map.Entry<String, List<Record>> entry : database.entrySet())
				for(Record e: entry.getValue())
					if(e.Record_ID.equals(recordID)) 
						if(recordID.startsWith("SR"))
						{
							 fname = ((StudentRecord)e).First_name;
							 lname = ((StudentRecord)e).Last_name;
							 courses = ((StudentRecord)e).CoursesRegistered;
							 status = ((StudentRecord)e).Status;
							 date = ((StudentRecord)e).date;
							 bloop = recordID+ "::" + fname + "::" + lname + "::" + courses + "::" + status + "::" + date;
						}
						else
						{
							fname = ((TeacherRecord)e).First_name;
							lname = ((TeacherRecord)e).Last_name;
							address = ((TeacherRecord)e).Address;
							phone = ((TeacherRecord)e).Phone;
							spec = ((TeacherRecord)e).Specialization;
							location = ((TeacherRecord)e).Location;
							bloop = recordID+ "::" + fname + "::" + lname + "::" + address + "::" + phone + "::" + spec + "::" + location;
						}
			return bloop;
		}
		
		public boolean checkRecordID(String recordID) 
		{
			for(Map.Entry<String, List<Record>> entry : database.entrySet())
				for(Record e: entry.getValue())
					 if(e.Record_ID.equals(recordID)) 
						return true;
			return false;
		}
	
}
