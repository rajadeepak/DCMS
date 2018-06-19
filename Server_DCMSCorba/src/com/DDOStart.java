package com;

import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CorbaApp.DCMS;
import CorbaApp.DCMSHelper;

public class DDOStart {
	
	public void start()
	{
		System.out.println("entered");
		 try {
			    Properties props = new Properties();
	            props.put("org.omg.CORBA.ORBInitialPort", 900);
	            String[] args= null;
				ORB orb = ORB.init(args,props);
	            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	   
	            rootpoa.the_POAManager().activate();
	            
	          
	            DDOServer ddoserver = new DDOServer();
	            
	
	            ddoserver.setORB(orb);

	          
	            org.omg.CORBA.Object dref = rootpoa.servant_to_reference(ddoserver);
	   
	        
	            DCMS href2 = DCMSHelper.narrow(dref);
	      
	            org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");
	          
	            NamingContextExt nref = NamingContextExtHelper.narrow(objref);
	            
	            
	            String name2 = "DDO";
	            
	       
	            
	            NameComponent path2[] = nref.to_name(name2);
	            nref.rebind(path2, href2);
	            
	            System.out.println(" Servers are running. . . .");
	            
	            orb.run();
	        }
	        catch(Exception e) {
	            e.printStackTrace();
	            
	        } System.out.println("Center Server Montreal Exiting ...");
	    } 
	}

