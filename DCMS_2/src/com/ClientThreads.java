package com;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.*;
import java.util.Date;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import CorbaApp.DCMS;
import CorbaApp.DCMSHelper;

	public class ClientThreads 
	{
		public static DCMS server;
		public static void getServer(String[] args, String serverLocation) 
		{
			try{
				ORB orb = ORB.init(args, null);
				org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
				NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
				String name = serverLocation;
				server= DCMSHelper.narrow(ncRef.resolve_str(name));
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}
		public static void main(String args[]) throws RemoteException, NotBoundException
		{
		
			Thread t1=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					LogManager logger;
					String status;
					try {
						getServer(args, "MTL");
						logger = new LogManager("MTL001-client.log");
						status = server.createTRecord("MTL001", "first1", "last1", "address1", "1234567890", "French", "MTL");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createTRecord Success. Manager ID: MTL001");
						else
							logger.writeLog("Operation createTRecord Failed. Manager ID: MTL001");
						
					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			Thread t2=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					LogManager logger;
					String status;
					try {
						getServer(args, "MTL");
						String date;
						logger = new LogManager("MTL002-client.log");
						DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
						date = "12/05/1999";
						status = server.createSRecord("MTL002", "first4", "last4", "english", "active", date);
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createSRecord Success. Manager ID: MTL002");
						else
							logger.writeLog("Operation createSRecord Failed. Manager ID: MTL002");

					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			Thread t3=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					LogManager logger;
					String status;
					try {
						getServer(args, "DDO");
						logger = new LogManager("DDO001-client.log");
						status = server.createTRecord("DDO001", "first2", "last2", "address2", "1234567891", "French", "MTL");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createTRecord Success. Manager ID: DDO001");
						else
							logger.writeLog("Operation createTRecord Failed. Manager ID: DDO001");

						
					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			
			
			Thread t4=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					LogManager logger;
					String status;
					try {
						getServer(args, "DDO");
						String date;
						logger = new LogManager("DDO002-client.log");
						date = "12/05/1989";
						status = server.createSRecord("DDO002", "first23", "last23", "arabic", "active", date);
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createSRecord Success. Manager ID: DDO002");
						else
							logger.writeLog("Operation createSRecord Failed. Manager ID: DDO002");

					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			Thread t5=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					LogManager logger;
					String status;
					try {
						getServer(args, "LVL");
						logger = new LogManager("LVL001-client.log");
						status = server.createTRecord("LVL001", "first3", "last3", "address3", "1234567892", "French", "MTL");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createTRecord Success. Manager ID: LVL001");
						else
							logger.writeLog("Operation createTRecord Failed. Manager ID: LVL001");
					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			
			Thread t6=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					LogManager logger;
					String status;
					try {
						getServer(args, "LVL");
						String date;
						logger = new LogManager("LVL002-client.log");
						date = "12/05/1931";
						status = server.createSRecord("LVL002", "first45", "last45", "telugu", "active", date );
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createSRecord Success. Manager ID: LVL002");
						else
							logger.writeLog("Operation createSRecord Failed. Manager ID: LVL002");

					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			
			Thread t7=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					LogManager logger;
					String status;
					try {
						getServer(args, "DDO");
						logger = new LogManager("DDO003-client.log");
						status = server.editRecord("DDO003", "TR10001", "phone", "9494515123");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation editRecord Success. Manager ID: DDO003");
						else
							logger.writeLog("Operation editRecord Failed. Manager ID: DDO003");

					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			
			Thread t8=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					LogManager logger;
					String status;
					try {
						getServer(args, "LVL");
						logger = new LogManager("LVL003-client.log");
						status = server.editRecord("LVL003", "TR10001", "phone", "5148022688");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation editRecord Success. Manager ID: LVL003");
						else
							logger.writeLog("Operation editRecord Failed. Manager ID: LVL003");

					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			
			Thread t9=new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					LogManager logger;
					String status;
					try {
						getServer(args, "MTL");
						logger = new LogManager("MTL003-client.log");
						status = server.transferRecord("MTL003", "TR10001", "LVL");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation transferRecord Success. Manager ID: MTL003");
						else
							logger.writeLog("Operation transferRecord Failed. Manager ID: MTL003");

					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			
			t1.start();
			try{

				t1.join();
			}catch(Exception e)
			{
				System.out.println(e);
			}
			t2.start();
			try{

				t2.join();
			}catch(Exception e)
			{
				System.out.println(e);
			}
			t3.start();
			try{

				t3.join();
			}catch(Exception e)
			{
				System.out.println(e);
			}
			t4.start();
			try{

				t4.join();
			}catch(Exception e)
			{
				System.out.println(e);
			}
			t5.start();
			try{

				t5.join();
			}catch(Exception e)
			{
				System.out.println(e);
			}
			t6.start();
			try{
				t6.join();
				
			}catch(Exception e)
			{
				System.out.println(e);
			}
			
			t7.start();
			try{
				t7.join();
				
			}catch(Exception e)
			{
				System.out.println(e);
			}
			
			t8.start();
			try{
				t8.join();
				
			}catch(Exception e)
			{
				System.out.println(e);
			}
			
			t9.start();
			try{
				t9.join();
				
			}catch(Exception e)
			{
				System.out.println(e);
			}
		}

	}