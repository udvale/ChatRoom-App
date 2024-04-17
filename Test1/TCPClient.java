package Test1;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private static Character guess;
    private static int state;
    private static int blank;

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 6789;
        Socket clientSocket = new Socket(host, port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        Scanner in = new Scanner(System.in);
        boolean cont = true;

        while (cont) {
            System.out.println("Let's play HANGMAN\n\n");
            System.out.println("Please enter your name:");
            String name = in.nextLine();
            // Send the player's name to the server
            outToServer.writeBytes("n " + name + "\r\n");

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String word = processRequest(inFromServer);
            // Initialize variables for the game
            ArrayList<Character> used = new ArrayList<>();
            blank = word.length();
            state = 1;
            char[] wordChar = new char[blank];
            hide(wordChar); // hide the word

            // Start the game loop
            while (blank != 0 && state != 7) {
                display(wordChar); // display the current state of the hidden word

                if (state > 1) {
                    System.out.println("It's your turn to guess. Enter a letter:");
                    guess = in.next().charAt(0);
                    while (!Character.isLetter(guess)) { // Validate input
                        System.out.println("Please input a letter!");
                        guess = in.next().charAt(0);
                    }
                    guess = Character.toLowerCase(guess);
                    outToServer.writeBytes(guess + "\r\n");
                }
                word = processRequest(inFromServer);
                if (state > 1) {
                    reveal(wordChar, guess, word);
                }
                hangman(state);
                System.out.println("Letters used: " + used); // Show used letters
                System.out.println("\n");
            }
            if (state == 7) {
                System.out.println("Game over. The word was: " + word);
            } else {
                System.out.println("Congratulations! You guessed the word: " + word);
            }
            System.out.println("Continue? (Y/N)");
            String response = in.next().trim();
            cont = response.equalsIgnoreCase("y");
        }
        
        in.close();
        outToServer.close();
        clientSocket.close();
        System.out.println("Thank you for playing.");
    }

    public static String processRequest(BufferedReader inFromServer) throws IOException {
        return inFromServer.readLine();
    }

    public static void hide(char word[]) {
        for (int i = 0; i < word.length; i++) {
            word[i] = '_';
        }
    }

    public static void display(char word[]) {
        for (char c : word) {
            System.out.print(c);
        }
        System.out.println();
    }

    public static void reveal(char word[], Character guess, String cWord) {
        if (guess == null) {
            System.err.println("Guess cannot be null.");
            return;
        }

        int flag = 0;
        for (int i = 0; i < word.length; i++) {
            if (word[i] == '_') {
                if (guess.equals(cWord.charAt(i))) {
                    word[i] = guess;
                    blank--;
                    flag++;
                }
            }
        }
        if (flag == 0) {
            state++;
        }
    }

    public static void hangman(int state) {
      if (state >= 1 && state <= 6) {
          System.out.println("You made " + state + " mistake" + (state > 1 ? "s" : ""));
      } else if (state == 7) {
          System.out.println("Game over. You made too many mistakes.");
      }
  }
  
}
