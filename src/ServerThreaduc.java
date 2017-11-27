import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerThreaduc implements Runnable{
    
    DatagramSocket socket = null;
    DatagramPacket packet = null;
    

    public ServerThreaduc(DatagramSocket socket,DatagramPacket packet) {
        this.socket = socket;
        this.packet = packet;
    }

    @Override
    public void run() {
        String info = null;
        InetAddress address = null;
        int port = 8800;
        byte[] data2 = null;
        DatagramPacket packet2 = null;
        GenerNode sv = new GenerNode();
        try {
            info = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Client send:"+info);
            String [] b = info.split(" ");
            String line = null;
            switch (b[0]) {
            case "put":
            		line = sv.put(b[1],b[2]) ;
            		break;
            case "del":
            		 line = sv.del(b[1]) ;
            		 break;
            case "get":
            		line = sv.get(b[1]) ; break;
            case "store":
            		line = sv.store() ; break;
            case "exit":
            		line = "the server is closed";
            		sv.exit() ;

            		break;
    		}
            
            address = packet.getAddress();
            port = packet.getPort();
            data2 = line.getBytes();
            packet2 = new DatagramPacket(data2, data2.length, address, port);
            socket.send(packet2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //socket.close();不能关闭         
    }

}