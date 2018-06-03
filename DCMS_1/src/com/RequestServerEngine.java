/**
 * 
 */
package com;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 * @author harvi
 *
 */
public class RequestServerEngine extends UnicastRemoteObject implements RequestServer {

	/**
	 * 
	 */
	
	private InterfaceRMI ddoServer = null;
	private InterfaceRMI lvlServer = null;
	private InterfaceRMI mtlServer = null;
	
	private static final long serialVersionUID = 1281406397068509163L;

	protected RequestServerEngine() throws RemoteException {
		super();
		try {
			Registry registry = LocateRegistry.getRegistry(2964);
			ddoServer = (InterfaceRMI) registry.lookup("DDOServer");
			
			registry = LocateRegistry.getRegistry(7878);
			lvlServer = (InterfaceRMI) registry.lookup("LVLServer");
			
			registry = LocateRegistry.getRegistry(1332);
			mtlServer = (InterfaceRMI) registry.lookup("MTLServer");
			
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.RequestServer#createTRecord(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int createTRecord(String firstName, String lastName, String address, String phone, String specialization,
			String location, String managerId) throws RemoteException {
		InterfaceRMI server = locateServerForManager(managerId);
		return server.createTRecord(firstName, lastName, address, phone, specialization, location);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.RequestServer#createSRecord(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.util.Date, java.lang.String)
	 */
	@Override
	public boolean createSRecord(String firstName, String lastName, String courseRegistered, String status,
			Date statusDate, String managerId) throws RemoteException {
		InterfaceRMI server = locateServerForManager(managerId);
		return server.createSRecord(firstName, lastName, courseRegistered, status, statusDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.RequestServer#getRecordCounts(java.lang.String)
	 */
	@Override
	public String getRecordCounts(String managerId) throws RemoteException {
		InterfaceRMI server = locateServerForManager(managerId);
		return server.getRecordCounts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.RequestServer#editRecord(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean editRecord(String recordID, String fieldName, String newValue, String managerId)
			throws RemoteException {
		InterfaceRMI server = locateServerForManager(managerId);
		return server.editRecord(recordID, fieldName, newValue);
	}

	private InterfaceRMI locateServerForManager(String managerId){
		String center = managerId.substring(0, 3).toUpperCase();
		switch(center){
		case "MTL":
			return mtlServer;
		case "LVL":
			return lvlServer;
		case "DDO":
			return ddoServer;
		default:
			return null;
		}
	}
	
	public static void main(String[] args) {

		 try {
			RequestServerEngine rse = new RequestServerEngine();
			Registry registry = LocateRegistry.createRegistry(9001);
			registry.bind("RequestServer", rse);
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}
