package test;

import java.util.Scanner;

import client.DCMSInterface;
import client.DDOImplService;

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
	    public static String recordID;
	    public static String serverLocation;
	    public static int managerIDbase =1000;
	
	
	public static void main(String[] args)
	
	{
		  DDOImplService serviecDDO = new DDOImplService();
		  DCMSInterface dcms = serviecDDO.getDDOImplPort();
		  dcms.createTRecord("123", "456", "459", "456", "arg4", "arg5", "arg6");
		    
//		   System.out.println("Enter manager ID : ");
//		    Scanner sc = new Scanner(System.in);
//		    managerID = sc.next();
//		    serverLocation = managerID.substring(0, 3).toUpperCase();
//		    
//		    
//		  
//		    
//		    while(true)  {
//
//			System.out.println("Select from following operations:");
//
//			System.out.println("1.create teacher record:");
//
//			System.out.println("2.create student record:");
//
//			System.out.println("3.Get record counts:");
//
//			System.out.println("4.Edit Record:");
//
//			System.out.println("5.Exit as the current manager:");
//			
//			System.out.println("6.logout:");
//			
//			int user_choice=sc.nextInt();
//
//			if(user_choice==1){
//				
//				get_inpput();
//				//System.out.println(managerID+" "+ First_name+" "+ Last_name+" "+Address+" "+ Phone+" "+Specialization+" "+Location);
//				dcms.createTRecord(managerID, First_name, Last_name, Address, Phone, Specialization, Location);
//			}
//			
//			else if(user_choice==2){
//
//				get_studentinput();
//
//				dcms.createSRecord(managerID,First_name, Last_name, CoursesRegistered, Status, date);
//
//			}

//			else if(user_choice==3){
//
//				System.out.println(dcms.getRecordCounts(managerID));
//
//			}
//			else if(user_choice==4){
//				
//				 get_editinp();
//				System.out.print(dcms.editRecord(managerID,RecordID, fieldName, newValue));
//			}
			

		
		    
		
//	}
	
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