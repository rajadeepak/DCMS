/**
 * 
 */
package com.main;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author harvi
 *
 */
public class ConfigurationBean {
	
	private static final ConfigurationBean instance = new ConfigurationBean();
	
	public Hashtable<String, String> env;
	public HashMap<String,Integer> master_servers;
	private HashMap<String, Long> server_port;
	
	private ConfigurationBean() {
		env = new Hashtable<String,String>();
		env.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		env.put("java.naming.provider.url", "tcp://localhost:61616"); 
		env.put("connectionFactoryNames", "myJmsFactory");
		env.put("queue.queue/heartbeat", "heartbeatq");
		env.put("queue.queue/voting", "votingq");
		env.put("topic.topic/election", "electiontopic");
		
		master_servers = new HashMap<String,Integer>();
		master_servers.put("MTL", 7001);
		master_servers.put("LVL", 8001);
		master_servers.put("DDO", 9001);
		
		server_port = new HashMap<>();
		server_port.put("MTL1", 7001L);
		server_port.put("MTL2", 7002L);
		server_port.put("MTL3", 7003L);
		server_port.put("LVL1", 8001L);
		server_port.put("LVL2", 8002L);
		server_port.put("LVL3", 8003L);
		server_port.put("DDO1", 9001L);
		server_port.put("DDO2", 9002L);
		server_port.put("DDO3", 9003L);
	}
	
	public static ConfigurationBean getInstance(){
		return instance;
	}
	
	public Hashtable<String, String> getEnv(){
		return this.env;
	}

	public Long getPortNumberForServer(String serverName) {
		return this.server_port.get(serverName);
	}
	

}
