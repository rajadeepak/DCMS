package com.one;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.omg.PortableServer.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.POA;

import com.main.LogManager;
import com.main.Record;
import com.main.StudentRecord;
import com.main.TeacherRecord;

import CorbaApp.DCMSPOA;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

public class DDO1Server implements Runnable {

	private ORB orb;
	private static int ID = 10000;
	private LogManager logger = null;
	public static volatile Map<String,List<Record>> database=new HashMap<String,List<Record>>();
	List<Record> records;
	Record recobj;
	
	private int MTL1Port= 1001;
	private int LVL1Port = 1002;
	private static int DDO1Port = 1003;
	private int MTL2Port= 1004;
	private int LVL2Port = 1005;
	private int DDO2Port = 1006;
	private int MTL3Port= 1007;
	private int LVL3Port = 1008;
	private int DDO3Port = 1009;
	private int FEPort = 7825;
	
    ExecutorService exec = Executors.newFixedThreadPool(10);
    
    public DDO1Server() throws IOException 
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
				if(DDO1Server.database.containsKey(key)){
					records=DDO1Server.database.get(key);
					records.add(recobj);
					DDO1Server.database.put(key, records);
				}
				else{
					records=new ArrayList<Record>();
					records.add(recobj);
					DDO1Server.database.put(key, records);
				}
				logger.writeLog("Manager : "+ManagerID+";Inserted Teacher Record Number : "+ ((TeacherRecord)recobj).Record_ID);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.writeLog("Manager : "+ManagerID+";Error occured while trying to insert teacher record number : "+((TeacherRecord)recobj).Record_ID);
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
				logger.writeLog("Manager : "+ManagerID+";Inserted Student Record Number : "+((StudentRecord)recobj).Record_ID);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.writeLog("Manager : "+ManagerID+";Error occured while trying to insert student record number : "+((StudentRecord)recobj).Record_ID);
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
								logger.writeLog("Manager : "+ManagerID+";"+fieldName +"could not be updated to "+ newValue +" value must be either active/inactive");
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
							logger.writeLog("Manager : "+ManagerID+";invalid field:  "+fieldName +" for the StudentRecord");
							return "Failed";
						}
					}
					else 
					{
						if(fieldName.equalsIgnoreCase("location")) 
						{
							if(!newValue.equalsIgnoreCase("lvl") && !newValue.equalsIgnoreCase("mtl") && !newValue.equalsIgnoreCase("ddo"))
							{
								logger.writeLog("Manager : "+ManagerID+";"+fieldName +"could not be updated to "+ newValue +" value must be either mtl/ddo/lvl");
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
							logger.writeLog("Manager : "+ManagerID+";invalid field:  "+fieldName +" for the TeacherRecord");
							return "Failed";
						}
					}
					
				}
			}
		if(!flag)
		{
			logger.writeLog("Manager : "+ManagerID+";RecordID:  "+recordID +" Not Found in this server");
			return "Failed";
		}
	        return "Success";
	}
			
	public String getRecordCounts(String ManagerID)
	{
		String s="DDO "+getSize()+" ";
		
		try {
			byte[] message = "getRecordCounts".getBytes();
			byte[] buffer1 = new byte[1000];
			byte[] buffer2 = new byte[1000];
			
			Future<String> mtlResponse = exec.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					DatagramSocket ds = new DatagramSocket();
					InetAddress aHost = InetAddress.getByName("localhost");
					DatagramPacket request = new DatagramPacket(message, message.length, aHost, MTL1Port);
					ds.send(request);
					DatagramPacket reply1 = new DatagramPacket(buffer1, buffer1.length);
					ds.receive(reply1);
					String response1 = new String(reply1.getData());
					String a = response1.trim();
					ds.close();
					return a;
				}
				
				
			});
			
			Future<String> lvlResponse = exec.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					DatagramSocket ds = new DatagramSocket();
					InetAddress aHost = InetAddress.getByName("localhost");
					DatagramPacket request = new DatagramPacket(message, message.length, aHost, LVL1Port);
					ds.send(request);
					DatagramPacket reply2 = new DatagramPacket(buffer2, buffer2.length);
					ds.receive(reply2);
					String response2= new String(reply2.getData());
					ds.close();
					String b = response2.trim();
					return b;
				}
			});
			
			s = s + mtlResponse.get() + lvlResponse.get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		logger.writeLog("Manager : "+ManagerID+";Current system records "+s);
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
		
		if(remoteServerName.equalsIgnoreCase("MTL"))
			port = MTL1Port;
		else
			port = LVL1Port;
		
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
		
	}
	
	public static int getSize()
	{
		int size = 0;
		for(Map.Entry<String, List<Record>> entry : database.entrySet())
			for(Record e: entry.getValue())
				if(e.Record_ID.contains("SR"))
					size++;
				else
					size++;
		return size;
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DatagramSocket ddo = null;
		
		String bloop = "";
		try{
			DDO1Server obj = new DDO1Server();
			ddo = new DatagramSocket(DDO1Port);
			while(true){
				
				byte[] buffer = new byte[1000];
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				ddo.receive(request);
				
				if(data(buffer).toString().equals("getRecordCounts"))
					bloop = "DDO "+ String.valueOf(database.size()) + ", "; 
				else if(data(buffer).toString().contains("transferRecord"))
				{
					
					String bleep = data(buffer).toString();
					String parts[] = bleep.split("::");
					if(parts[2].startsWith("TR"))
						bloop = obj.createTRecord(parts[1], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
					else
						bloop = obj.createSRecord(parts[1], parts[3], parts[4], parts[5], parts[6], parts[7]);
				}
				
				else if(data(buffer).toString().contains("createTRecord"))
				{
					String bleep = data(buffer).toString();
					String parts[] = bleep.split("::");
					bloop = obj.createTRecord(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
				}
				else if(data(buffer).toString().contains("createSRecord"))
				{
					String bleep = data(buffer).toString();
					String parts[] = bleep.split("::");
					bloop = obj.createSRecord(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
				}
				else if(data(buffer).toString().contains("editRecord"))
				{
					String bleep = data(buffer).toString();
					String parts[] = bleep.split("::");
					bloop = obj.editRecord(parts[1], parts[2], parts[3], parts[4]);
				}
				else if(data(buffer).toString().contains("GRCMethod"))
				{
					String bleep = data(buffer).toString();
					String parts[] = bleep.split("::");
					bloop = obj.getRecordCounts(parts[1]);
				}
				else if(data(buffer).toString().contains("TRMethod"))
				{
					String bleep = data(buffer).toString();
					String parts[] = bleep.split("::");
					bloop = obj.transferRecord(parts[1], parts[2], parts[3]);
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

	
}