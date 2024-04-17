public class HangmanGame {
   private String givenWord;
   private StringBuilder hiddenWord;
   private int attempts;
   
   public HangmanGame(String word) {
       this.givenWord = word.toLowerCase();
       this.hiddenWord = new StringBuilder(word.replaceAll(".", "_"));
       this.attempts = 5; // Set a maximum number of attempts
   }
   
   public String getHiddenWord() {
       return hiddenWord.toString();
   }
   
   public int getAttempts() {
       return attempts;
   }

   public String getWord() {
    return givenWord;
}

   /**
    * Check how many letters were guessed correctly for the word.
    * @param letter
    * @return  true if the guess was correct, false otherwise
    */
   public boolean makeGuess(char guess) {
       boolean guessedCorrectly = false;
       guess = Character.toLowerCase(guess);
       for (int i = 0; i < givenWord.length(); i++) {
           if (givenWord.charAt(i) == guess) {
               hiddenWord.setCharAt(i, guess);
               guessedCorrectly = true;
           }
       }
       if (!guessedCorrectly) {
          attempts--; // Decrease the number of attempts if the guess was incorrect
          System.out.println("You have " + getAttempts() + " remaining.");
       }
       return guessedCorrectly;
   }
   
   public boolean isWordGuessed() {
    return !hiddenWord.toString().contains("_");
}
   
   //If the attemps are exhausted then the game is over
   public boolean isGameOver() {
       return attempts <= 0;
   }
}

