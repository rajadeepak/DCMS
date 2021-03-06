package com.main;
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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.attribute.ResolutionSyntax;

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
import fr.slaynash.communication.handlers.PacketHandler;
import fr.slaynash.communication.rudp.RUDPClient;
import fr.slaynash.communication.rudp.RUDPServer;
import fr.slaynash.communication.utils.NetUtils;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

public class FrontEnd extends DCMSPOA{

	private ORB orb;
	private static int ID = 10000;
	private LogManager logger = null;
	public static DCMS server;
	private int MTL1Port= 1001;
	private int LVL1Port = 1002;
	private int DDO1Port = 1003;
	private int MTL2Port= 1004;
	private int LVL2Port = 1005;
	private int DDO2Port = 1006;
	private int MTL3Port= 1007;
	private int LVL3Port = 1008;
	private int DDO3Port = 1009;
	private static int FEPort = 7825;
	public static String rudpResponse = "";
    ExecutorService exec = Executors.newFixedThreadPool(10);
    
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
			System.out.println(new String(data));
		}

		@Override
		public void onReliablePacketReceived(byte[] data) {
			String omg = new String(data);
			rudpResponse = omg.substring(3);
		}

		@Override
		public void onRemoteStatsReturned(int sentRemote, int sentRemoteR, int receivedRemote, int receivedRemoteR) {
			// TODO Auto-generated method stub
			
		}

    }
    
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
    	String msg;
		int port;
		
		msg = "true::createTRecord"+ "::" + ManagerID + "::" + firstName + "::" + lastName + "::" + address + "::" + phone + "::" + specialization + "::" + location;
		port = getPort(ManagerID);
		forwardRequest(msg, port);
//		System.out.println("returning: "+rudpResponse);
		return rudpResponse;
	}

	@Override
	public String createSRecord(String ManagerID, String firstName, String lastName, String courseRegistered,
			String status, String statusDate)
	{
		String msg;
		int port;
		
		msg = "true::createSRecord"+ "::" + ManagerID + "::" + firstName + "::" + lastName + "::" + courseRegistered + "::" + status + "::" + statusDate;
		 
		port = getPort(ManagerID);
		forwardRequest(msg, port);
		return rudpResponse;
	}
	
	@Override
	public String editRecord(String ManagerID, String recordID, String fieldName, String newValue) 
	{
		String msg;
		int port;
		
		msg = "true::editRecord"+ "::" + ManagerID + "::" + recordID + "::" + fieldName + "::" + newValue;
		 
		port = getPort(ManagerID);
		forwardRequest(msg, port);
		return rudpResponse;
	}
			
	@Override
	public String getRecordCounts(String ManagerID)
	{
		String msg;
		int port;
		
		msg = "true::GRCMethod"+ "::" + ManagerID;
		 
		port = getPort(ManagerID);
		forwardRequest(msg, port);
		
		return rudpResponse;
	}
	
	public String transferRecord(String ManagerID, String recordID, String remoteServerName)
	{
		String msg;
		int port = MTL1Port;
		
		msg = "true::TRMethod"+ "::" + ManagerID + "::" + recordID + "::" + remoteServerName;
		 
		port = getPort(ManagerID);
		forwardRequest(msg, port);
		return rudpResponse;
		
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FrontEnd feserver = new FrontEnd();
		feserver.startHeartBeatListener();
		feserver.startVotingListener();

	}
	
	private void startVotingListener() {
		
		try {
			Context context = new InitialContext(ConfigurationBean.getInstance().getEnv());
			ConnectionFactory factory = (ConnectionFactory) context.lookup("myJmsFactory");
			Destination destination = (Destination) context.lookup("queue/voting");
			
			Connection connection = factory.createConnection();
	        connection.start();

	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        MessageConsumer consumer = session.createConsumer(destination);
	        consumer.setMessageListener(VoteListener.getInstance());
			
		} catch (NamingException |JMSException e) {
			e.printStackTrace();
		} 
		
	}

	private void startHeartBeatListener(){
		
		try {
			Context context = new InitialContext(ConfigurationBean.getInstance().getEnv());
			ConnectionFactory factory = (ConnectionFactory) context.lookup("myJmsFactory");
			Destination destination = (Destination) context.lookup("queue/heartbeat");
			
			Connection connection = factory.createConnection();
	        connection.start();

	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        MessageConsumer consumer = session.createConsumer(destination);
	        consumer.setMessageListener(HeartbeatListener.getInstance());
			
		} catch (NamingException |JMSException e) {
			e.printStackTrace();
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
			rudpResponse = "";
			client.sendReliablePacket(bloop);
			while(rudpResponse.equals(""))
			{
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Transferring over Reliable UDP...");
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
	
	
	public int getPort(String ManagerID)
	{
		int port = 0;

		String ManagerIDTrim = ManagerID.substring(0, 3);
		
		switch(ManagerIDTrim) {
		case "MTL":
			port = ConfigurationBean.getInstance().master_servers.get("MTL");
			break;
		case "LVL":
			port = ConfigurationBean.getInstance().master_servers.get("LVL");
			break;
		case "DDO":
			port = ConfigurationBean.getInstance().master_servers.get("DDO");
			break;
		default:
			System.out.println("bloop haha");
			break;
		}
		
		return port;
	}
}