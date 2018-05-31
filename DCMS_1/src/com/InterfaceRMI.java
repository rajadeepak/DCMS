package com;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface InterfaceRMI extends Remote {
	
	int createTRecord (String firstName, String lastName, String address, String phone, String specialization, String location) throws RemoteException;
	boolean createSRecord (String firstName, String lastName, String courseRegistered, String status, Date statusDate) throws RemoteException;
	String getRecordCounts () throws RemoteException;
	boolean editRecord (String recordID, String fieldName, String newValue) throws RemoteException;
	public int getrecordcount() throws RemoteException;

}
