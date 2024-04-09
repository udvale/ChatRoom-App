package FinalProject;

import java.io.* ;
import java.net.* ;
import java.util.* ;

public final class HangmanServer
{
	private static char[] wordList;
	private static String letter;
	 public static void main(String argv[]) throws Exception{
		 System.out.println("Runing");
		 // Set the port number.
		 int port = 6789;
		
		// Establish the listen socket.
		 ServerSocket socket = new ServerSocket(port);
		 Boolean firstClient=true;
		
		while (true) {
		 // Listen for a TCP connection request.
			Socket connection = socket.accept();
			if(firstClient) {
				if(wordList == null) {
					// Construct an object to process the HTTP request message.
					HandleClient1 request = new HandleClient1(connection,null);
					wordList = request.getWordList();
					// Create a new thread to process the request.
					Thread thread = new Thread(request);
					// Start the thread.
					thread.start();
				}
				else {
					// Construct an object to process the HTTP request message.
					HandleClient1 request = new HandleClient1(connection,letter);
					// Create a new thread to process the request.
					Thread thread = new Thread(request);
					// Start the thread.
					thread.start();
				}
				
					firstClient = false;
				
			}
			else {
				letter = "a";
				firstClient = true;
			}
			
			
	 }
		
	
}
	 
	 
	 }

