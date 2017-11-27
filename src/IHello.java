import java.rmi.Remote;
import java.rmi.RemoteException;

/** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2008-8-7 21:50:02 
* 定义一个远程接口，必须继承Remote接口，其中需要远程调用的方法必须抛出RemoteException异常 
*/ 
public interface IHello extends Remote { 
    public String put(String key, String value) throws RemoteException;
    public String get(String key) throws RemoteException;
    public String del(String key) throws RemoteException;
    public String store() throws RemoteException;
    public String exit() throws RemoteException;
}