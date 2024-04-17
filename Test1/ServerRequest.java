package Test1;
// import java.net.ServerSocket;

// public class ServerRequest {
//    public static void main(String argv[]) throws IOException{
//       int port = 6789;
//       HashMap<String, Integer> score =  new HashMap<String, Integer>();
//       ServerSocket welcomeSocket = new ServerSocket(port);
//       System.out.println("Waiting for incoming connection Request...");

//       while (true){
//          Socket connection = welcomeSocket.accept();
//          ServerTCP request = new ServerTCP(socket, score);
//          Thread thread = new Thread(request);
//          thread.start();
//       }
//    }
   
// }

import java.net.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


public final class ServerRequest {
	public static HashMap<String, Integer> scoreB;
	public static HashMap<String, String> nameDB;

	public static void main(String[] args) throws Exception {

		scoreB = new HashMap<String, Integer>();
		nameDB = new HashMap<String, String>();
      ArrayList<Socket> clientSockets = new ArrayList<>();
		int port = 6789;

		ServerSocket socket = new ServerSocket(port);
		while (true) {
			Socket connection = socket.accept();
			ServerTCP request = new ServerTCP(connection, scoreB, nameDB, clientSockets);
         Thread thread = new Thread(request);
			thread.start();
		}
	}
}
