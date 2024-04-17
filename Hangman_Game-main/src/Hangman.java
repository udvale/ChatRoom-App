
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    String word;
    String hiddenWord;
    int count;
    boolean win;
    int score;
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    String gameStatus;

    /**
     * A constructor to initiate a new hangman game object, and
     * also starts a new game.
     * @param clientSocket
     * @param in
     * @param out
     * @throws IOException 
     */
    public Hangman(Socket clientSocket, BufferedReader in, PrintWriter out, String word) throws IOException {
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
	    this.word=word;
        startGame();
    }

    /**
     * This method starts the game and keeps the client playing
     * until either the game finishes or if they type "exit"
     * @throws IOException 
     */
    public void startGame() throws IOException {
        out.println("Welcome to the game");
        hiddenWord = new String(new char[word.length()]).replace("\0", "_");
        win = false;
        count = 0;
        while (count < hiddenWord.length() && hiddenWord.contains("_")) {
            out.println("Guess any letter in the word");
            out.println("With Remaning trials : ");
            out.println((hiddenWord.length() - count));
            out.println(hiddenWord);
            String guess = in.readLine();
            if (guess.equalsIgnoreCase("exit")) {
                System.out.println("Client " + clientSocket.getPort() + " has exitted the server");
                clientSocket.close();
                break;
            }
            /**
             * a method call to check if the word entered matches the hidden word
             */
            hang(guess.toLowerCase());
        }
        out.println("exit");
        if (win) {
            addScore();
            setString("Correct! You win! The word was " + word + "\nYour score is: ");
        } else {
            decreaseScore();
            setString("Sorry! You lose! The word was " + word + "\nYour score is: ");
        }

    }


    /**
     * Adjusts the score in case the player won
     */
    public void addScore() {
        score++;
    }

    /**
     * Adjusts the score in case the player lost
     */
    public void decreaseScore() {
        score--;
    }

    /**
     * If the user won, the game status will be changed to "won"
     * If the user lost, the game status will be changed to "lost"
     * @param string 
     */
    public void setString(String string) {
        this.gameStatus = string;
    }

    /**
     * This gets the game status.
     * @return game status
     */
    public String getString() {
        return gameStatus;
    }

    /**
     * This will return the score
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * This method will check the correctness of the guesses
     * and updates the new hidden word accordingly
     * @param guess
     * @throws IOException 
     */
    public void hang(String guess) throws IOException {
        String newasterisk = "";
        if (guess.equals(word)) {
            hiddenWord = word;
            win = true;
        } else {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess.charAt(0)) {
                    newasterisk += guess.charAt(0);
                } else if (hiddenWord.charAt(i) != '_') {
                    newasterisk += word.charAt(i);
                } else {
                    newasterisk += "_";
                }
            }
            if (hiddenWord.equals(newasterisk)) {
                count++;
            } else {
                hiddenWord = newasterisk;
            }
            if (hiddenWord.equals(word)) {
                win = true;
            }
        }
    }

}
