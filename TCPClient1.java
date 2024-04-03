import java.io.*;
import java.net.*;
public class TCPClient1 {
   public static void main(String[] args) throws Exception, IOException{
      final int port = 6789;
      final String serverAddress = "192.168.1.183"; //example for now
      String resp;
      // String modifiedSentence;
      
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      Socket clientSocket = new Socket(serverAddress, port);
      BufferedReader inFromServer =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

      while ((resp = inFromServer.readLine()) != null){
         // will handle for hint soon.
         if (resp.startsWith("Please enter the word")) {
            String userInputString = inFromUser.readLine().trim().toLowerCase();
            outToServer.writeUTF(userInputString);
        } else {
            System.out.println("Received unexpected response from server: " + resp);
        }
      }
      clientSocket.close();
   }
}
