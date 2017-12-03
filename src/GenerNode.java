import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class GenerNode {
	static HashMap map = new HashMap();
	static int flag = 0;
	
    public static void main(String[] args) throws IOException {
	    	switch(args[0]){
	    		case ("uc"):{
	        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(args[1]));
	        DatagramPacket socket = null;
	        int count = 0;
	        System.out.println("***UDP Server is on, Waiting for data***");
	        
	        byte[] data = null;
	        while(true){
	            data = new byte[1024];//创建字节数组，指定接收的数据包的大小
	            socket = new DatagramPacket(data, data.length);
	            serverSocket.receive(socket);//此方法在接收到数据报之前会一直阻塞
	            Thread thread = new Thread(new ServerThreaduc(serverSocket, socket));
	            thread.start();
	            count++;
	            System.out.println("times:"+count);
	            InetAddress address = socket.getAddress();
	            System.out.println("connected IP："+address.getHostAddress());
	            if (flag == 1) {
	            	serverSocket.close();
	            }
	            
	        }
	       
	    		}
	    		case ("tc") :{
	    		try {
	                ServerSocket serverSocket1 = new ServerSocket(Integer.parseInt(args[1]));
	                Socket socket1 = null;
	                int count = 0;
	                System.out.println("***TCP Server is on, Waiting for data***");
	                
	                while(true){
	                    socket1 = serverSocket1.accept();
	                    Thread thread = new Thread(new ServerThreadTC(socket1));
	                    thread.start();
	                    count++;
	                    System.out.println("times:"+count);
	                    InetAddress address = socket1.getInetAddress();
	                    System.out.println("connected IP："+address.getHostAddress());
	                    if(flag == 1) {
	                    	serverSocket1.close();
	                    }
	                    
	                }            
	            } catch (IOException e) {
	                e.printStackTrace();
	            }

	    		}
	    		case ("rmic") :{
	    			try { 
	            	    IHello rhello = new HelloImpl(); 
	            	    LocateRegistry.createRegistry(Integer.parseInt(args[1])); 
	            		String address = "rmi://localhost:"+args[1]+"/RHello";
	                Naming.bind(address,rhello); 
	                System.out.println(">>>>>Successfully Binding remote object"); 
	            } catch (RemoteException e) { 
	                System.out.println("Abnormal1"); 
	                e.printStackTrace(); 
	            } catch (AlreadyBoundException e) { 
	                System.out.println("Abnormal2"); 
	                e.printStackTrace(); 
	            } catch (MalformedURLException e) { 
	                System.out.println("Abnormal3"); 
	                e.printStackTrace(); 
	            }
	    			 
	    		}
	    	}
    
    }

	
	public static String put(String key, String value) {
		map.put(key, value);
		System.out.println("sucessful put key value");
		return "new key-value";
	}
	public String del(String key) {
		
		return (String) map.remove(key)+" removed";
	}
	public String get(String key) {
		
		return (String) "the value is "+map.get(key);
	}
	public String store() {
		Set set = map.keySet();
        	for(Iterator iter = set.iterator(); iter.hasNext();)
        	  {
        	   String key = (String)iter.next();
        	   String value = (String)map.get(key);
        	   System.out.println(key+"===="+value);
        	  }
		return "the map is printed";
	}
	public void exit() {
		
		flag = 1;
	}
}