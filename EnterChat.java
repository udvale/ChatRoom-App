import java.util.Scanner;

public class EnterChat {
	public static void main(String argv[]) {
		Room room = new Room();
		Scanner input = new Scanner(System.in);  
		System.out.println("Please enter your user name: ");
	    String userName = input.nextLine();  
	    room.addUser(userName);
	}
}
