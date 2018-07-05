package test;

import java.io.IOException;
import java.util.Scanner;
import javax.xml.ws.WebServiceRef;

import com.LogManager;



import client.DCMSInterface;
import client.DDOImplService;
import client.LVLImplService;
import client.MTLImplService;




public class StartClient {
	@WebServiceRef(wsdlLocation="http://localhost:7777/ddo, http://localhost:7777/lvl, http://localhost:7777/mtl")
	
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
	public static String recordID;
	public static String serverLocation;
	public static int managerIDbase =1000;
	private static LogManager logger = null;
	public static Scanner sc;
//	public static DDOImplService ddo = new DDOImplService();
//	public static LVLImplService lvl = new LVLImplService();
//	public static MTLImplService mtl = new MTLImplService();
//	public static DCMSInterface ddoService = ddo.getDDOImplPort();
//	public static DCMSInterface lvlService = lvl.getLVLImplPort();
//	public static DCMSInterface mtlService = mtl.getMTLImplPort();
	public static DCMSInterface service = null;
	
	public StartClient() throws IOException{
		
		 super();
		 logger = new LogManager("Client-log.log");
	}
	
	
	public static void main(String[] args) throws IOException{
		
		DDOImplService ddo = new DDOImplService();
		LVLImplService lvl = new LVLImplService();
		MTLImplService mtl = new MTLImplService();
		DCMSInterface ddoService = ddo.getDDOImplPort();
		DCMSInterface lvlService = lvl.getLVLImplPort();
		DCMSInterface mtlService = mtl.getMTLImplPort();
		
		StartClient orike = new StartClient();
		sc = new Scanner(System.in);
		serverLocation = getManagerInput();
		service = getService(serverLocation, ddoService, lvlService, mtlService);

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
				getTeacherInput();
				status = service.createTRecord(managerID, First_name, Last_name, Address, Phone, Specialization, Location);
				System.out.println(status);
				if(status.equalsIgnoreCase("success"))
					logger.writeLog("Operation createTRecord Success. Manager ID: "+managerID);
				else
					logger.writeLog("Operation createTRecord Failed. Manager ID: "+managerID);
				break;
			case 2:
				getStudentInput();
				status = service.createSRecord(managerID,First_name, Last_name, CoursesRegistered, Status, date);
				if(status.equalsIgnoreCase("success"))
					logger.writeLog("Operation createSRecord Success. Manager ID: "+managerID);
				else
					logger.writeLog("Operation createSRecord Failed. Manager ID: "+managerID);
				break;
			case 3:
				System.out.println(service.getRecordCounts(managerID));
				logger.writeLog("Operation getRecordCounts Success. Manager ID: "+managerID);
				break;
			case 4:
				getEditRecordInput();
				status = service.editRecord(managerID,RecordID, fieldName, newValue);
				if(status.equalsIgnoreCase("success"))
					logger.writeLog("Operation editRecord Success. Manager ID: "+managerID);
				else
					logger.writeLog("Operation editRecord Failed. Manager ID: "+managerID);
				break;
			case 5:
				getTransferInput();
				status = service.transferRecord(managerID, RecordID, serverLocation);
				if(status.equalsIgnoreCase("success"))
					logger.writeLog("Operation transferRecord Success. Manager ID: "+managerID);
				else
					logger.writeLog("Operation transferRecord Failed. Manager ID: "+managerID);
				break;
			case 6:
				System.out.println("Manager logged Out. Please login again");
				serverLocation = getManagerInput();
				service = getService(serverLocation, ddoService, lvlService, mtlService);
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
	
	public static DCMSInterface getService(String serverLocation, DCMSInterface ddoService, DCMSInterface lvlService, DCMSInterface mtlService)
	{
		if(serverLocation.equalsIgnoreCase("DDO"))
			service = ddoService;
		
		else if(serverLocation.equalsIgnoreCase("LVL"))
			service = lvlService;

		else if(serverLocation.equalsIgnoreCase("MTL"))
			service = mtlService;

		return service;
	}
	public static String getManagerInput() {
		
		sc = new Scanner(System.in);
		System.out.println("Enter manager ID : ");
	    managerID = sc.next();
	    serverLocation = managerID.substring(0, 3).toUpperCase();
	    return serverLocation;
	}


	private static void getTransferInput() {
		
		sc = new Scanner(System.in);
		System.out.println("Enter recordID");
		RecordID= sc.nextLine();
		System.out.println("which server you want to transfer?");
		serverLocation=sc.nextLine();
	}

	private static void getTeacherInput() {
		
		sc = new Scanner(System.in);
		System.out.println("Enter First Name");
		First_name=sc.nextLine();
		System.out.println("Enter Last Name");
		Last_name=sc.nextLine();
		System.out.println("Enter Address");
		Address=sc.nextLine();
		System.out.println("Enter specilization");
		Specialization=sc.nextLine();
		System.out.println("Enter phone");
		Phone=sc.nextLine();
		System.out.println("Ener Location");
		Location = sc.nextLine();
	}

	private static void getEditRecordInput() {

		sc=new Scanner(System.in);
		System.out.println("Enter record id");
		RecordID=sc.nextLine();
		System.out.println("Enter field name ");
		fieldName=sc.nextLine();
		System.out.println("Enter new value that you want to update");
		newValue=sc.nextLine();
	}

	private static  void getStudentInput() {
		
		sc = new Scanner(System.in);
		System.out.println("Enter First Name");
		First_name=sc.nextLine();
		System.out.println("Enter Last Name");
		Last_name=sc.nextLine();
		System.out.println("Enter courses registered");
		CoursesRegistered=sc.nextLine();
		System.out.println("Enter status");
		Status=sc.nextLine();
		System.out.println("Enter Date");
		date=sc.nextLine();
	}
}