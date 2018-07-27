/**
 * 
 */
package com.main;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;

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
			String server = t.getText().split("::")[0];
			Long timestamp = Long.parseLong(t.getText().split("::")[1]);
			DatabaseBean.getInstance().lastHeartbeat.put(server, timestamp);
			
			String last = checkTimeElapsedSinceLastHeartbeat(System.currentTimeMillis());
			Long portNum = ConfigurationBean.getInstance().getPortNumberForServer(last);
			if(last != null){
				System.out.println("Trigger Election for : "+last);
				try {
					DatabaseBean.getInstance().lastHeartbeat.remove(last);
					Thread electionThread = new Thread(new ElectionTrigger(last.substring(0, 3)));
					electionThread.start();
				} catch (NamingException e) {
					e.printStackTrace();
				}
				
			}
			
			System.out.println(t.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private String checkTimeElapsedSinceLastHeartbeat(Long timestamp){
		for(String server :DatabaseBean.getInstance().lastHeartbeat.keySet()){
			Long previous = DatabaseBean.getInstance().lastHeartbeat.get(server);
			long diff = Math.subtractExact(timestamp, previous);
			
			if(diff>10000 && 
					ConfigurationBean.getInstance().master_servers.get(server.substring(0, 3)) != -1){
				return server;
			}
		}
		return null;
	}

}
