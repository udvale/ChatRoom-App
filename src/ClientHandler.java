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
   
      @Override
   public void run() {
       Game game = new Game(client1Socket, client2Socket, player1Name, player2Name);
       game.run();
   }
}