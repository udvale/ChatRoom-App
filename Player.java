package FinalProject;

public class Player {
	private Integer userID;
	private String userName;
	
	public Player(int initUserID, String initUserName) {
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
