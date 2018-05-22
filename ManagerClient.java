package com;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagerClient {

	static String First_name;
	static String Last_name;
	static String Address;
	static String Phone;
    static String Specialization;
	static String Location;
    static String CoursesRegistered;
    static String Status;
    static Date date;
	
	public static void main(String[] args) {
	InterfaceRMI rmi_obj;
	
	
	System.out.println("Enter manager id:");
	Scanner sc=new Scanner(System.in);
	String ManagerID=sc.next();
	
	if(ManagerID.substring(0,3).equalsIgnoreCase("MTL")){
		
		
		rmi_obj=new MTLServer();
		
		
	}
	else if(ManagerID.substring(0,3).equalsIgnoreCase("LVL")){
		
		rmi_obj=new LVLServer();
		
	}
	else if(ManagerID.substring(0,3).equalsIgnoreCase("DDO")){
		
		rmi_obj=new DDOServer();
		
	}
	else
	{
		System.out.println("bloop");
		rmi_obj=new DDOServer();
	}

	while(true)  {
	System.out.println("1.Select from following operations:");
	System.out.println("2.create teacher record:");
	System.out.println("3.create student record:");
	System.out.println("4.Get record counts:");
	System.out.println("5.Edit Record:");
	System.out.println("6.Exit:");
	int user_choice=sc.nextInt();
	if(user_choice==1){
		get_inpput();
		rmi_obj.createTRecord(First_name, Last_name, Address, Phone, Specialization, Location);
	}
	else if(user_choice==2){
		get_studentinput();
		rmi_obj.createSRecord(First_name, Last_name, CoursesRegistered, Status, date);
	}
	else if(user_choice==3){
		System.out.println(rmi_obj.getRecordCounts());
		System.out.println("BTEE");
	}

}
	}


	private static void get_studentinput() {
		// TODO Auto-generated method stub
		String bloop;
		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
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
		bloop=s.nextLine();
		try{
			date = sourceFormat.parse(bloop);
		}catch(Exception e){
			System.out.println(e);
		}
		
		
		
	}


	private static void get_inpput() {
		// TODO Auto-generated method stub
		System.out.println("Enter First Name");
		Scanner s=new Scanner(System.in);
		First_name=s.nextLine();
		System.out.println("Enter Last Name");
		Last_name=s.nextLine();
		System.out.println("Enter address");
		Address=s.nextLine();
		System.out.println("Enter phone number");
		Phone=s.nextLine();
		System.out.println("Enter specialization");
		Specialization=s.nextLine();
		System.out.println("Enter location");
		Location=s.nextLine();
		
	}
}
