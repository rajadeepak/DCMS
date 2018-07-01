package com;

import java.io.IOException;

import javax.xml.ws.Endpoint;

public class StartServers {

	public static void main(String[] args) throws IOException {
		Endpoint ddo = Endpoint.publish("http://localhost:7777/ddo", new DDOImpl());
		System.out.println("DDO Server Started : "+ddo.isPublished());
		Endpoint lvl = Endpoint.publish("http://localhost:7777/lvl", new LVLImpl());
		System.out.println("LVL Server Started : "+lvl.isPublished());
		Endpoint mtl = Endpoint.publish("http://localhost:7777/mtl", new MTLImpl());
		System.out.println("MTL Server Started : "+mtl.isPublished());
	}
}
