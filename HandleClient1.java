package FinalProject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public final class HandleClient1 implements Runnable{
   
   private static Socket socket;
   private static String msg;
   private static char[] wordList;
   static String word;
   private static String letter;
   

   public HandleClient1(Socket socket, String letter) throws Exception{ 
      this.socket=socket;
      this.letter=letter;
   }


   @Override
   public void run() {
      try {
    	  if(wordList == null) {
    		  initWord();
    	  }
    	  else {
    		  updateWord(letter);
    	  }
          
      } catch (Exception e) {
          System.out.println(e);
      }
  }

  private static void initWord() throws Exception {
        InputStream is = socket.getInputStream();
        DataOutputStream userOut = new DataOutputStream(socket.getOutputStream());
        //Setup input stream filters
        BufferedReader userIn = new BufferedReader(new InputStreamReader(is));
        userOut.writeUTF("Please enter word: ");
        word = userIn.readLine();
        
        int charCount = word.length();
	    wordList = new char[charCount];
	    
	    for(int i=0; i<charCount; i++) {
	    	wordList[i] = '-';
	    }

        System.out.println("close");
        userOut.close();
        userIn.close();
        socket.close();
        //return resp;
        
    }
  
  private static void updateWord(String letter) throws IOException {
	  InputStream is = socket.getInputStream();
      DataOutputStream userOut = new DataOutputStream(socket.getOutputStream());
      //Setup input stream filters
      BufferedReader userIn = new BufferedReader(new InputStreamReader(is));
      userOut.writeUTF("How many times does "+letter+" apear? ");
      int occurNum = Integer.parseInt(userIn.readLine());
      
      
      if(occurNum == 0) {
	    	System.out.println("Incorrect guess");
	    }
	    else { 
	    	for(int i=1;i<=occurNum;i++) {
	    		userOut.writeUTF("What is the index of appearance "+i);
	    		String nextIndex = userIn.readLine().trim().toLowerCase();
	    		int index = Integer.parseInt(nextIndex);
 	    		wordList[index] = letter.charAt(0);
	    	}
	    }
      

      System.out.println("close");
      userOut.close();
      userIn.close();
      socket.close();
  }
  
  public static char[] getWordList() {
	  return wordList;
	}
   
   private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception{
      // Construct a 1K buffer to hold bytes on their way to the socket.
      byte[] buffer = new byte[1024];
      int bytes = 0;
      // Copy requested file into the socket's output stream.
      while((bytes = fis.read(buffer)) != -1 ) {
      os.write(buffer, 0, bytes);
   }

}
 
}
