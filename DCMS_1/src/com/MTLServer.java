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

public class MTLServer extends UnicastRemoteObject implements InterfaceRMI {
	protected MTLServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	public static  Map<String,List<Record>> database=new HashMap<String,List<Record>>();
	List<Record> records;
	Record recobj;
	private static int MTLPort = 1412;
    private int LVLPort = 7875;
    private int DDOPort = 7825;

    public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		DatagramSocket ddo = null;
		
		try{
			MTLServer mts=new MTLServer();
			Registry registry=LocateRegistry.createRegistry(1332);
			registry.bind("MTLServer",mts );
			System.out.println("MTL server started");
			ddo = new DatagramSocket(MTLPort);
			byte[] buffer = new byte[1000];
			while(true)
			{
			System.out.println("Inside MTL Main");
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				ddo.receive(request);
				String bloop = "MTL "+ String.valueOf(database.size()) + ", "; 
				System.out.println("In the MTLserver main"+bloop);
				byte[] blah = bloop.getBytes();
				DatagramPacket reply = new DatagramPacket(blah, blah.length, request.getAddress(), request.getPort());
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
	@Override
	public int createTRecord(String firstName, String lastName, String address, String phone, String specialization,
			String location) {
		// TODO Auto-generated method stub
		boolean flag = true;
		try{	
			String key=lastName.substring(0,1);
			//System.out.println(key);
			recobj=new TeacherRecord(firstName, lastName, address, phone, specialization, location, "DDO"+database.size());
			
			if(database.containsKey(key)){
				records=database.get(key);
				records.add(recobj);
				database.put(key, records);
				System.out.println("size of LVL"+database.size());
			}
			
			else{
				records=new ArrayList<Record>();
				records.add(recobj);
				database.put(key, records);
			}
			System.out.println("size of LVL"+database.size());
		}
		catch(Exception e){
			System.out.println(e);
			flag = false;
		}
		finally{
			if (flag)
			{
				
			}
			else;
				
			//write to log
		}
		
		/*for(Map.Entry<String, List<Record>> e : database.entrySet()){
			   for(Record e1 : e.getValue())
			      System.out.println(e.getKey() + " = "+ e1.First_name+" "+e1.Last_name+" "+e1.Record_ID);
			}*/
		return MTLServer.database.size();
		
		
		
	}

	@Override
	public boolean createSRecord(String firstName, String lastName, String courseRegistered, String status, Date statusDate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getRecordCounts() {
		// TODO Auto-generated method stub
				DatagramSocket ds = null;
				String response1, response2;
				try {
					String temp = Integer.toString(database.size());
					//byte[] message = "Record Count".getBytes();
					byte[] message = temp.getBytes();
					byte[] buffer1 = new byte[1000];
					byte[] buffer2 = new byte[1000];
					
					ds = new DatagramSocket();
		            InetAddress aHost = InetAddress.getByName("localhost");
		            DatagramPacket request = new DatagramPacket(message, message.length, aHost, DDOPort);
		            ds.send(request);
		            
		            
		            DatagramPacket reply1 = new DatagramPacket(buffer1, buffer1.length);
		            ds.receive(reply1);
		            response1 = new String(reply1.getData());
		            
		            ds.close();
		            
		            
		            ds = new DatagramSocket();
		            request = new DatagramPacket(message, message.length, aHost, LVLPort);
		            ds.send(request);
		            
		            
		            DatagramPacket reply2 = new DatagramPacket(buffer2, buffer2.length);
		            ds.receive(reply2);
		            response2 = new String(reply2.getData());
		            
		            ds.close();
		            		
		            String response = "DDO " +response1+ ",LVL " +response2+ ",MTL" +database.size();
		            System.out.println("Inside MTL Method");
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
		return false;
	}

}
