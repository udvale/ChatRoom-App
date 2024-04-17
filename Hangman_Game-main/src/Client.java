
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket;
        PrintWriter out;
        BufferedReader in;
        try {
            Scanner scn = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            clientSocket = new Socket(ip, 6789);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            //welcome to the server
            System.out.println(in.readLine());
            while (true) {
                //enter anything to start a new hangman game
                System.out.println(in.readLine());
                String choice = scn.next();
                //sending client's choice to the server
                if (choice.toLowerCase().equals("exit")) {
                    out.println(choice);
                    System.out.println("Exitting the server...  Good bye");
                    break;
                }
                out.println(choice); 
                System.out.println(in.readLine());//welcome hangman

                while (true) {
                    String ex = in.readLine();
                    if (ex.equals("exit")) {
                        break;
                    }
                    System.out.println(ex + "\n" + in.readLine() + in.readLine() + "\n" + in.readLine() + "\n");//Guess any ...
                    String letter = scn.next(); // send letter
                    if (letter.equals("exit")) {
                        System.out.println("Exitting the server...  Good bye");
                    }
                    out.println(letter);
                }
                System.out.println(in.readLine());//win or lose ...
                System.out.println(in.readLine());//score ...
            }

            scn.close();
        } catch (Exception e) {

        }
    }
}
