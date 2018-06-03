package com;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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

	static String ManagerID;
	
	static Registry registry;
	
	static int TrecordCount = 0;
	
	static RequestServer rqServer = null;
	
 static String RecordID;
	 
	 static String fieldName;
	 static String newValue;
	public static void main(String[] args) throws Exception {

		default_inp();

		Scanner sc = new Scanner(System.in);
		
		while (true) {

			System.out.println("Select from following operations:");

			System.out.println("1.create teacher record:");

			System.out.println("2.create student record:");

			System.out.println("3.Get record counts:");

			System.out.println("4.Edit Record:");

			System.out.println("5.Exit as the current manager:");

			System.out.println("6.logout:");

			int user_choice = sc.nextInt();

			if (user_choice == 1) {

				get_inpput();
				TrecordCount = (rqServer.createTRecord(First_name, Last_name, Address, Phone, Specialization, Location, ManagerID));

			}

			else if (user_choice == 2) {

				get_studentinput();

				rqServer.createSRecord(First_name, Last_name, CoursesRegistered, Status, date, ManagerID);

			}

			else if (user_choice == 3) {

				ArrayList<String> res= rqServer.getRecordCounts(ManagerID);
				for(int i=0;i<res.size();i++){
					System.out.print(res.get(i));
				}


			} else if (user_choice == 4) {
				
				 get_editinp();
				 System.out.print(rqServer.editRecord(RecordID, fieldName, newValue,ManagerID));
			} else if (user_choice == 5) {
				default_inp();
			}
			else if(user_choice==6){
				System.out.println("GOODBYE!!");
				System.exit(0);
			}

		}
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


	private static void default_inp() throws Exception {
		
		System.out.println("Enter manager id:");

		Scanner sc = new Scanner(System.in);

		ManagerID = sc.next();

		registry = LocateRegistry.getRegistry(9001);
		rqServer = (RequestServer) registry.lookup("RequestServer");
		
	}


	private static void get_studentinput() {

		// TODO Auto-generated method stub

		String bloop;

		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");

		Scanner s = new Scanner(System.in);

		System.out.println("Enter First Name");

		First_name = s.nextLine();

		System.out.println("Enter Last Name");

		Last_name = s.nextLine();

		System.out.println("Enter courses registered");

		CoursesRegistered = s.nextLine();

		System.out.println("Enter status");

		Status = s.nextLine();

		System.out.println("Enter Date");

		bloop = s.nextLine();

		try {

			date = sourceFormat.parse(bloop);

		} catch (Exception e) {

			System.out.println(e);

		}

	}

	private static void get_inpput() {

		// TODO Auto-generated method stub

		System.out.println("Enter First Name");

		Scanner s = new Scanner(System.in);

		First_name = s.nextLine();

		System.out.println("Enter Last Name");

		Last_name = s.nextLine();

		System.out.println("Enter address");

		Address = s.nextLine();

		System.out.println("Enter phone number");

		Phone = s.nextLine();

		System.out.println("Enter specialization");

		Specialization = s.nextLine();

		System.out.println("Enter location");

		Location = s.nextLine();

	}

}