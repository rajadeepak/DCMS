package com;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public interface RequestServer extends Remote {
	int createTRecord (String firstName, String lastName, String address, String phone, String specialization, String location, String managerId) throws RemoteException;
	boolean createSRecord (String firstName, String lastName, String courseRegistered, String status, Date statusDate, String managerId) throws RemoteException;
	ArrayList<String> getRecordCounts (String managerId) throws RemoteException;
	boolean editRecord (String recordID, String fieldName, String newValue, String managerId) throws RemoteException,ParseException;
}
