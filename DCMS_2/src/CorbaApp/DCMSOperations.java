package CorbaApp;


/**
* CorbaApp/DCMSOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Interface.idl
* Wednesday, 13 June, 2018 8:12:02 PM AKDT
*/

public interface DCMSOperations 
{
  String createTRecord (String ManagerID, String firstName, String lastName, String address, String phone, String specialization, String location);
  String createSRecord (String ManagerID, String firstName, String lastName, String courseRegistered, String status, String statusDate);
  String getRecordCounts (String ManagerID);
  String editRecord (String ManagerID, String recordID, String fieldName, String newValue);
  String transferRecord (String managerID, String recordID, String remoteCenterServerName);
} // interface DCMSOperations
