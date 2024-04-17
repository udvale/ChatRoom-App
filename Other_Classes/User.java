package Other_Classes;
public class User {
	private Integer userID;
	private String userName;
	
	public User(int initUserID, String initUserName) {
		userID = initUserID;
		userName = initUserName;
	}
	
	public Integer getID() {
		return userID;
	}
	
	public String getName() {
		return userName;
	}
}
