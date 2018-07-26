package com.main;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
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

import CorbaApp.DCMS;
import CorbaApp.DCMSHelper;
import CorbaApp.DCMSPOA;
import fr.slaynash.communication.handlers.OrderedPacketHandler;
import fr.slaynash.communication.rudp.RUDPClient;
import fr.slaynash.communication.utils.NetUtils;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

public class FrontEnd extends DCMSPOA {

	private ORB orb;
	private static int ID = 10000;
	private LogManager logger = null;
	private Queue<String> queue = new LinkedList<>();
	public static DCMS server;
	private int MTL1Port= 1001;
	private int LVL1Port = 1002;
	private  int DDO1Port = 1003;
	private int MTL2Port= 1004;
	private int LVL2Port = 1005;
	private int DDO2Port = 1006;
	private int MTL3Port= 1007;
	private int LVL3Port = 1008;
	private int DDO3Port = 1009;
	private static int FEPort = 7825;
	public static RUDPClient client;
	
    ExecutorService exec = Executors.newFixedThreadPool(10);
    
    protected FrontEnd() throws IOException 
    {
		super();
		logger = new LogManager("FrontEnd.log");
	}

    
    public synchronized int genID()
	{
		return ID++;
	}
    
    public void setORB(ORB orb) 
    {
    	this.orb = orb;
    }
    
    public void shutdown()
    {
    	orb.shutdown(false);
    }

    @Override
    public String createTRecord(String ManagerID, String firstName, String lastName, String address, String phone,
			String specialization, String location) 
	{
    	
    	String msg, result;
		int port;
		
		msg = "createTRecord"+ "::" + ManagerID + "::" + firstName + "::" + lastName + "::" + address + "::" + phone + "::" + specialization + "::" + location;
		port = getPort(ManagerID);
		result = forwardRequest(msg, port);
//		queue.add(msg);
//		
//		
//		DatagramSocket ds = null;
//		
//		try {
//			byte[] message = msg.getBytes();
//			byte[] buffer = new byte[1000];
//			
//			ds = new DatagramSocket();
//            InetAddress aHost = InetAddress.getByName("localhost");
//            DatagramPacket request = new DatagramPacket(message, message.length, aHost, port);
//            ds.send(request);
//            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
//            ds.receive(reply);
//            String response = new String(reply.getData());
//            ds.close();
//            
//            result = response.trim();
//            
//		}catch(SocketException e) {
//			e.printStackTrace();
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
//		finally {
//			if(ds!=null)
//				ds.close();
//		}
//		
//		if(result.equalsIgnoreCase("success"))
//        	logger.writeLog("ManagerID: "+ManagerID+". createTRecord Success.");            
//      
//        else {
//        	logger.writeLog("ManagerID: "+ManagerID+". createTRecord Failed.");
//			return "failed";
//        }
		
		return "success";
	}

	@Override
	public String createSRecord(String ManagerID, String firstName, String lastName, String courseRegistered,
			String status, String statusDate)
	{
		String msg, result = null;
		int port;
		String ManagerIDTrim = ManagerID.substring(0, 3);
		
		msg = "createSRecord"+ "::" + ManagerID + "::" + firstName + "::" + lastName + "::" + courseRegistered + "::" + status + "::" + statusDate;
		 
		switch(ManagerIDTrim) {
		case "MTL":
			break;
		case "LVL":
			break;
		case "DDO":
			break;
		default:
			System.out.println("bloop haha");
			break;
		}
		
		port = DDO1Port;
		
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
            
            result = response.trim();
            
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
		
		if(result.equalsIgnoreCase("success"))
        	logger.writeLog("ManagerID: "+ManagerID+". createSRecord Success.");            
      
        else {
        	logger.writeLog("ManagerID: "+ManagerID+". createSRecord Failed.");
			return "failed";
        }
		
		return "success";
	}
	
	@Override
	public String editRecord(String ManagerID, String recordID, String fieldName, String newValue) 
	{
		String msg, result = null;
		int port;
		String ManagerIDTrim = ManagerID.substring(0, 3);
		
		msg = "editRecord"+ "::" + ManagerID + "::" + recordID + "::" + fieldName + "::" + newValue;
		 
		switch(ManagerIDTrim) {
		case "MTL":
			break;
		case "LVL":
			break;
		case "DDO":
			break;
		default:
			System.out.println("bloop haha");
			break;
		}
		
		port = DDO1Port;
		
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
            
            result = response.trim();
            
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
		
		if(result.equalsIgnoreCase("success"))
        	logger.writeLog("ManagerID: "+ManagerID+". editRecord Success.");            
      
        else {
        	logger.writeLog("ManagerID: "+ManagerID+". editRecord Failed.");
			return "failed";
        }
		
		return "success";
	}
			
	@Override
	public String getRecordCounts(String ManagerID)
	{
		String msg, result = null;
		int port;
		String ManagerIDTrim = ManagerID.substring(0, 3);
		
		msg = "GRCMethod"+ "::" + ManagerID;
		 
		switch(ManagerIDTrim) {
		case "MTL":
			break;
		case "LVL":
			break;
		case "DDO":
			break;
		default:
			System.out.println("bloop haha");
			break;
		}
		
		port = DDO1Port;
		
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
            
            result = response.trim();
            
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
		
		return result;
	}
	
	public String transferRecord(String ManagerID, String recordID, String remoteServerName)
	{
		String msg, result = null;
		int port = MTL1Port;
		String ManagerIDTrim = ManagerID.substring(0, 3);
		
		msg = "TRMethod"+ "::" + ManagerID + "::" + recordID + "::" + remoteServerName;
		 
		switch(ManagerIDTrim.toUpperCase()) {
		case "MTL": port = MTL1Port;
			break;
		case "LVL": port = LVL1Port;
			break;
		case "DDO": port = DDO1Port;
			break;
		default:
			System.out.println("bloop haha");
			break;
		}
		
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
            
            result = response.trim();
            
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
		
		if(result.equalsIgnoreCase("success"))
        	logger.writeLog("ManagerID: "+ManagerID+". transferRecord Success.");            
      
        else {
        	logger.writeLog("ManagerID: "+ManagerID+". transferRecord Failed.");
			return "failed";
        }
		
		return "success";
		
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String forwardRequest(String msg, int port)
	{
		String result = "blaaaahhhh";
		int SERVER_PORT = port;
		RUDPClient client = null;
		InetAddress SERVER_HOST = NetUtils.getInternetAdress("localhost");
		byte[] message = msg.getBytes();
		try {
			client = new RUDPClient(SERVER_HOST, SERVER_PORT);
			client.setPacketHandler(OrderedPacketHandler.class);
			client.connect();
		}
		catch(SocketException e) {
			System.out.println("Cannot allow port for the client. Client can't be launched.");
			System.exit(-1);
		}
		catch(UnknownHostException e) {
			System.out.println("Unknown host: " + SERVER_HOST);
			System.exit(-1);
		}
		catch(SocketTimeoutException e) {
			System.out.println("Connection to " + SERVER_HOST + ":" + SERVER_PORT + " timed out.");
		}
		catch (InstantiationException e) {} //Given handler class can't be instantiated.
		catch (IllegalAccessException e) {} //Given handler class can't be accessed.
		catch(IOException e) {}

		client.sendPacket(message); //Send packet to the server
		client.sendReliablePacket(message); //Send packet to the server

		client.disconnect(); //Disconnect from server
		return result;
	}
	
	
	public int getPort(String ManagerID)
	{
		int port;

		String ManagerIDTrim = ManagerID.substring(0, 3);
		
		switch(ManagerIDTrim) {
		case "MTL":
			break;
		case "LVL":
			break;
		case "DDO":
			break;
		default:
			System.out.println("bloop haha");
			break;
		}
		
		port = DDO1Port;
		return port;
	}
}