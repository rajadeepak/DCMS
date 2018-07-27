/**
 * 
 */
package com.main;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author harvi
 *
 */
public class ElectionTriggerListener implements Runnable {

	private String serverName = null;

	private Connection connection;

	private Session session;

	public ElectionTriggerListener(String serverName) {

		try {
			this.serverName = serverName;

			Context context = new InitialContext(ConfigurationBean.getInstance().env);
			ConnectionFactory factory = (ConnectionFactory) context.lookup("myJmsFactory");
			Destination destination = (Destination) context.lookup("topic/election");

			connection = factory.createConnection();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageConsumer consumer = session.createConsumer(destination);

			consumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message arg0) {
					TextMessage m = (TextMessage) arg0;
					try {
						String electionMessage = m.getText();
						String mType = electionMessage.split("::")[0];
						String mVal = electionMessage.split("::")[1];
						
						if(mType.equals("ELECTIONBEGIN") && ElectionTriggerListener.this.serverName.contains(mVal)){
							System.out.println("Received Election Trigger for server : "+electionMessage);
							Thread th = new Thread(new VoteTrigger(ElectionTriggerListener.this.serverName+"::"+System.currentTimeMillis()));
							th.start();
						}
						else if(mType.equals("ELECTIONRESULT")){
							System.out.println("Received Election Result : "+electionMessage);
							ConfigurationBean.getInstance().master_servers.put(mVal, Integer.parseInt(electionMessage.split("::")[2]));
						}
						
					} catch (JMSException | NamingException | IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
