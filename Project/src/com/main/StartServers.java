package com.main;

import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CorbaApp.DCMS;
import CorbaApp.DCMSHelper;

public class StartServers
{

    public static void main(String[] args) {
        try {
            initCORBA(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void initCORBA(String[] args) throws Exception {
        try {
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", 900);
            String[] a = null;
            ORB orb = ORB.init(a,null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            
            FrontEnd obj = new FrontEnd();
            
            obj.setORB(orb);
            
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(obj);
            
            DCMS href = DCMSHelper.narrow(ref);
            
            org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");
            NamingContextExt nref = NamingContextExtHelper.narrow(objref);

            String name = "FE";
            
            NameComponent path[] = nref.to_name(name);
            nref.rebind(path, href);
            
            System.out.println(" Center Server is running. . . .");
            
            orb.run();
        }
        catch(Exception e) {
            e.printStackTrace();
            
        } System.out.println("Center Server is Exiting ...");
    } 
}