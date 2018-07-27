package com.main;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import CorbaApp.DCMSPOA;

public class FEServer extends DCMSPOA{

	public static void main(String[] args) {
		
		FEServer server = new FEServer();
		server.start();

	}
	
	private void start(){
		
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
	

	@Override
	public String createTRecord(String ManagerID, String firstName, String lastName, String address, String phone,
			String specialization, String location) {
		return null;
	}

	@Override
	public String createSRecord(String ManagerID, String firstName, String lastName, String courseRegistered,
			String status, String statusDate) {
		return null;
	}

	@Override
	public String getRecordCounts(String ManagerID) {
		return null;
	}

	@Override
	public String editRecord(String ManagerID, String recordID, String fieldName, String newValue) {
		return null;
	}

	@Override
	public String transferRecord(String managerID, String recordID, String remoteCenterServerName) {
		return null;
	}

}
