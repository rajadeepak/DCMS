package com;

import java.util.Date;

public interface InterfaceRMI {
	
	boolean createTRecord (String firstName, String lastName, String address, String phone, String specialization, String location);
	boolean createSRecord (String firstName, String lastName, String courseRegistered, String status, Date statusDate);
	String getRecordCounts ();
	boolean editRecord (String recordID, String fieldName, String newValue);

}
