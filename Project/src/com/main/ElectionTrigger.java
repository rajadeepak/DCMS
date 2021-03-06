package com.main;

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

public class ElectionTrigger implements Runnable {

	
	private String clustername;
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	
	public ElectionTrigger(String clustername) throws NamingException, JMSException {

		this.clustername = clustername;
		
		ConfigurationBean.getInstance().master_servers.put(clustername, -1);
		
		Context context = new InitialContext(ConfigurationBean.getInstance().getEnv());
        ConnectionFactory factory = (ConnectionFactory) context.lookup("myJmsFactory");
        Destination destination = (Destination) context.lookup("topic/election");

        connection = factory.createConnection();
        
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        producer = session.createProducer(destination);
        
        connection.start();
	}
	
	@Override
	public void run() {
		try {
			TextMessage message = session.createTextMessage("ELECTIONBEGIN::"+clustername);
			System.out.println("Sending message to topic : "+message.getText());
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
   	 	
	}

}
