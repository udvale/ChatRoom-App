package Hangman;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {
    private static final int PORT = 6789;
    private static final Map<Socket, String> playerNames = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for clients to connect");

            while (true) {//waiting for clients to connect
                Socket client1 = serverSocket.accept();//the first client to connect is assigned as client1
                String player1Name = verifyPlayer(client1);
                System.out.println("Player 1 has connected with the name: " + player1Name);
//                String player1Password = getPlayerPassword(client1);//new
//                System.out.println("Player 1 has connected with the password: " + player1Password);

                Socket client2 = serverSocket.accept();//the second client to connect is assigned as client2
                String player2Name = verifyPlayer(client2);
                System.out.println("Player 2 has connected with the name: " + player2Name);
//                String player2Password = getPlayerPassword(client2);//new
//                System.out.println("Player 1 has connected with the password: " + player2Password);

                playerNames.put(client1, player1Name);
                playerNames.put(client2, player2Name);
                
                //creates a thread to handle the clients
                ClientHandler clientHandler = new ClientHandler(client1, client2, player1Name, player2Name);
                Thread thread = new Thread(clientHandler);
                thread.start();
                System.out.println("Started a new game");
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

//    private static String getPlayerName(Socket clientSocket) throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        String playerName = in.readLine();
//        return playerName != null ? playerName : "Unknown Player";
//    }
//    
//    private static String getPlayerPassword(Socket clientSocket) throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        String playerPassword = in.readLine();
//        return playerPassword != null ? playerPassword : "Unknown Player";
//    }
//    
//    

    public static String createAccount(Socket player) {
    	try {
    		   PrintWriter out = new PrintWriter(player.getOutputStream(), true);
    		   BufferedReader in = new BufferedReader(new InputStreamReader(player.getInputStream()));
    		   Boolean check = true;
    		   Boolean newName = true;
    		   String username = null;
    		   String password = null;
    		   while(check) {
    			   out.println("USERNAME");
        		   out.flush();
        		   username = in.readLine();
        		   out.println("PASSWORD");
        		   out.flush();
        		   password = in.readLine();
        		   
        		  //Check if the user name is unique
        		  File accountsFile = new File("filename.txt");
        	      Scanner reader = new Scanner(accountsFile);
        	      while (reader.hasNextLine()) {
        	        String account = reader.nextLine();
        	        System.out.println(account);
        	        if(account.contains(username+",")) {
        	        	newName = false;
        	        	
        	        }
        	        else {
        	        	check = false;
        	        }
        	        
    		   }
        	      reader.close();
    		   
    	      }
    		   if(newName) {
   	        	//Adding the new account to the accounts file
   	        	FileWriter file = new FileWriter("filename.txt",true);
   	            file.write(username+","+password+"\n");
   	            file.close();
   	        	return username;
   	        }else{
    		   		out.println("Please try again with a new username.");
    		   		createAccount(player);
    		   	  }   
    	      
    	   } catch (IOException e){
               e.printStackTrace();
           }
    	   return "Login failed";
    	
    }
    
    public static String verifyAccount(Socket player) {
    	String returnName ="Login failed";
    	Boolean verified = false;
    	try {
   		   PrintWriter out = new PrintWriter(player.getOutputStream(), true);
   		   BufferedReader in = new BufferedReader(new InputStreamReader(player.getInputStream()));
   		   
   		   out.println("USERNAME");
		   out.flush();
		   String userName = in.readLine();
		   out.println("PASSWORD");
   		   out.flush();
   		   String password = in.readLine();
   		   System.out.println("userName: "+userName+" password: "+password);
   		   
   		   //Check if the account exists
   		  File accountsFile = new File("filename.txt");
   	      Scanner reader = new Scanner(accountsFile);
   	      while (reader.hasNextLine()) {
   	        String account = reader.nextLine();
//   	        System.out.println("check for: "+account);
//   	        System.out.println("check for: "+userName+","+password);
   	        if(account.contains(userName+","+password)) {
   	        	System.out.println("entered");
   	        	returnName = userName;
   	        	verified = true;
   	        }
   	       
   	        	
   	        
   	      }
   	      reader.close();
   	      if(!verified) {
   	    	out.println("This is not a valid account. Please try again.");
   	      	returnName = verifyPlayer(player);
   	      }
   	    
   	   } catch (IOException e){
              e.printStackTrace();
          }
   	   return returnName;
    }
    
    public static String verifyPlayer(Socket player) {
    	String username = "Login failed";
    	try {
	    	PrintWriter out = new PrintWriter(player.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(player.getInputStream()));
	    	out.println("Welcome to Hangman!");
	    	out.println("SIGNIN");
	    	out.flush();
			String answer = in.readLine();
			
			if(answer.equals("yes")) {
				username = verifyAccount(player);
			} else if(answer.equals("no")) {
				username = createAccount(player);
			} else {
				username = verifyPlayer(player);
			}
			
			
    	} catch (IOException e){
            e.printStackTrace();
        }
    	
    	
 	   return username;
    	
  	}
     
//    public static void main(String[] args) {
//        try {
//          File myObj = new File("filename.txt");
//          if (myObj.createNewFile()) {
//            System.out.println("File created: " + myObj.getName());
//          } else {
//            System.out.println("File already exists.");
//          }
//        } catch (IOException e) {
//          System.out.println("An error occurred.");
//          e.printStackTrace();
//        }
//      }
}
