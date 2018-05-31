package com;

import java.util.Date;

public class StudentRecord extends Record {
	String CoursesRegistered,Status;
	Date date;
 StudentRecord(String firstname, String lastname, String bloop,String course,String status,Date dt) {
		super(firstname, lastname, bloop);
		// TODO Auto-generated constructor stub
		CoursesRegistered=course;
		Status=status;
		date=dt;
		
	}




}
