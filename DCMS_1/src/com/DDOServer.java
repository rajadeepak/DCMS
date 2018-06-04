package com;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class DDOServer extends UnicastRemoteObject implements InterfaceRMI {

	protected DDOServer() throws RemoteException,IOException {
		super();
		logger = new LogManager("ddo-server.log");
	}

	public static Map<String,List<Record>> database=new HashMap<String,List<Record>>();
	List<Record> records;
	Record recobj;
	private int MTLPort = 1412;
    private int LVLPort = 7875;
    private static int DDOPort = 7825;
    
    private LogManager logger = null;
	
	@Override
	public int createTRecord(String firstName, String lastName, String address, String phone, String specialization,
			String location) {

		try{	
			String key=lastName.substring(0,1);
			//System.out.println(key);
			recobj=new TeacherRecord(firstName, lastName, address, phone, specialization, location, getTeacherRecordid());
			
			synchronized(this) {
			if(DDOServer.database.containsKey(key)){
				records=DDOServer.database.get(key);
				records.add(recobj);
				DDOServer.database.put(key, records);
			}
			
			else{
				records=new ArrayList<Record>();
				records.add(recobj);
				DDOServer.database.put(key, records);
			}
			logger.writeLog("Inserted Teacher Record Number : "+ ((TeacherRecord)recobj).Record_ID);
		}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.writeLog("Error occured while trying to insert teacher record number : "+((TeacherRecord)recobj).Record_ID);
			return -1;
		}
		
	
		return DDOServer.database.size();
		
		
	
	}

	@Override
	public boolean createSRecord(String firstName, String lastName, String courseRegistered, String status, Date statusDate) {

		try{	
			String key=lastName.substring(0,1);
			recobj=new StudentRecord(firstName, lastName, getStudentRecordid(), courseRegistered, status, statusDate);
			
			synchronized(this){
			if(database.containsKey(key)){
				records=database.get(key);
				records.add(recobj);
				database.put(key, records);
				
			}
			
			else{
				records=new ArrayList<Record>();
				records.add(recobj);
				database.put(key, records);
			}
			logger.writeLog("Inserted Student Record Number : "+((StudentRecord)recobj).Record_ID);
		}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.writeLog("Error occured while trying to insert student record number : "+((StudentRecord)recobj).Record_ID);
			return false;
		}
		return true;
		
	}

	@Override
	public ArrayList<String> getRecordCounts() {
		// TODO Auto-generated method stub
		ArrayList<String> result=new ArrayList<String>();
		DatagramSocket ds = null;
		
		
		try {
			//String temp = Integer.toString(database.size());
			byte[] message = "Record Count".getBytes();
			//byte[] message = temp.getBytes();
			byte[] buffer1 = new byte[1000];
			byte[] buffer2 = new byte[1000];
			
			ds = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName("localhost");
            DatagramPacket request = new DatagramPacket(message, message.length, aHost, MTLPort);
            ds.send(request);
            
            
            DatagramPacket reply1 = new DatagramPacket(buffer1, buffer1.length);
            ds.receive(reply1);
 String response1 = new String(reply1.getData());
            
            System.out.println("1="+response1);
            System.out.println(response1.length());
            ds.close();
            
            
            ds = new DatagramSocket();
            request = new DatagramPacket(message, message.length, aHost, LVLPort);
            ds.send(request);
            
            
            DatagramPacket reply2 = new DatagramPacket(buffer2, buffer2.length);
            ds.receive(reply2);
            String response2= new String(reply2.getData());
            System.out.println("2="+response2);
            System.out.println(response2.length());
            
            
            String s="DDO "+DDOServer.database.size()+" ";
            result.add(response1.trim());
            result.add(response2.trim());
		    result.add(s);
            System.out.println(result);
            ds.close();
            		
             }
        catch(SocketException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if(ds!=null)
                ds.close();
        }
		
		
		logger.writeLog("Current system records "+result);
		return result;
	}

	@Override
	public boolean editRecord(String recordID, String fieldName, String newValue) throws ParseException {
		// TODO Auto-generated method stub
		synchronized(this){
		 Iterator tr = database.entrySet().iterator();
	        while(tr.hasNext()) {
	            Entry ent = (Entry) tr.next();
	            List<Record> recv = (List<Record>) ent.getValue();
	                Iterator it = recv.iterator();
	                while(it.hasNext()) {
	                    Record rec = (Record) it.next();
	                    if(fieldName.equalsIgnoreCase("address")) {
	                        ((TeacherRecord) rec).updateAddress(newValue);
	                        //return recordID+ "address is changed to" +newValue;
	                    }
	                    if(fieldName.equalsIgnoreCase("specialization")) {
	                        ((TeacherRecord) rec).updateSpecialization(newValue);
	                        //return recordID+ "specialization is changed to" +newValue;
	                    }
	                    if(fieldName.equalsIgnoreCase("location")) {
	                        String c = ((TeacherRecord) rec).getLocation();
	                        ((TeacherRecord) rec).updateLocation(newValue);
	                        if(newValue.equalsIgnoreCase("DDO")) {
	                            //recordCount++;
	                        }
	                        if(newValue.equalsIgnoreCase("LVL")) {
	                            //recordCount--;
	                        }
	                        else {
	                            //recordCount--;
	                        }
	                       // return recordID+ "location is changed from " +c+ "to" +newValue;
	                    }
	                    if(fieldName.equalsIgnoreCase("course")) {
	                        //String[] val = newValue.split(",");
	                       // String[] a = ((StudentRecord) rec).getCourse();
	                        ((StudentRecord) rec).updateCourse(newValue);
	                        //return recordID+ "course registeration is changed from " +a+ "to" +newValue;
	                    }
	                    if(fieldName.equalsIgnoreCase("status")) {
	                        ((StudentRecord) rec).updateStatus(newValue);
	                        String b = ((StudentRecord) rec).getStatus();
	                        //return recordID+ "status is changed from " +b+ " to" +newValue;
	                    }
	                    if(fieldName.equalsIgnoreCase("statusdate")) {
	                        DateFormat format = new SimpleDateFormat("dd/MM/yy");
	                        Date d = format.parse(newValue);
	                        ((StudentRecord) rec).updateStatusDate(d);
	                        //return recordID+ "status date is changed to" +newValue;
	                    }
	                }
	        }
			
	        logger.writeLog("Update record number : "+recordID+"; new value of field : "+fieldName+" is : "+newValue);
		}
	        return true;
			
	}
			

	public synchronized String getTeacherRecordid() {
		// TODO Auto-generated method stub
		return "TR"+ 1000+DDOServer.database.size();
			}
				
	
	public synchronized String getStudentRecordid() {
		// TODO Auto-generated method stub
		return "SR"+ 1000+DDOServer.database.size();
		}
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		DatagramSocket ddo = null;
		
		
		try{
			DDOServer dds=new DDOServer();
			Registry registry=LocateRegistry.createRegistry(2964);
			registry.bind("DDOServer", dds);
			System.out.println("DDO server started");
			
			while(true){
				ddo = new DatagramSocket(DDOPort);
				byte[] buffer = new byte[1000];
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				ddo.receive(request);
				String bloop = "DDO "+ String.valueOf(database.size()) + ", "; 
				byte[] blah = bloop.getBytes();
				DatagramPacket reply = new DatagramPacket(blah,blah.length, request.getAddress(), request.getPort());
				ddo.send(reply);

			}
		}
		
		catch(SocketException e){
			System.out.println("Socket Exception: "+e);
		}
		catch(IOException e){
			System.out.println("IO Exception: "+e);
		}
		finally{
			if(ddo != null)
				ddo.close();
		}
	}

	
}
