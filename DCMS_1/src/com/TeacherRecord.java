package com;

public class TeacherRecord extends Record {
	String Address;
	String  Phone;
    String Specialization;
	String Location;

	TeacherRecord(String firstname, String lastname,String address, String phone,String specialization,String location, String redordID) {
		super(firstname, lastname, redordID);
		// TODO Auto-generated constructor stub
		
		Address=address;
		Phone=phone;
		Specialization=specialization;
		Location=location;
	}

}
