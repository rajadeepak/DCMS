package com;

import java.io.IOException;
import java.rmi.RemoteException;

public class Driver_startServer {
	
	
	public static void main(String[] args) throws Exception {
		
		
	
		
		LVLStart lvl=new LVLStart();
		
		DDOStart ddo=new DDOStart();
	
		MTLStart mtl= new MTLStart();
		
		ddo.start();
		mtl.start();
		lvl.start();
		
	}

}
