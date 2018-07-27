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
	
	private Hashtable<String, String> env;
	private HashMap<String,Integer> master_servers;
	
	private ConfigurationBean() {
		env = new Hashtable<String,String>();
		env.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		env.put("java.naming.provider.url", "tcp://localhost:61616"); 
		env.put("connectionFactoryNames", "myJmsFactory");
		env.put("queue.queue/heartbeat", "heartbeatq");
		
		master_servers = new HashMap<String,Integer>();
		master_servers.put("MTL", 7000);
		master_servers.put("LVL", 8000);
		master_servers.put("DDO", 9000);
		
	}
	
	public static ConfigurationBean getInstance(){
		return instance;
	}
	
	public Hashtable<String, String> getEnv(){
		return this.env;
	}

}
