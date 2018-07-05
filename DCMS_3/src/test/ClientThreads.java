package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.LogManager;

import client.DCMSInterface;
import client.DDOImplService;
import client.LVLImplService;
import client.MTLImplService;

	public class ClientThreads 
	{
		static ExecutorService exec = Executors.newFixedThreadPool(10);
		public static DCMSInterface service = null;
		
		public static void main(String args[])
		{
		
			DDOImplService ddo = new DDOImplService();
			LVLImplService lvl = new LVLImplService();
			MTLImplService mtl = new MTLImplService();
			DCMSInterface ddoService = ddo.getDDOImplPort();
			DCMSInterface lvlService = lvl.getLVLImplPort();
			DCMSInterface mtlService = mtl.getMTLImplPort();
			
			Runnable runnableTask1 = () -> {
			    try {
			    	LogManager logger;
					String status;
					service = mtlService;
						logger = new LogManager("MTL001-client.log");
					status = service.createTRecord("MTL001", "first1", "last1", "address1", "1234567890", "French", "MTL");
					if(status.equalsIgnoreCase("success"))
						logger.writeLog("Operation createTRecord Success. Manager ID: MTL001");
					else
						logger.writeLog("Operation createTRecord Failed. Manager ID: MTL001");
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			};

			Runnable runnableTask2 = () -> {
					LogManager logger;
					String status;
					try {
						service = mtlService;
						logger = new LogManager("MTL002-client.log");
						status = service.createSRecord("MTL002", "first4", "last4", "english", "active", "12/05/1999");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createSRecord Success. Manager ID: MTL002");
						else
							logger.writeLog("Operation createSRecord Failed. Manager ID: MTL002");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			};
			Runnable runnableTask3 = () -> {
					LogManager logger;
					String status;
					try {
						service = ddoService;
						logger = new LogManager("DDO001-client.log");
						status = service.createTRecord("DDO001", "first2", "last2", "address2", "1234567891", "French", "MTL");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createTRecord Success. Manager ID: DDO001");
						else
							logger.writeLog("Operation createTRecord Failed. Manager ID: DDO001");

						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			};
			
			Runnable runnableTask4 = () -> {
					LogManager logger;
					String status;
					try {
						service = ddoService;
						String date;
						logger = new LogManager("DDO002-client.log");
						date = "12/05/1989";
						status = service.createSRecord("DDO002", "first23", "last23", "arabic", "active", date);
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createSRecord Success. Manager ID: DDO002");
						else
							logger.writeLog("Operation createSRecord Failed. Manager ID: DDO002");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			};
			Runnable runnableTask5 = () -> {
					LogManager logger;
					String status;
					try {
						service = lvlService;
						logger = new LogManager("LVL001-client.log");
						status = service.createTRecord("LVL001", "first3", "last3", "address3", "1234567892", "French", "MTL");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createTRecord Success. Manager ID: LVL001");
						else
							logger.writeLog("Operation createTRecord Failed. Manager ID: LVL001");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			};
			
			Runnable runnableTask6 = () -> {
					LogManager logger;
					String status;
					try {
						service = lvlService;
						String date;
						logger = new LogManager("LVL002-client.log");
						date = "12/05/1931";
						status = service.createSRecord("LVL002", "first45", "last45", "telugu", "active", date );
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation createSRecord Success. Manager ID: LVL002");
						else
							logger.writeLog("Operation createSRecord Failed. Manager ID: LVL002");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			};
			Runnable runnableTask7 = () -> {
					LogManager logger;
					String status;
					try {
						service = ddoService;
						logger = new LogManager("DDO003-client.log");
						status = service.editRecord("DDO003", "TR10000", "phone", "9494515123");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation editRecord Success. Manager ID: DDO003");
						else
							logger.writeLog("Operation editRecord Failed. Manager ID: DDO003");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			};
			Runnable runnableTask8 = () -> {
					LogManager logger;
					String status;
					try {
						service = lvlService;
						logger = new LogManager("LVL003-client.log");
						status = service.editRecord("LVL003", "TR10000", "phone", "5148022688");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation editRecord Success. Manager ID: LVL003");
						else
							logger.writeLog("Operation editRecord Failed. Manager ID: LVL003");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			};
			Runnable runnableTask9 = () -> {
					LogManager logger;
					String status;
					try {
						service = mtlService;
						logger = new LogManager("MTL003-client.log");
						status = service.transferRecord("MTL003", "TR10000", "LVL");
						if(status.equalsIgnoreCase("success"))
							logger.writeLog("Operation transferRecord Success. Manager ID: MTL003");
						else
							logger.writeLog("Operation transferRecord Failed. Manager ID: MTL003");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			};
			
			List<Runnable> runableTasks = new ArrayList<>();
			runableTasks.add(runnableTask1);
			runableTasks.add(runnableTask2);
			runableTasks.add(runnableTask3);
			runableTasks.add(runnableTask4);
			runableTasks.add(runnableTask5);
			runableTasks.add(runnableTask6);
			runableTasks.add(runnableTask7);
			runableTasks.add(runnableTask8);
			runableTasks.add(runnableTask9);
			
			for(Runnable job: runableTasks)
					exec.execute(job);
			
			exec.shutdown();
		}

	}