package com;

import java.util.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.NamingContextPackage.*;
import CorbaApp.DCMS;
import CorbaApp.DCMSHelper;

import org.omg.CosNaming.*;



public class StartClient {


	    public static String managerID;
	    static String First_name;

		 static String Last_name;

		 static String Address;

		 static String Phone;

		 static String Specialization;

		 static String Location;

		 static  String CoursesRegistered;

		 static String Status;

		 static String date;
		 
		 static String RecordID;
		 
		 static String fieldName;
		 static String newValue;
	   
	    /*public static MTLServer mtl_server = null;
	    public static LVLServer lvl_server = null;
	    public static DDOServer ddo_server = null;*/
	    public static DCMS server;
	    public static String recordID;
	    public static String serverLocation;
	    public static int managerIDbase =1000;
	
	
	public static void main(String[] args)
	
	{
		
		   System.out.println("Enter manager ID : ");
		    Scanner sc = new Scanner(System.in);
		    managerID = sc.next();
		    serverLocation = managerID.substring(0, 3).toUpperCase();
		    server = getServer(args, serverLocation);
			while(true)  {

			System.out.println("Select from following operations:");

			System.out.println("1.create teacher record:");

			System.out.println("2.create student record:");

			System.out.println("3.Get record counts:");

			System.out.println("4.Edit Record:");

			System.out.println("5.Transfer Record:");
			
			System.out.println("5.Exit as the current manager:");
			
			System.out.println("6.logout:");
			
			int user_choice=sc.nextInt();

			if(user_choice==1){
				
				get_inpput();
				server.createTRecord(managerID, First_name, Last_name, Address, Phone, Specialization, Location);
			}
			
			else if(user_choice==2){

				get_studentinput();

				server.createSRecord(managerID,First_name, Last_name, CoursesRegistered, Status, date);

			}

			else if(user_choice==3){

				System.out.println(server.getRecordCounts(managerID));

			}
			else if(user_choice==4){
				
				 get_editinp();
				server.editRecord(managerID,RecordID, fieldName, newValue);
			}
			
			else if(user_choice==5){
				
				get_transferinp();
				server.transferRecord(managerID, RecordID, serverLocation);
				
			}
			}
		
		    
		
	}
	
private static void get_transferinp() {
		// TODO Auto-generated method stub
	
	Scanner s=new Scanner(System.in);
	System.out.println("Enter recordID");
	RecordID= s.nextLine();
	System.out.println("which server you want to transfer?");
	serverLocation=s.nextLine();
	
		
	}




private static void get_inpput() {
		// TODO Auto-generated method stub
		
	     
Scanner s=new Scanner(System.in);

	

	System.out.println("Enter First Name");

	First_name=s.nextLine();

	System.out.println("Enter Last Name");

	Last_name=s.nextLine();

	System.out.println("Enter Address");
	Address=s.nextLine();
	
	System.out.println("Enter specilization");
	
	Specialization=s.nextLine();
	
	System.out.println("Enter phone");
	
	Phone=s.nextLine();
	
	System.out.println("Ener Location");
	
	Location = s.nextLine();
	
	
	}



public static DCMS getServer(String[] args, String serverLocation) {
    
    
    try{
        // create and initialize the ORB
        ORB orb = ORB.init(args, null);
         // get the root naming context
        org.omg.CORBA.Object objRef = 
            orb.resolve_initial_references("NameService");
        // Use NamingContextExt instead of NamingContext. This is 
        // part of the Interoperable naming Service.  
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
     // resolve the Object Reference in Naming
        String name = serverLocation;
        return DCMSHelper.narrow(ncRef.resolve_str(name));
    }
    catch(Exception e) {
        e.printStackTrace();
    }
    return null;
}



private static void get_editinp() {
	// TODO Auto-generated method stub
      Scanner s=new Scanner(System.in);

	System.out.println("Enter record id");
	RecordID=s.nextLine();
	System.out.println("Enter field name ");
	fieldName=s.nextLine();
	System.out.println("Enter new value that you want to update");
	newValue=s.nextLine();
}


private static  void get_studentinput() {

	// TODO Auto-generated method stub

	

	Scanner s=new Scanner(System.in);

	

	System.out.println("Enter First Name");

	First_name=s.nextLine();




	System.out.println("Enter Last Name");

	Last_name=s.nextLine();

	

	System.out.println("Enter courses registered");

	CoursesRegistered=s.nextLine();

	

	System.out.println("Enter status");

	Status=s.nextLine();

	

	System.out.println("Enter Date");

	date=s.nextLine();

}
}