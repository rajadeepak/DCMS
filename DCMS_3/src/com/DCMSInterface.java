package com;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService

public interface DCMSInterface {

	@WebMethod
	public String createTRecord (String ManagerID,String firstName, String lastName, String address, String phone, String specialization, String location);
	public String createSRecord (String ManagerID,String firstName, String lastName, String courseRegistered,  String status, String statusDate);
}
