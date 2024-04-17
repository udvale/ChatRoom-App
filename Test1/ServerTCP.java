package Test1;
// import java.io.*;
// import java.net.*;
// import java.util.*;

// public class ServerTCP implements Runnable {
//     Socket socket;
//     HashMap<String, Integer> score;
//     Random randWord;

//     public ServerTCP(Socket socket, HashMap<String, Integer> score) throws IOException {
//         this.socket = socket;
//         this.score = score;
//         this.randWord = new Random();
//     }

//     public void run() {
//         try {
//             gameStart();
//         } catch (Exception e) {
//             System.out.println(e);
//         }
//     }

//     private void gameStart() throws Exception {
//         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//         DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

//         outToClient.writeBytes("Please enter the word for the game:");
//         String word = inFromClient.readLine().trim().toLowerCase();
//         Hangman game = new Hangman(word);

//         while (!game.isGameOver()) {
//             outToClient.writeBytes(game.getHiddenWord() + " " + game.getAttempts() + "\r\n");
//             char guess = inFromClient.readLine().charAt(0);
//             game.makeGuess(guess);
//         }

//         if (game.isWordGuessed()) {
//             outToClient.writeBytes("You guessed the word! The word was: " + game.getWord() + "\r\n");
//         } else {
//             outToClient.writeBytes("Game over! The word was: " + game.getWord() + "\r\n");
//         }

//         inFromClient.close();
//         outToClient.close();
//         socket.close();
//     }
// }
import java.io.*;
import java.net.*;
import java.util.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServerTCP implements Runnable {
    final static String CRLF = "\r\n";
    Socket socket;
    static final boolean verbose = true;
    HashMap<String, Integer> scoreB;
    HashMap<String, String> nameDB;
    Random rand;
    List<Socket> clientSockets;

    public ServerTCP(Socket socket, HashMap<String, Integer> scoreB2, HashMap<String, String> nameDB2,
                     List<Socket> clientSockets) throws Exception {
        this.socket = socket;
        this.scoreB = scoreB2;
        this.nameDB = nameDB2;
        this.clientSockets = clientSockets;
        rand = new Random();
    }

    public void run() {
        try {
            gameStart();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void gameStart() throws Exception {
        System.out.println("Processing request");
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String requestLine;

        while ((requestLine = br.readLine()) != null) {
            System.out.println(requestLine);
            String[] tokens = requestLine.split(" ");
            if (requestLine.startsWith("n")) {
                String playerName = tokens[1];
                nameDB.put(socket.getInetAddress().toString(), playerName);
                sendToClient(socket, "You have joined the game as: " + playerName);
                clientSockets.add(socket);
            }

            if (nameDB.size() == 2) {
                Thread.sleep(10000);
                String word = selectWord(rand);
                String initialMessage = "Game started! The word to guess: " + word;
                sendToAllClients(initialMessage);
            }
        }
    }

    private void sendToClient(Socket clientSocket, String message) throws IOException {
        DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
        os.writeBytes(message + CRLF);
        os.flush();
    }

    private void sendToAllClients(String message) {
        for (Socket clientSocket : clientSockets) {
            try {
                sendToClient(clientSocket, message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String selectWord(Random rand) throws FileNotFoundException {
        File file = new File("words.txt");
        Scanner reader = new Scanner(file);
        int numWord = reader.nextInt();
        int pickNum = rand.nextInt(numWord) + 1;
        for (int i = 0; i < pickNum; i++) {
            reader.nextLine();
        }
        String word = reader.nextLine();
        reader.close();
        return word;
    }
}

