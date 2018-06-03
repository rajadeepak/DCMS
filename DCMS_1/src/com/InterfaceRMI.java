package com;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public interface InterfaceRMI extends Remote {
	
	int createTRecord (String firstName, String lastName, String address, String phone, String specialization, String location) throws RemoteException;
	boolean createSRecord (String firstName, String lastName, String courseRegistered, String status, Date statusDate) throws RemoteException;
	ArrayList<String> getRecordCounts () throws RemoteException;
	boolean editRecord (String recordID, String fieldName, String newValue) throws RemoteException, ParseException;

}
