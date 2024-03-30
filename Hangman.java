public class HangmanGame {
   private String givenWord;
   private StringBuilder currentGuess;
   private int attempts;
   
   public HangmanGame(String word) {
       this.givenWord = word.toLowerCase();
       this.currentGuess = new StringBuilder("_".repeat(word.length()));
       this.attempts = 5; // Set a maximum number of attempts
   }
   
   public String getCurrentGuess() {
       return currentGuess.toString();
   }
   
   public int getAttempts() {
       return attempts;
   }
   
   /**
    * Check how many letters were guessed correctly for the word.
    * @param letter
    * @return  true if the guess was correct, false otherwise
    */
   public boolean guessLetter(char letter) {
       boolean guessedCorrectly = false;
       for (int i = 0; i < givenWord.length(); i++) {
           if (givenWord.charAt(i) == letter) {
               currentGuess.setCharAt(i, letter);
               guessedCorrectly = true;
           }
       }
       if (!guessedCorrectly) {
          attempts--; // Decrease the number of attempts if the guess was incorrect
       }
       return guessedCorrectly;
   }
   
   public boolean isGameWon() {
       return currentGuess.indexOf("_") == -1;
   }
   
   //If the attemps are exhausted then the game is over
   public boolean isGameOver() {
       return attempts <= 0;
   }
}

