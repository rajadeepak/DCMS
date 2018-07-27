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

public class ElectionResultTrigger implements Runnable {

	
	private String serverName;
	private Long portNumber;
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	
	public ElectionResultTrigger(String s) throws NamingException, JMSException {

		this.serverName = s;
		
		portNumber = ConfigurationBean.getInstance().getPortNumberForServer(serverName);
		
		ConfigurationBean.getInstance().master_servers.put(serverName.substring(0, 3), portNumber.intValue());
		
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
			TextMessage message = session.createTextMessage("ELECTIONBEGIN::"+serverName.substring(0, 3)+"::"+portNumber);
			System.out.println("Publishing election result to topic : "+message.getText());
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
   	 	
	}

}
