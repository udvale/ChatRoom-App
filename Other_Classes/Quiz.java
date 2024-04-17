package Other_Classes;

import java.util.HashMap;
import java.util.ArrayList;

public class Quiz {
	private HashMap scoreTable;//key is the user id and value is that user's current score
	private HashMap userNameTable;//key is the user id and value is that user's user name
	private ArrayList questions;
	private ArrayList answers;
	
	public Quiz() {
		scoreTable = new HashMap<Integer, Integer>();//key is the user id and value is that user's current score
		userNameTable = new HashMap<Integer, String>();//key is the user id and value is that user's user name
		questions = new ArrayList<String>();
		answers = new ArrayList<String>();
    }
	
	public void addUser(Player player) {
		Integer id = player.getID();
		scoreTable.put(id,0);
		userNameTable.put(id, player.getName());
	}
	
	public void addQuestion(String question, String answer) {
		questions.add(question);
		answers.add(answer);
	}
	
	public void incScore(Integer id) {
		Integer currentScore = (Integer) scoreTable.get(id);
		currentScore++;
		scoreTable.put(id, currentScore);
	}
}
