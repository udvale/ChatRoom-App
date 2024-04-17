
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientHandler extends Thread implements Runnable{

    static Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    // public void run() {
    //     try {
    //         int score = 0;
    //         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    //         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    //         out.println("The Server has started");

    //         while (true) {
    //             out.println("Enter anything to start the game or 'Exit' to leave from the server");
    //             String choice = in.readLine();
    //             if (choice.toLowerCase().equals("exit")) {
    //                 //out.println("Exitting the server...  Good bye");
    //                 System.out.println("Client " + clientSocket.getPort() + " has exitted the server");
    //                 clientSocket.close();
    //                 break;
    //             }
    //             String word = getRandomWord().toLowerCase();
	// 	        System.out.println("The server Chose the word: "+ word);
    //             Hangman g = new Hangman(clientSocket, in, out,word);
    //             score += g.getScore();
    //             out.print(g.getString() + score + "\n");
    //         }
    //     } catch (Exception e) {

    //     }
    // }
    public void run() {
        try {
            int score = 0;
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("The Server has started");
            while (true) {
                out.println("Enter anything to start the game or 'Exit' to leave from the server");
                String choice = in.readLine();
                if (choice.toLowerCase().equals("exit")) {
                    //out.println("Exitting the server...  Good bye");
                    System.out.println("Client " + clientSocket.getPort() + " has exitted the server");
                    clientSocket.close();
                    break;
                }
                String word = getRandomWord().toLowerCase();
		        System.out.println("The server Chose the word: "+ word);
                Hangman g = new Hangman(clientSocket, in, out,word);
                score += g.getScore();
                // out.print(g.getString() + score + "\n");
                while (true) {
                    String serverMessage = in.readLine();
                    if (serverMessage.equals("YOUR_TURN")) {
                        out.println("It's your turn! Enter a single letter to guess:");
                        String guess;
                        do {
                            guess = in.readLine();
                        } while (guess.length() != 1); // Ensure the guess is a single letter
                        out.println(guess);
                    } else if (serverMessage.equals("WAIT_FOR_TURN")) {
                        System.out.println("Waiting for the other player to make a guess...");
                    } else if (serverMessage.startsWith("GAME_STATE")) {
                        System.out.println("Current game state: " + serverMessage.substring(10));
                    } else if (serverMessage.equals("YOU_WIN")) {
                        System.out.println("You win!");
                        break;
                    } else if (serverMessage.equals("YOU_LOSE")) {
                        System.out.println("You lose.");
                        break;
                    }
                }
                out.print(g.getString() + score + "\n");
            }
        } catch (Exception e) {

        }
    }

	/**
     * this method gets us a random word every time the game starts.
     * @return a random word from API
	 * @throws InterruptedException
     */
    public String getRandomWord() throws InterruptedException {
        String word = "";
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://random-word-api.herokuapp.com/word")) // Replace with your API endpoint
                        .header("Content-Type", "application/json") // Add necessary headers
                        .GET()
                        .build();
            // Send HttpRequest and handle response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                word = response.body().substring(2, response.body().length() - 2);
            } else {
                throw new IOException("Failed to fetch random word from API");
            }
        } catch (IOException e){
            System.out.println(e);
        }
        return word;
    }

}
