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
import java.util.*;

import javax.sound.midi.Synthesizer;

public class DDOServer extends UnicastRemoteObject implements InterfaceRMI {

	protected DDOServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Map<String,List<Record>> database=new HashMap<String,List<Record>>();
	List<Record> records;
	private   int recordCount=0;
	Record recobj;
	private int MTLPort = 1412;
    private int LVLPort = 7875;
    private static int DDOPort = 7825;
    
	
	@Override
	public int createTRecord(String firstName, String lastName, String address, String phone, String specialization,
			String location) {
		// TODO Auto-generated method stub

		boolean flag = true;
		try{	
			String key=lastName.substring(0,1);
			//System.out.println(key);
			recobj=new TeacherRecord(firstName, lastName, address, phone, specialization, location, "TR"+ 1000+DDOServer.database.size());
			
			if(DDOServer.database.containsKey(key)){
				records=DDOServer.database.get(key);
				records.add(recobj);
				DDOServer.database.put(key, records);
				recordCount++;
				
			}
			
			else{
				records=new ArrayList<Record>();
				records.add(recobj);
				DDOServer.database.put(key, records);
				recordCount++;
			}
			System.out.println("size of ddo"+DDOServer.database.size());
		}
		catch(Exception e){
			System.out.println(e);
			flag = false;
		}
		finally{
			/*if (flag)
			{
				
			}*/
			/*for(Map.Entry<String, List<Record>> e : database.entrySet()){
			   for(Record e1 : e.getValue())
			      System.out.println(e.getKey() + " = "+ e1.First_name+" "+e1.Last_name+" "+e1.Record_ID);
			}*/
				
			//write to log
		}
		
		/*for(Map.Entry<String, List<Record>> e : database.entrySet()){
			   for(Record e1 : e.getValue())
			      System.out.println(e.getKey() + " = "+ e1.First_name+" "+e1.Last_name+" "+e1.Record_ID);
			}*/
	System.out.println("recordCount:"+ database.size());
		return DDOServer.database.size();
		
		
	
	}

	@Override
	public boolean createSRecord(String firstName, String lastName, String courseRegistered, String status, Date statusDate) {
		// TODO Auto-generated method stub
		

		boolean flag = true;
		try{	
			String key=lastName.substring(0,1);
			//System.out.println(key);
			recobj=new StudentRecord(firstName, lastName, "ST"+ 1000+database.size(), courseRegistered, status, statusDate);
			
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
			
		}
		catch(Exception e){
			System.out.println(e);
			flag = false;
		}
		finally{
			/*if (flag)
			{
				
			}*/
			/*for(Map.Entry<String, List<Record>> e : database.entrySet()){
			   for(Record e1 : e.getValue())
			      System.out.println(e.getKey() + " = "+ e1.First_name+" "+e1.Last_name+" "+e1.Record_ID);
			}*/
	
				
			//write to log
		}
		
		/*for(Map.Entry<String, List<Record>> e : database.entrySet()){
			   for(Record e1 : e.getValue())
			      System.out.println(e.getKey() + " = "+ e1.First_name+" "+e1.Last_name+" "+e1.Record_ID);
			}*/
		return true;
		
	}

	@Override
	public String getRecordCounts() {
		// TODO Auto-generated method stub
		
		System.out.println("in getrecordcounts"+database.size());
		DatagramSocket ds = null;
		String response1, response2;
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
            response1 = new String(reply1.getData());
            System.out.println("1st response inside DDO"+response1);
            ds.close();
            
            
            ds = new DatagramSocket();
            request = new DatagramPacket(message, message.length, aHost, LVLPort);
            ds.send(request);
            
            
            DatagramPacket reply2 = new DatagramPacket(buffer2, buffer2.length);
            ds.receive(reply2);
            response2 = new String(reply2.getData());
            System.out.println("2nd response inside DDO"+response2);
            ds.close();
            		
            String response = response1+response2+ ",DDO" +database.size();
            System.out.println("printing response"+response1+","+response2+ ",DDO" +database.size());
            System.out.println("Inside DDO Method");
            return response;
             }
        catch(SocketException e) {
            return "Socket:" +e.getMessage();
        }
        catch(IOException e) {
            return "IO:" +e.getMessage();
        }
        finally {
            if(ds!=null)
                ds.close();
        }
	}

	@Override
	public boolean editRecord(String recordID, String fieldName, String newValue) {
		// TODO Auto-generated method stub
		
		for(Map.Entry<String, List<Record>> e : database.entrySet())
		{
			String bloop = "";
			List<Record> blah;
			for(Record e1 : e.getValue())
				if(e1.Record_ID.equalsIgnoreCase(recordID))
					bloop = e1.Last_name.substring(0, 1);
			
			blah = database.get(bloop);
			String bleep;
			
			for(int i=0; i<blah.size();i++ ){
				System.out.println(blah.get(i));
				if(blah.get(i).equals(fieldName))
					System.out.println("Hurrayyyyy");
			}
				
		}
		
		return false;
	}

	@Override
	public int getrecordcount() {
		// TODO Auto-generated method stub
		return DDOServer.database.size();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		DatagramSocket ddo = null;
		
		
		try{
			DDOServer dds=new DDOServer();
			Registry registry=LocateRegistry.createRegistry(2964);
			registry.bind("DDOServer", dds);
			System.out.println("DDO server started");
			ddo = new DatagramSocket(DDOPort);
			byte[] buffer = new byte[1000];
			while(true){
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				System.out.println("checking requt:"+request.getData().toString());
				ddo.receive(request);
				System.out.println(request.getPort()+""+request.toString());
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
