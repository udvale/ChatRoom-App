package Test1;
// public class ClientHandler extends Thread implements Runnable{
//    public static final int GAME_PORT = 6789;
//    public static final int MAX_HINT = 3; // Maximum number of hints allowed per game
//    static clientSocket = null;

//    ClientHandler(Socket clientSocketsocket) {
//       this.clientSocket = clientSocket;
//    }

//    public void run(){
//       startGame(null, null);
//    }

//    private static void  startGame(Socket user1Socket, Socket user2Socket) throws IOException{
//       System.out.println("Both Users have been connected");
//       System.out.println("The game has begun");
//       BufferedReader wordIn = new BufferedReader(new InputStreamReader(user1Socket.getInputStream()));
//       DataOutputStream wordOut = new DataOutputStream(user1Socket.getOutputStream());

//       BufferedReader wordGuessIn = new BufferedReader(new InputStreamReader(user2Socket.getInputStream()));
//       DataOutputStream wordGuessOut = new DataOutputStream(user2Socket.getOutputStream());

//       wordOut.writeUTF("Please enter the word to guess: ");
//       String w = wordIn.readLine().trim().toLowerCase();
//       System.out.println("The word");

//       int  hintCount = 0;
//       boolean found = false;
//       Hangman hangman = new Hangman(w);
//       while (!hangman.isGameOver()){
//           wordGuessOut.writeUTF("Guess what word this is: "+ hangman.getHiddenWord());
//           wordGuessOut.writeUTF("Number of guesses you have left "+ hangman.getAttempts());
//           wordGuessOut.writeUTF("Enter a letter to guess or if you want hint type 'hint': ");
//           String guess = wordGuessIn.readLine().trim().toLowerCase();

//           if (guess.equals("hint") && hintCount < MAX_HINT){
//               hintCount++;
//               int hintIndex = (int) (Math.random() * w.length());
//               wordOut.writeUTF("Hint: The word has a letter '" + w.charAt(hintIndex) + "' at position " + (hintIndex + 1));
//           } 
//           else if (guess.length() == 1 && Character.isLetter(guess.charAt(0))){
//                   char  c = guess.charAt(0);
//                   found = hangman.makeGuess(c);
//                   if (!found){
//                       wordGuessOut.writeUTF("Incorrect guess");
//                   }
//           }else{
//                   wordGuessOut.writeUTF("Invalid guess. The letter" + guess + " is not valid input");
//           }
  

//           if (hangman.isWordGuessed()){
//               wordGuessOut.writeUTF("Congratulations! You got it right!");
//           }else{
//               wordGuessOut.writeUTF("You are out of attempts. The word was "+ w);
//           }

//           wordIn.close();
//           wordOut.close();
//           wordGuessIn.close();
//           wordGuessOut.close();
//           user1Socket.close();
//           user2Socket.close();
//           System.out.println("Disconnecting...");
//       }

//   }
   
// }

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private List<Socket> clientSockets;

    public ClientHandler() {
        this.clientSockets = new ArrayList<>();
    }

    // Method to add a client socket to the list
    public synchronized void addClientSocket(Socket clientSocket) {
        clientSockets.add(clientSocket);
    }

    // Method to get all client sockets
    public synchronized List<Socket> getClientSockets() {
        return new ArrayList<>(clientSockets);
    }
}

