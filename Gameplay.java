package FinalProject;

import java.util.Scanner;

public class Gameplay {
	public static void main(String argv[]) {
		   System.out.println("Please enter word: ");
		   Scanner scanner0 = new Scanner(System.in);  
		   String theWord = scanner0.nextLine(); 
		   
		   int charCount = theWord.length();
		    boolean found = false;
		    char[] wordList = new char[charCount];
		    
		    for(int i=0; i<charCount; i++) {
		    	wordList[i] = '-';
		    }
		    String word = new String(wordList);
	       while(!found) {
	       	
	    	System.out.println("The word is  "+word);
	    	System.out.println("Guess a letter: ");
	   	    //String letter = user2In.readLine().trim().toLowerCase();
	   	    Scanner scanner = new Scanner(System.in);  
		    String letter = scanner.nextLine(); 
	   	    
	   	    System.out.println("How many times does "+letter+" apear? ");
	   	    //String number = user1In.readLine();
	   	    Scanner scanner2 = new Scanner(System.in);  
		    String number = scanner2.nextLine(); 
	   	    int occurNum = Integer.parseInt(number);
	   	    
	   	    if(occurNum == 0) {
	   	    	System.out.println("Incorrect guess");
	   	    }
	   	    else { 
	   	    	for(int i=1;i<=occurNum;i++) {
	   	    		System.out.println("What is the index of appearance "+i);
	   	    		//String nextLetter = user1In.readLine().trim().toLowerCase();
	   	    		Scanner scanner3 = new Scanner(System.in);  
	   	    		String nextIndex = scanner.nextLine();
	   	    		int index = Integer.parseInt(nextIndex);
	   	    		wordList[index] = letter.charAt(0);
	   	    	}
	   	    }
	   	    word = new String(wordList);
	   	    if(!word.contains("-")) {
	   	    	found = true;
	   	    }
	       }
	       
	       
	       System.out.println("Congratulations! You guessed  "+word);
	   }
}
