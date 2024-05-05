import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Server class manages connections between clients, handling communication and game sessions.
 * It listens for incoming connections on a specified port and creates game sessions for pairs of clients.
 */
public class Server {
    private static final int PORT = 6789;
    private static final Map<Socket, String> playerNames = new HashMap<>();
    private static  String player1Name = "";
    private static  String player2Name = "";

    public static void main(String[] args) throws InterruptedException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for clients to connect");

            while (true) {//waiting for clients to connect
                // Socket client1 = serverSocket.accept();//the first client to connect is assigned as client1
                // String player1Name = verifyPlayer(client1);
                // System.out.println("Player 1 has connected with the username: " + player1Name);

                // Socket client2 = serverSocket.accept();//the second client to connect is assigned as client2
                // String player2Name = verifyPlayer(client2);
                // System.out.println("Player 2 has connected with the username: " + player2Name);

                // playerNames.put(client1, player1Name);
                // playerNames.put(client2, player2Name);

                Socket client1 = serverSocket.accept();
                Socket client2 = serverSocket.accept();

                // Creating threads to handle the verification process for both clients
                Thread thread1 = new Thread(() -> {
                    player1Name = verifyPlayer(client1);
                    playerNames.put(client1, player1Name);
                    System.out.println("Player 1 has connected with the username: " + player1Name);
                });

                Thread thread2 = new Thread(() -> {
                    player2Name = verifyPlayer(client2);
                    playerNames.put(client2, player2Name);
                    System.out.println("Player 2 has connected with the username: " + player2Name);
                });

                thread1.start();
                thread2.start();

                // Wait for both threads to complete
                thread1.join();
                thread2.join();
                
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

    /**
     * Called by verifyPlayer when a client needs to create a new account.
     * New account information is stored in the accounts.txt file.
     * @param player the socket for the client that is creating an account
     * @return the username of the client that is creating an account
     */
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
        		  File accountsFile = new File("accounts.txt");
        	      Scanner reader = new Scanner(accountsFile);
        	      while (reader.hasNextLine()) {
        	        String account = reader.nextLine();
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
   	        	FileWriter file = new FileWriter("accounts.txt",true);
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
    
    /**
     * Is called by verifyPlayer when a l=player wants to login into an existing account.
     * @param player the Socket for the client that is logging in
     * @return the client's user name
     */
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
   		  File accountsFile = new File("accounts.txt");
   	      Scanner reader = new Scanner(accountsFile);
   	      while (reader.hasNextLine()) {
   	        String account = reader.nextLine();

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
    
    /**
     * This method let lets a player either log in to an existing account 
     * or create a new account
     * @param player
     * @return 
     */
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
}
