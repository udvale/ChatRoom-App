import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Main Game logic for  the client-server model Hangman game.
 * Created by Udval and Alexandria
 */
public class Game {
    Boolean isRunning = true;
    Socket player1;
    Socket player2;
    String player1Name;
    String player2Name;
    PrintWriter p1_out;
    PrintWriter p2_out;
    BufferedReader p1_in;
    BufferedReader p2_in;
    String secretPhrase;
    int gameLives = 2;
    int P1Losses = 0;
    int P2Losses = 0;

    public Game (Socket player1, Socket player2, String player1Name, String player2Name){
        this.player1 = player1;
        this.player2 = player2;
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        try {
            p1_out = new PrintWriter(player1.getOutputStream(), true);
            p2_out = new PrintWriter(player2.getOutputStream(), true);
            p1_in = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            p2_in = new BufferedReader(new InputStreamReader(player2.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        int lives = 6;
        int totalRounds = 3;
        int round = 1;
        char[] guesses = new char[27];
        int nextGuessIndex = 1;
        guesses[0] = ' '; // Initialize the guesses array with spaces so spaces automatically show up.
    
        p1_out.println("Welcome to Hangman, " + player1Name + "! You are Player 1!");
        p2_out.println("Welcome to Hangman, " + player2Name + "! You are Player 2!");
    
        while (P1Losses != gameLives && P2Losses != gameLives && round <= totalRounds) {
            boolean hintGiven = false;
            p1_out.println(" ");
            p2_out.println(" ");
            p1_out.println("You get to choose the phrase for this round");
            p2_out.println("Our opponent is choosing a phrase for you to guess. Next round you will get to choose a phrase!");
            p2_out.println("You are allowed to request 1 hint from your opponent by typing: hint");
            p1_out.println("Lives left: " + (gameLives - P1Losses));
            p2_out.println("Lives left: " + (gameLives - P2Losses));
            p1_out.println("SETPHRASE");
            p1_out.flush();
            p2_out.flush();
    
            try {
                secretPhrase = p1_in.readLine();
                System.out.println("Got the hidden word: " + secretPhrase);
            } catch (IOException e) {
                e.printStackTrace();
            }

            p2_out.println("You can start guessing now.");
            p2_out.println(display(guesses));
            p2_out.flush();
    
            while (!checkForWin(guesses) && lives != 0) {
                p2_out.println("GUESS");
                p2_out.println(display(guesses));
                p2_out.flush();
                try {
                    String letterGuess = p2_in.readLine();
                   // Check if the input is a hint request
                   if (letterGuess.equalsIgnoreCase("hint")) {
                    // Check if hint is already given
                    if (!hintGiven) {
                        // Request hint from the other player
                        p1_out.println("HINT");
                        p1_out.flush();
    
                        // Receive hint from the other player
                        String hint = p1_in.readLine();
                        p2_out.println("Hint: " + hint);
                        p2_out.flush();
                        hintGiven = true;
                    } else {
                        // If hint is already given
                        p2_out.println("Hint no longer available.");
                        p2_out.flush();
                    }
                    continue;
                }
                    // Check if the guess is only one character
                    if (letterGuess.length() != 1) {
                        p2_out.println("You can only input one letter for a guess.");
                        p2_out.flush();
                        continue;
                    }               
                    char guess = letterGuess.charAt(0);
                    System.out.println("Your opponent guessed the letter: " + guess);
    
                    if (!new String(guesses).contains(String.valueOf(guess))) {
                        // If the guess hasn't already been made
                        guesses[nextGuessIndex] = guess;
                        nextGuessIndex++;
                    } else {
                        while (new String(guesses).contains(String.valueOf(guess))) {
                            p2_out.println("GUESSAGAIN");
                            p2_out.flush();
                            guess = p2_in.readLine().charAt(0);
                        }
                        guesses[nextGuessIndex] = guess;
                        nextGuessIndex++;
                    }
    
                    if (secretPhrase.contains(String.valueOf(guess))) {
                        p2_out.println(">> The letter " + guess + " is in the hidden phrase.");
                        p2_out.println(display(guesses));
                        p1_out.println("Your opponent guessed the right letter: " + guess);
                        // p1_out.println(display(guesses));
                    } else {
                        p2_out.println(">> The letter " + guess + " is not in the hidden phrase.");
                        p1_out.println("Your opponent guessed the wrong letter: " + guess);
                        lives--;
                        p1_out.println(displayHangman(lives));
                        p2_out.println(displayHangman(lives));
                    }
    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    
            // Handle round conclusion
            if (checkForWin(guesses)) {
                p1_out.println("************************************************************");
                p2_out.println("************************************************************");
                p1_out.println("ROUND " + round + " has concluded!");
                p1_out.println("You lose this round. Your opponent has guessed the phrase");
                p2_out.println("ROUND " + round + " has concluded!");
                p2_out.println("Congratulations! You win this round");
                round++;
                P1Losses++;
            } else {
                p1_out.println("************************************************************");
                p2_out.println("************************************************************");
                p1_out.println("ROUND " + round + " has concluded!");
                p1_out.println("Congratulations! You win this round");
                p2_out.println("ROUND " + round + " has concluded!");
                p2_out.println("You lose this round! The phrase was: " + secretPhrase);
                P2Losses++;
                round++;
            }
    
            // Handle game over
            if (P1Losses == gameLives || P2Losses == gameLives) {
                p1_out.println(">>>");
                p2_out.println(">>>");
                String winner = P1Losses == gameLives ? player2Name : player1Name;
                String loser = P1Losses == gameLives ? player1Name : player2Name;
                playWinnerSound(winner);
                playLoserSound(loser);
                p1_out.println("Game over! " + loser + " you have lost the game.");
                p2_out.println("Congratulations " + winner + "! You have won the game!");
            } else {
                switchPlayers(player1, player2);
                lives = 6;
                guesses = new char[27];
                guesses[0] = ' ';
            }
        }
        p1_out.println("ENDGAME");
        p2_out.println("ENDGAME");
    }
    

    /**
    * Displays the current state of the secret phrase with hidden letters.
    * @param guesses The array of guessed letters.
    * @return The current state of the secret phrase with hidden letters.
    */
    public String display(char[] guesses){
        char[] secretPhraseHidden = secretPhrase.toCharArray();
        int i = 0;
        for(char c : secretPhraseHidden){
            //if the character isn't in the guesses list, convert it to an underline.  otherwise, leave it as is.
            if (!(new String(guesses).contains(String.valueOf(c)))){
                secretPhraseHidden[i] = '_';
            }
            i++;
        }
        return String.valueOf(secretPhraseHidden);
    }

    /**
    * Checks if the game has been won based on the guessed letters.
    * @param guesses The array of guessed letters.
    * @return True if the game has been won, false otherwise.
    */
    public Boolean checkForWin(char[] guesses){
        char[] secretPhraseHidden = secretPhrase.toCharArray();
        int i = 0;
        for(char c : secretPhraseHidden){
            //if the character isn't in the guesses list, the player hasn't won yet.  otherwise, they have.
            if (!(new String(guesses).contains(String.valueOf(c)))){
                return false;
            }
            i++;
        }
        return true;
    }

    /**
    * Displays the hangman ASCII art based on the remaining lives.
    * @param lives The remaining lives of the player.
    * @return The hangman ASCII art.
    */
    public String displayHangman(int lives) {
        String[] hangmen = new String[7];
        hangmen[0] = "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " / \\  |\n" +
                "      |\n";
        hangmen[1] = "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " /    |\n" +
                "      |";
        hangmen[2] = "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "      |\n" +
                "      |";
        hangmen[3] = "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|   |\n" +
                "      |\n" +
                "      |";
        hangmen[4] = "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                "  |   |\n" +
                "      |\n" +
                "      |";
        hangmen[5] = "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                "      |\n" +
                "      |\n" +
                "      |";
        hangmen[6] = "  +---+\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "      |";
        return hangmen[lives];
    }

    /**
     * Switches the players for the next round. This method swaps the game statistics and 
     * I/O streams between player 1 and player 2, enabling the current player to become 
     * the opponent in the next round.
     * @param player1 The socket of 1st player
     * @param player2 The socket of 2nd player 
     */
    public void switchPlayers(Socket player1, Socket player2) {
        int temp = P1Losses;
        P1Losses = P2Losses;
        P2Losses = temp;
        try {
            p1_out = new PrintWriter(player2.getOutputStream(), true);
            p2_out = new PrintWriter(player1.getOutputStream(), true);
            p1_in = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            p2_in = new BufferedReader(new InputStreamReader(player1.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Plays applause sound for the winner.
     *
     * @param winner The name of the winner.
     */
    public void playWinnerSound(String winner) {
        try {
            String soundFile = "sounds/applause.wav";
            playSound(soundFile);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Plays boo sound for the loser ( the person who lost :) ).
     *
     * @param loser The name of the loser.
     */
    public void playLoserSound(String loser) {
        try {
            String soundFile = "sounds/boo.wav";
            playSound(soundFile);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

  /**
     * Plays the sound file.
     * @param soundFile The path to the sound file.
     */
    private void playSound(String soundFile) throws Exception {
        File file = new File(soundFile);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        clip.start();
    }
}
