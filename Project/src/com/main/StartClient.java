package com.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Logger;

import org.omg.CORBA.*;
import org.omg.CosNaming.NamingContextPackage.*;
import CorbaApp.DCMS;
import CorbaApp.DCMSHelper;

import org.omg.CosNaming.*;



public class StartClient {
	
	   public static String managerID;
	   public static String First_name;
	   public static String Last_name;
	   public static String Address;
	   public static String Phone;
	   public static String Specialization;
	   public static String Location;
	   public static String CoursesRegistered;
	   public static String Status;
	   public static String date;
	   public static String RecordID;
	   public static String fieldName;
	   public static String newValue;
	   public static DCMS server;
	   public static String recordID;
	   public static String serverLocation = "FE";
	   public static int managerIDbase =1000;
	   private LogManager logger = null;
	
	   protected StartClient() throws IOException  
	   {
		 super();
	   }
	   
	   public static void main(String[] args) throws IOException
	   {
			LogManager logger = new LogManager("Client-log.log");
			Scanner sc = new Scanner(System.in);
			getManagerInput();
		    getServer(args, serverLocation);
		    String status = "";
			while(true)  {
				System.out.println("Select from following operations:");
				System.out.println("1.create teacher record:");
				System.out.println("2.create student record:");
				System.out.println("3.Get record counts:");
				System.out.println("4.Edit Record:");
				System.out.println("5.Transfer Record:");
				System.out.println("6.Logout as the current manager:");
				System.out.println("7.Exit");
			
				int user_choice=sc.nextInt();
				switch(user_choice)
				{
				case 1:
					status = "bad";
					getTeacherInput();
					status = status+ server.createTRecord(managerID, First_name, Last_name, Address, Phone, Specialization, Location);
					System.out.println("received status: "+status);
					if(status.contains("uccess"))
						logger.writeLog("Operation createTRecord Success. Manager ID: "+managerID);
					else
						logger.writeLog("Operation createTRecord Failed. Manager ID: "+managerID);
					break;
				case 2:
					getStudentInput();
					status = server.createSRecord(managerID,First_name, Last_name, CoursesRegistered, Status, date);
					if(status.equalsIgnoreCase("success"))
						logger.writeLog("Operation createSRecord Success. Manager ID: "+managerID);
					else
						logger.writeLog("Operation createSRecord Failed. Manager ID: "+managerID);
					break;
				case 3:
					System.out.println(server.getRecordCounts(managerID));
					logger.writeLog("Operation getRecordCounts Success. Manager ID: "+managerID);
					break;
				case 4:
					getEditRecordInput();
					status = server.editRecord(managerID,RecordID, fieldName, newValue);
					if(status.equalsIgnoreCase("success"))
						logger.writeLog("Operation editRecord Success. Manager ID: "+managerID);
					else
						logger.writeLog("Operation editRecord Failed. Manager ID: "+managerID);
					break;
				case 5:
					getTransferInput();
					status = server.transferRecord(managerID, RecordID, serverLocation);
					if(status.equalsIgnoreCase("success"))
						logger.writeLog("Operation transferRecord Success. Manager ID: "+managerID);
					else
						logger.writeLog("Operation transferRecord Failed. Manager ID: "+managerID);
					break;
				case 6:
					System.out.println("Manager logged Out. Please login again");
					getManagerInput();
				    getServer(args, serverLocation);
					break;
				case 7:
					System.out.println("Thank you. Exiting!");
					return;
				default:
					System.out.println("Invalid Option. Please select again");
					break;
				}
			}
	}
	
	public static void getManagerInput()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter manager ID : ");
	    managerID = sc.next();
	}
	
	private static void getTransferInput() 
	{
		Scanner s=new Scanner(System.in);
		System.out.println("Enter recordID");
		RecordID= s.nextLine();
		System.out.println("which server you want to transfer?");
		serverLocation=s.nextLine();
	}

	private static void getTeacherInput() 
	{
		Scanner s = new Scanner(System.in);
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

	public static void getServer(String[] args, String serverLocation) 
	{
		try{
//			java.util.Properties p = new java.util.Properties();
//			p.setProperty("com.sun.CORBA.codeset.charsets", "0x05010001, 0x00010109");    // UTF-8, UTF-16
//			p.setProperty("com.sun.CORBA.codeset.wcharsets", "0x00010109, 0x05010001");    // UTF-16, UTF-8
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = serverLocation;
			server= DCMSHelper.narrow(ncRef.resolve_str(name));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void getEditRecordInput() 
	{
		Scanner s=new Scanner(System.in);
		System.out.println("Enter record id");
		RecordID=s.nextLine();
		System.out.println("Enter field name ");
		fieldName=s.nextLine();
		System.out.println("Enter new value that you want to update");
		newValue=s.nextLine();
	}

	private static  void getStudentInput() 
	{
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