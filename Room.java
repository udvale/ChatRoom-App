import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;

public class Room {
	private static JFrame window;
	
	private static HashMap userNameTable;//key is the user id and value is that user's user name
	private static Integer nextId;
	
	public Room() {
		userNameTable = new HashMap<Integer, String>();//key is the user id and value is that user's user name
		nextId = 0;
    }
	
	public static User addUser(String userName) {
		User user = new User(nextId,userName);
		userNameTable.put(nextId, userName);
		nextId++;
		System.out.println("has entered the chat");
		return user;
	}
	
}
