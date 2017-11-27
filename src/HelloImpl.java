import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2008-8-7 21:56:47 
* 远程的接口的实现 
*/ 
public class HelloImpl extends UnicastRemoteObject implements IHello { 
    public HelloImpl() throws RemoteException { 
    }   
    HashMap map = new HashMap();	
    public String put(String key, String value) throws RemoteException {   		
		map.put(key,value);	
    return "Key stored"; 
    }
    public String get(String key) throws RemoteException { 	
    	String value = (String) map.get(key);
    return value; 
} 
    public String del(String key) throws RemoteException { 	
    	String value = (String) map.remove(key);
    return (key+"and"+value+" deleted"); 
} 
    public String store() throws RemoteException { 	
		Set set = map.keySet();
		for(Iterator iter = set.iterator(); iter.hasNext();)
    	{
    	  	   String key_ = (String)iter.next();
    	  	   String value_ = (String)map.get(key_);
    	  	   System.out.println(key_+"===="+value_);
    	  	  }
    return "map are printed"; 
} 
    public String exit() throws RemoteException { 	
    	int a = 1; int b = 0;
    	System.out.println(a/b);
    return ("System exited"); 
}
}
