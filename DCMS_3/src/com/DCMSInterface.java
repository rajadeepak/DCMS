package com;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService

public interface DCMSInterface {

	public String createTRecord (@WebParam(name="ManagerID") String ManagerID, @WebParam(name="FirstName")  String firstName, @WebParam(name="LastName") String lastName, @WebParam(name="Address")String address, @WebParam(name="Phone")String phone, @WebParam(name="Specialization")String specialization, @WebParam(name="Location")String location);
	public String createSRecord (@WebParam(name="ManagerID") String ManagerID, @WebParam(name="FirstName")  String firstName, @WebParam(name="LastName") String lastName, @WebParam(name="CoursesRegistered") String courseRegistered,  @WebParam(name="Status") String status, @WebParam(name="StatusDate") String statusDate);
	public String getRecordCounts (@WebParam(name="ManagerID") String ManagerID);
	public String editRecord (@WebParam(name="ManagerID") String ManagerID, @WebParam(name="RecordID") String recordID, @WebParam(name="FieldName") String fieldName, @WebParam(name="NewValue") String newValue);
	public String transferRecord(@WebParam(name="ManagerID") String managerID, @WebParam(name="RecordID") String recordID, @WebParam(name="NewServerName") String remoteCenterServerName);
	
}
