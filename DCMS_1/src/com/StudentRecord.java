package com;

import java.util.Date;

public class StudentRecord extends Record {
	String CoursesRegistered,Status;
	Date date;
	String Record_ID;
	StudentRecord(){}
 StudentRecord(String firstname, String lastname, String bloop,String course,String status,Date dt) {
		super(firstname, lastname);
		// TODO Auto-generated constructor stub
		Record_ID=bloop;
		CoursesRegistered=course;
		Status=status;
		date=dt;
		
	}

 public void updateRecordID(String id)
 {
	  Record_ID = id;
 }

 public String getRecordID(){
     return  Record_ID;
 }

 public void updateCourse(String course) {
     this.CoursesRegistered = course;
 }

 public String getCourse() {
     return CoursesRegistered;
 }

 public void updateStatus(String status) {
     this.Status = status;
 }

 public String getStatus() {
     return Status;
 }

 public void updateStatusDate(Date date) {
     this.date = date;
 }

 public Date getStatusDate() {
     return date;
 }


}
