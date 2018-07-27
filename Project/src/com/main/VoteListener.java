/**
 * 
 */
package com.main;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;

/**
 * @author harvi
 *
 */
public class VoteListener implements MessageListener {

	private static final VoteListener instance = new VoteListener();
	
	private List<String> voteTally = new ArrayList<String>();
	
	private VoteListener() {

	}
	
	public static VoteListener getInstance(){
		return instance;
	}
	
	@Override
	public void onMessage(Message m) {
		TextMessage t = (TextMessage) m;
		try {
			String server = t.getText().split("::")[0];
			Long timestamp = Long.parseLong(t.getText().split("::")[1]);
			System.out.println("Received vote from : "+server);
			voteTally.add(server);
			
			if(voteTally.size()==2){
				 String key = voteTally.get(0);
				 voteTally.clear();
				 System.out.println("Election won by :"+key);
				 Thread th = new Thread(new ElectionResultTrigger(key));
				 th.start();
			}
		} catch (JMSException | NamingException e) {
			e.printStackTrace();
		}
	}
	
}
