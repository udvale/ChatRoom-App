package FinalProject;

import java.io.*;
import java.net.*;


class TCPHangmanServer{
    public static final int GAME_PORT = 6788;
    public static final int MAX_HINT = 3; // Maximum number of hints allowed per game
    public static void main(String argv[]) throws Exception {
    ServerSocket welcomeSocket = new ServerSocket(GAME_PORT);
    System.out.println("Waiting for incoming connection Request...");

        while(true) {
            Socket user1Socket = welcomeSocket.accept();
            System.out.println("User 1 has connected" + user1Socket.getInetAddress());
            Socket user2Socket = welcomeSocket.accept();
            System.out.println("User 2 has connected" + user2Socket.getInetAddress());

            //If both users (for now) are connected the the game begins
            startGame(user1Socket, user2Socket);
            welcomeSocket.close();
        }
    }

    
    private static void round(BufferedReader user1In, DataOutputStream user1Out, BufferedReader user2In, DataOutputStream user2Out, String theWord) throws IOException {
		int charCount = theWord.length();
	    boolean found = false;
	    char[] wordList = new char[charCount];
	    
	    for(int i=0; i<charCount; i++) {
	    	wordList[i] = '-';
	    }
	    String word = new String(wordList);
        while(!found) {
        	
        	user2Out.writeUTF("The word is  "+word);
    		user2Out.writeUTF("Guess a letter: ");
    	    String letter = user2In.readLine().trim().toLowerCase();
    	    
    	    user1Out.writeUTF("How many times does "+letter+" apear? ");
    	    String number = user1In.readLine();
    	    int occurNum = Integer.parseInt(number);
    	    
    	    if(occurNum == 0) {
    	    	System.out.println("Incorrect guess");
    	    }
    	    else { 
    	    	for(int i=1;i<=occurNum;i++) {
    	    		user1Out.writeUTF("What is the index of appearance "+i);
    	    		String nextIndex = user1In.readLine().trim().toLowerCase();
    	    		int index = Integer.parseInt(nextIndex);
       	    		wordList[index] = letter.charAt(0);
    	    	}
    	    }
    	    word = new String(wordList);
    	    if(!word.contains("-")) {
    	    	found = true;
    	    }
        }
        
        
        user2Out.writeUTF("Congratulations! You guessed  "+word);   
    	    
    	    
    	    
        	
        
	}
    
    private static void  startGame(Socket user1Socket, Socket user2Socket) throws IOException{
        BufferedReader wordIn = new BufferedReader(new InputStreamReader(user1Socket.getInputStream()));
        DataOutputStream wordOut = new DataOutputStream(user1Socket.getOutputStream());

        BufferedReader wordGuessIn = new BufferedReader(new InputStreamReader(user2Socket.getInputStream()));
        DataOutputStream wordGuessOut = new DataOutputStream(user2Socket.getOutputStream());

        
        wordOut.writeUTF("Please enter the word to guess: ");
        String w = wordIn.readLine().trim().toLowerCase();
        
        round(wordIn,wordOut,wordGuessIn,wordGuessOut,w);

//        int  hintCount = 0;
//        boolean found = false;
//        Hangman hangman = new Hangman(w);
//        while (!hangman.isGameOver()){
//            wordGuessOut.writeUTF("Guess what word this is: "+ hangman.getHiddenWord());
//            wordGuessOut.writeUTF("Number of guesses you have left "+ hangman.getAttempts());
//            wordGuessOut.writeUTF("Enter a letter to guess or if you want hint type 'hint': ");
//            String guess = wordGuessIn.readLine().trim().toLowerCase();
//
//            if (guess.equals("hint") && hintCount < MAX_HINT){
//                hintCount++;
//                int hintIndex = (int) (Math.random() * w.length());
//                wordOut.writeUTF("Hint: The word has a letter '" + w.charAt(hintIndex) + "' at position " + (hintIndex + 1));
//            } 
//            else if (guess.length() == 1 && Character.isLetter(guess.charAt(0))){
//                    char  c = guess.charAt(0);
//                    found = hangman.makeGuess(c);
//                    if (!found){
//                        wordGuessOut.writeUTF("Incorrect guess");
//                    }
//            }else{
//                    wordGuessOut.writeUTF("Invalid guess. The letter" + guess + " is not valid input");
//            }
//    
//
//            if (hangman.isWordGuessed()){
//                wordGuessOut.writeUTF("Congratulations! You got it right!");
//            }else{
//                wordGuessOut.writeUTF("You are out of attempts. The word was "+ w);
//            }

            wordIn.close();
            wordOut.close();
            wordGuessIn.close();
            wordGuessOut.close();
            user1Socket.close();
            user2Socket.close();
            System.out.println("Disconnecting...");
        //}

    }
}
