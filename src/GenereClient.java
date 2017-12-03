

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GenereClient {
	public static void main(String[] args) throws IOException {
		String data = null ;
        switch(args[3]) {
        case "put":data = (args[3]+" "+args[4]+" "+args[5]);break;
        case "del":data = (args[3]+" "+args[4]);break;
        case "get":data = (args[3]+" "+args[4]);break;
        default:data = (args[3]);break;
        }
        switch(args[0]) {
       case "uc":{
        		byte[] dataudp = data.getBytes();
        		InetAddress address = InetAddress.getByName(args[1]);
        		DatagramPacket packet = new DatagramPacket(dataudp, dataudp.length, address, Integer.parseInt(args[2]));
                //创建DatagramSocket，实现数据发送和接收
                DatagramSocket socket = new DatagramSocket();
                //向服务器端发送数据报
                socket.send(packet);
                
                //接收服务器响应数据
                byte[] data2 = new byte[1024];
                DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
                socket.receive(packet2);
                String info = new String(data2, 0, packet2.getLength());
                System.out.println("Client: "+info);
                socket.close();
        }
        case "tc":{
        		Socket socket = new Socket(args[1], Integer.parseInt(args[2]));
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write(data);
            pw.flush();//刷新缓存，向服务器端输出信息
            socket.shutdownOutput();//关闭输出流
            
            //获取输入流，接收服务器端响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
            String datain = null;
            while((datain=br.readLine())!= null){
                System.out.println("Service: "+datain);
            }

            socket.close();
        }
        case "rmic":{
        	try { 
                String IP_A = args[1];
                String port = args[2];
            		String address = "rmi://"+IP_A+":"+port+"/RHello";
                IHello rhello =(IHello) Naming.lookup(address); 
                
                String operate = args[3];
                switch(operate) {
                case "put":
                	System.out.println(rhello.put(args[4],args[5]));
            			break;
                case "get":
                	System.out.println(rhello.get(args[4]));
                		break;
                case "del":
                	System.out.println(rhello.del(args[4]));
            			break;
                case "store":
                	System.out.println(rhello.store());
            			break;
                }
            } catch (NotBoundException e) { 
                e.printStackTrace(); 
            } catch (MalformedURLException e) { 
                e.printStackTrace(); 
            } catch (RemoteException e) { 
                e.printStackTrace();   
            } 
        }
        
        }
        

	}
}
