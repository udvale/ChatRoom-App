
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("Server has started...");
        
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client " + clientSocket.getPort() + " has joined the server.");
                Thread thread = new ClientHandler(clientSocket);
                thread.start();
            } catch (Exception e) {
            }
        }
    }
}
