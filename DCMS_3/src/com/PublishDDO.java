package com;

import java.io.IOException;

import javax.xml.ws.Endpoint;

public class PublishDDO {

	public static void main(String[] args) throws IOException {
		Endpoint endpoint = Endpoint.publish("http://localhost:7777/ddo", new DDOImpl());
		System.out.println(endpoint.isPublished());
	}
}
