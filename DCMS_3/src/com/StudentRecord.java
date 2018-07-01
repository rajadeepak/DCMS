package com;

import java.util.Date;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="StudentRecord")

public class StudentRecord extends Record {
	String CoursesRegistered,Status;
	String date;
	StudentRecord(){}
 StudentRecord(String firstname, String lastname, String bloop,String course,String status,String dt) {
		super(firstname, lastname, bloop);
		// TODO Auto-generated constructor stub
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

 public void updateStatusDate(String date) {
     this.date = date;
 }

 public String getStatusDate() {
     return date;
 }


}
