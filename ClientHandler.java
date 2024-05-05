package Hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler allows the server to create threads. ClientHandler implements 
 * the Runnable interface.
 */
class ClientHandler implements Runnable {
   private final Socket client1Socket;//The Socket for the first player
   private final Socket client2Socket;//The Socket for the second player
   private final String player1Name;//The first player's name
   private final String player2Name;//The second player's name

   /**
    * ClientHandler initializes 
    * @param socket1
    * @param socket2
    * @param player1Name
    * @param player2Name
    */
   public ClientHandler(Socket socket1, Socket socket2, String player1Name, String player2Name) {
       this.client1Socket = socket1;
       this.client2Socket = socket2;
       this.player1Name = player1Name;
       this.player2Name = player2Name;
   }
   
//   public void verifyPlayer(Socket clientSocket, String userName) {
//	   try {
//		   PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//		   BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//		   
//		   out.println("Enter your password:");
//		   String password = in.readLine();
//		   System.out.println("userName: "+userName+" passowrd: "+password);
//	   } catch (IOException e){
//           e.printStackTrace();
//       }
//	}
//   
      @Override
   public void run() {
//       verifyPlayer(client1Socket, player1Name);
//       verifyPlayer(client2Socket, player2Name);
       Game game = new Game(client1Socket, client2Socket, player1Name, player2Name);
       game.run();
   }
}