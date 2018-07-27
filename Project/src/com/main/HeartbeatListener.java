/**
 * 
 */
package com.main;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author harvi
 *
 */
public class HeartbeatListener implements MessageListener {

	private static final HeartbeatListener instance = new HeartbeatListener();
	
	private HeartbeatListener() {

	}
	
	public static HeartbeatListener getInstance(){
		return instance;
	}
	
	@Override
	public void onMessage(Message m) {
		TextMessage t = (TextMessage) m;
		try {
			DatabaseBean.getInstance().lastHeartbeat.put(t.getText().split("::")[0], Long.parseLong(t.getText().split("::")[1]));
			System.out.println(t.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
