package com.main;

import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.one.*;
import com.two.*;
import com.three.*;

import CorbaApp.DCMS;
import CorbaApp.DCMSHelper;

public class StartServers
{

    public static void main(String[] args) {
        try {
            initCORBA(args);
            
            DDO1Server ddo1 = new DDO1Server();
            LVL1Server lvl1 = new LVL1Server();
            MTL1Server mtl1 = new MTL1Server();
            DDO2Server ddo2 = new DDO2Server();
            LVL2Server lvl2 = new LVL2Server();
            MTL2Server mtl2 = new MTL2Server();
            DDO3Server ddo3 = new DDO3Server();
            LVL3Server lvl3 = new LVL3Server();
            MTL3Server mtl3 = new MTL3Server();
            
            ddo1.run();
            lvl1.run();
            mtl1.run();
            ddo2.run();
            lvl2.run();
            mtl2.run();
            ddo3.run();
            lvl3.run();
            mtl3.run();
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