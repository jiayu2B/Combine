
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2008-8-7 22:21:07 
* 客户端测试，在客户端调用远程对象上的远程方法，并返回结果。 
*/ 
public class Client { 
    public static void main(String args[]){ 
        try { 
            String IP_A = args[1];
            String port = args[2];
        		String address = "rmi://"+IP_A+":"+port+"/RHello";
            IHello rhello =(IHello) Naming.lookup(address); 
            
            String operate = args[3];
            switch(operate) {
//            case "create":
//            	System.out.println(rhello.create());
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