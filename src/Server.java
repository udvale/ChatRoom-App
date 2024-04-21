import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 6789;
    private static final Map<Socket, String> playerNames = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for clients to connect");

            while (true) {
                Socket client1 = serverSocket.accept();
                String player1Name = getPlayerName(client1);
                System.out.println("Player 1 has connected with the name: " + player1Name);

                Socket client2 = serverSocket.accept();
                String player2Name = getPlayerName(client2);
                System.out.println("Player 2 has connected with the name: " + player2Name);

                playerNames.put(client1, player1Name);
                playerNames.put(client2, player2Name);

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

    private static String getPlayerName(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String playerName = in.readLine();
        return playerName != null ? playerName : "Unknown Player";
    }
}
