import java.net.Socket;

class ClientHandler implements Runnable {
   private final Socket client1Socket;
   private final Socket client2Socket;
   private final String player1Name;
   private final String player2Name;

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