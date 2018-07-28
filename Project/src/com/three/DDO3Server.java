package com.three;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.naming.NamingException;

import com.main.ElectionTriggerListener;
import com.main.HeartbeatGenerator;
import com.main.LogManager;
import com.main.Record;
import com.main.StudentRecord;
import com.main.TeacherRecord;
import com.main.FrontEnd.MyPacketHandler;
import com.one.LVL1Server;

import fr.slaynash.communication.handlers.OrderedPacketHandler;
import fr.slaynash.communication.handlers.PacketHandler;
import fr.slaynash.communication.rudp.RUDPClient;
import fr.slaynash.communication.rudp.RUDPServer;
import fr.slaynash.communication.utils.NetUtils;
import fr.slaynash.test.RouterClientTest.ClientPacketHandler;

public class DDO3Server {

	private static int MTL1Port= 7001;
	private static int LVL1Port = 8001;
	private static int DDO1Port = 9001;
	private static int MTL2Port= 7002;
	private static int LVL2Port = 8002;
	private static int DDO2Port = 9002;
	private static int MTL3Port= 7003;
	private static int LVL3Port = 8003;
	private static int DDO3Port = 9003;
	private static int FEPort = 7825;
	private static int MTLPort3= 7006;
	private static int DDOPort3= 9006;
	private static int LVLPort3= 8006;
	
	public  volatile Map<String,List<Record>> database=new HashMap<String,List<Record>>();
	List<Record> records;
	Record recobj;
	
	private int ID = 10000;
	private LogManager logger = null;
	
	public RUDPServer server;
	public static String rudpResponse = "";
	
	ExecutorService exec = Executors.newFixedThreadPool(10);
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
	private static DDO3Server serverInstance = new DDO3Server();
	
	private DDO3Server() {
		super();
		try {
			logger = new LogManager("ddo-3-server.log");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static DDO3Server getInstance() {
		return serverInstance;
	}

	public static class MyPacketHandler extends PacketHandler{
    	
		public MyPacketHandler() {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onConnection() {
			System.out.println("Connected");
		}

		@Override
		public void onDisconnectedByLocal(String reason) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDisconnectedByRemote(String reason) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPacketReceived(byte[] data) {
			
		}

		@Override
		public void onReliablePacketReceived(byte[] data) {
			String rep = new String(data);
			String bloop = "";
			
			if(rep.equals("getRecordCounts"))
				bloop = "DDO "+ String.valueOf(DDO3Server.getInstance().database.size()); 
			
			else if(rep.contains("transferRecord"))
			{
				
				String bleep = rep;
				String parts[] = bleep.split("::");
				if(parts[2].startsWith("TR"))
					bloop = DDO3Server.getInstance().createTRecord(parts[1], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
				else
					bloop = DDO3Server.getInstance().createSRecord(parts[1], parts[3], parts[4], parts[5], parts[6], parts[7]);
			}
			
			else if(rep.contains("createTRecord"))
			{
				String bleep = rep;
				String parts[] = bleep.split("::");
				System.out.println(parts[0]);
				bloop = DDO3Server.getInstance().createTRecord(parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
				if(parts[0].contains("true"))
				{
					String str = rep.replace("true", "false");
					DDO3Server.getInstance().forwardRequest(str,DDO1Port);
					DDO3Server.getInstance().forwardRequest(str,DDO2Port);
				}
					
				
			}
			
			else if(rep.contains("createSRecord"))
			{
				String bleep = rep;
				String parts[] = bleep.split("::");
				bloop = DDO3Server.getInstance().createSRecord(parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
				if(parts[0].contains("true"))
				{
					String str = rep.replace("true", "false");
					DDO3Server.getInstance().forwardRequest(str,DDO1Port);
					DDO3Server.getInstance().forwardRequest(str,DDO2Port);
				}
				
			}
			
			else if(rep.contains("editRecord"))
			{
				String bleep = rep;
				String parts[] = bleep.split("::");
				bloop = DDO3Server.getInstance().editRecord(parts[2], parts[3], parts[4], parts[5]);
				if(parts[0].contains("true"))
				{
					String str = rep.replace("true", "false");
					DDO3Server.getInstance().forwardRequest(str,DDO1Port);
					DDO3Server.getInstance().forwardRequest(str,DDO2Port);
				}
				
			}
			
			else if(rep.contains("GRCMethod"))
			{
				String bleep = rep;
				String parts[] = bleep.split("::");
				bloop = DDO3Server.getInstance().getRecordCounts(parts[2]);
				if(parts[0].contains("true"))
				{
					String str = rep.replace("true", "false");
					DDO3Server.getInstance().forwardRequest(str,DDO1Port);
					DDO3Server.getInstance().forwardRequest(str,DDO2Port);
				}
				
			}
			
			else if(rep.contains("TRMethod"))
			{
				String bleep = rep;
				String parts[] = bleep.split("::");
				bloop = DDO3Server.getInstance().transferRecord(parts[2], parts[3], parts[4]);
				if(parts[0].contains("true"))
				{
					String str = rep.replace("true", "false");
					DDO3Server.getInstance().forwardRequest(str,DDO1Port);
					DDO3Server.getInstance().forwardRequest(str,DDO2Port);
				}
				
			}
			
			System.out.println("Request Transferred over Reliable UDP");
			
			try {
				DDO3Server.getInstance().server.getConnectedClients().get(0).sendReliablePacket(bloop.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		@Override
		public void onRemoteStatsReturned(int sentRemote, int sentRemoteR, int receivedRemote, int receivedRemoteR) {
			// TODO Auto-generated method stub
			
		}

    }
   	
    public String createTRecord(String ManagerID, String firstName, String lastName, String address, String phone,
			String specialization, String location) 
	{
		try{	
			String key=lastName.substring(0,1);
			String id = "TR"+genID();
			recobj=new TeacherRecord(firstName, lastName, address, phone, specialization, location, id);
			
			synchronized(this) {
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
					DatagramPacket request = new DatagramPacket(message, message.length, aHost, MTLPort3);
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
					DatagramPacket request = new DatagramPacket(message, message.length, aHost, LVLPort3);
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
			port = MTLPort3;
		else
			port = LVLPort3;
		
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
		
		getInstance().startServer();
		
		DatagramSocket ddo = null;
		try{
			
			ddo = new DatagramSocket(DDOPort3);
			while(true){
				String bloop = "";
				byte[] buffer = new byte[1000];
				
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				ddo.receive(request);
				
				if(data(buffer).toString().equals("getRecordCounts"))
					bloop = "DDO "+ String.valueOf(DDO3Server.getInstance().getSize()) + ", "; 
				
				else if(data(buffer).toString().contains("transferRecord"))
				{
					String bleep = data(buffer).toString();
					String parts[] = bleep.split("::");
					if(parts[2].startsWith("TR"))
						bloop = DDO3Server.getInstance().createTRecord(parts[1], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
					else
						bloop = DDO3Server.getInstance().createSRecord(parts[1], parts[3], parts[4], parts[5], parts[6], parts[7]);
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
	
	private void startServer() {
		try {
			executor.scheduleAtFixedRate(new HeartbeatGenerator("DDO3"), 1 , 5, TimeUnit.SECONDS);
			exec.execute(new ElectionTriggerListener("DDO3"));
		} catch (NamingException | JMSException | IOException e) {
			e.printStackTrace();
		}
		try {
			server = new RUDPServer(DDO3Port);
			server.setPacketHandler(MyPacketHandler.class);
			server.start();
		}
		catch(SocketException e) {
			System.out.println("Port 9003 is occupied. Server couldn't be initialized.");
			System.exit(-1);
		}
	}
	
    public synchronized int genID()
	{
		return ID++;
	}
  
	public int getSize()
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
	
	public boolean checkRecordID(String recordID) 
	{
		for(Map.Entry<String, List<Record>> entry : database.entrySet())
			for(Record e: entry.getValue())
				 if(e.Record_ID.equals(recordID)) 
					return true;
		return false;
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
	
	public void forwardRequest(String msg, int port)
	{
		try {
			InetAddress inet = InetAddress.getLocalHost();
			RUDPClient client = new RUDPClient(inet, port);
			client.setPacketHandler(MyPacketHandler.class);
			client.connect();
			byte[] bloop = msg.getBytes();
			client.sendReliablePacket(bloop);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		catch(SocketException e) {
			System.out.println("Cannot allow port for the client. Client can't be launched.");
			System.exit(-1);
		}
		catch(UnknownHostException e) {
			System.out.println("Unknown host: " + port);
			System.exit(-1);
		}
		catch(SocketTimeoutException e) {
			System.out.println("Connection to " + port + " timed out.");
		}
		catch (InstantiationException e) {} //Given handler class can't be instantiated.
		catch (IllegalAccessException e) {} //Given handler class can't be accessed.
		catch(IOException e) {}
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
	
}