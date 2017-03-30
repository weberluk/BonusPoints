package TicTacToe;

import java.util.Arrays;

import TicTacToe_Server.TicTacToe_H2;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class TicTacToe_Model {
	
	private static TicTacToe_Model model;
	
	/**
	 * Factory method for returning the singleton board
	 * @param mainClass
	 *            The main class of this program
	 * @return The singleton resource locator
	 */
	public static TicTacToe_Model getModel() {
		if (model == null)
			model = new TicTacToe_Model();
		return model;
	}
	
	enum Value {
		Cross, Point, Empty, Danger
	};

	enum HumanPlayer {
		Human, Computer
	};
	
	//Actual name - default = default
	private String name = "default";
	
	//Actual id - default = 741 (watch calculate-Methode in Controller
	private int Id = 741;
	
	// the index for MiniMax
	private int index;
	
	// the score that return
	private int score;
	
	// the acutely Position
	private int xPos = 0;
	private int yPos = 0;

	private TicTacToe_Computer computer;
	private TicTacToe_MiniMax ticTacToe_MiniMax;

	// To set the computer show run()
	public void setComputer(TicTacToe_Computer computer) {
		this.computer = computer;
	}
	
	// To set the MiniMax show run()
	public void setMiniMax(TicTacToe_MiniMax ticTacToe_MiniMax){
		this.ticTacToe_MiniMax = ticTacToe_MiniMax;
	}

	// SimpleBooleanProperty for overwatching the model
	private SimpleBooleanProperty value = new SimpleBooleanProperty();

	public static final int DIMENSION = 3;
	private Value[][] board = new Value[DIMENSION][DIMENSION];
	private HumanPlayer player = HumanPlayer.Human;
	private Value sign = Value.Cross;

	// set the board empty
	public void setAllEmpty(int i, int j) {
		board[i][j] = Value.Empty;
		// open a new MiniMax
		TicTacToe_MiniMax ticTacToe_MiniMax = new TicTacToe_MiniMax(this);
	}


	// sets cross or Point in the right place and show is it Human or Computer
	public void setBoard(int i, int j) {
		if (board[i][j] == Value.Empty) {
			if (sign == Value.Cross) {
				board[i][j] = Value.Cross;
			} else {
				board[i][j] = Value.Point;
			}

		} 
	}

	// check the board for a winner after every turn - watch workflow in controller 
	// for win gives 20 Points Score for the winner
	public boolean checkWinner(int i, int j, Value v) {
		int winnerPoint = 20;
		Boolean[] winner = new Boolean[DIMENSION];

		// check for winner in horizontal
		Arrays.fill(winner, false);
		for (int k = 0; k < DIMENSION; k++) {
			if (board[i][k] == v) {
				winner[k] = true;
			}
			if (!Arrays.toString(winner).contains("f")) {
				this.setScore(winnerPoint);
				return true;
			}
		}

		// check for winner in vertical
		Arrays.fill(winner, false);
		for (int k = 0; k < DIMENSION; k++) {
			if (board[k][j] == v) {
				winner[k] = true;
			}
			if (!Arrays.toString(winner).contains("f")) {
				this.setScore(winnerPoint);
				return true;
				
			}
		}

		// check for winner in diagonal
		Arrays.fill(winner, false);
		if (i == j) {
			for (int k = 0; k < DIMENSION; k++) {
				if (board[k][k] == v) {
					winner[k] = true;
				}
				if (!Arrays.toString(winner).contains("f")) {
					this.setScore(winnerPoint);
					return true;
				}
			}
		}
		// check for anti-diagonal
		Arrays.fill(winner, false);
		for (int k = 0; k < DIMENSION; k++) {
			if (board[k][((DIMENSION - 1) - k)] == v) {
				winner[k] = true;
			}
			if (!Arrays.toString(winner).contains("f")) {
				if(this.getPlayer() == HumanPlayer.Human)
					this.setScore(winnerPoint);
				return true;
			}
		}
		return false;
	}
	
	// The given Name gives a new Id for the DB
	public int generateId(String name) {
		char[] charArray;
		int i = 0;

		charArray = name.toCharArray();

		for (char a : charArray) {
			i += (int) a;
		}
		return i;
	}
	
	// Getter and Setter for the values in the model
	
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score = score;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getId(){
		return this.Id;
	}
	
	public void setId(int id){
		this.Id = id;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void setIndexForMiniMax(int i, int j){
		if(i == 0 && j == 0){
			index = 0;
		}
		if(i == 0 && j == 1){
			index = 1;
		}
		if(i == 0 && j == 2){
			index = 2;
		}
		if(i == 1 && j == 0){
			index = 3;
		}
		if(i == 1 && j == 1){
			index = 4;
		}
		if(i == 1 && j == 2){
			index = 5;
		}
		if(i == 2 && j == 0){
			index = 6;
		}
		if(i == 2 && j == 1){
			index = 7;
		}
		if(i == 2 && j == 2){
			index = 8;
		}
		
	}

	public int getXPos() {
		score = ticTacToe_MiniMax.makeMove(index);
		return this.xPos;
	}

	public int getYPos() {
		return this.yPos;
	}

	// setter for the SimpleBooleanProperty
	public void setValue(Boolean newValue) {
		try{
			value.setValue(newValue);;
		} catch (Exception e){
			e.printStackTrace();
		}

	}

	// getter for the SimpleBooleanProperty
	public boolean getValue() {
		return value.get();
	}

	public SimpleBooleanProperty getValueProperty() {
		return value;
	}

	public HumanPlayer getPlayer() {
		return this.player;
	}

	public void setPlayer(HumanPlayer player) {
		this.player = player;
	}

	// get position in board
	public Value getBoardPosition(int i, int j) {
		return board[i][j];
	}
	
	public void setSign(Value sign) {
		this.sign = sign;
	}

	public Value getSign() {
		return this.sign;
	}
	
}
