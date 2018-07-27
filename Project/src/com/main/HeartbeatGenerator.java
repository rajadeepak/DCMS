package com.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HeartbeatGenerator implements Runnable{

	private String id;
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	
	public HeartbeatGenerator(String id) throws NamingException, JMSException, FileNotFoundException, IOException {
		
		this.id = id;
		Context context = new InitialContext(ConfigurationBean.getInstance().getEnv());
        ConnectionFactory factory = (ConnectionFactory) context.lookup("myJmsFactory");
        Destination destination = (Destination) context.lookup("queue/heartbeat");

        
        connection = factory.createConnection();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        producer = session.createProducer(destination);
        
        connection.start();
	}
	
	@Override
	public void run() {
		TextMessage heartbeatMessage = null;
		try {
			heartbeatMessage = session.createTextMessage(id+"::"+System.currentTimeMillis());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			producer.send(heartbeatMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
