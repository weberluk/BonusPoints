package TicTacToe;

import java.util.Arrays;

import javafx.beans.property.SimpleBooleanProperty;

public class TicTacToe_Model {

	private TicTacToe_Computer computer;
	private TicTacToe_MiniMax ticTacToe_MiniMax;

	public void setComputer(TicTacToe_Computer computer) {
		this.computer = computer;
	}
	public void setMiniMax(TicTacToe_MiniMax ticTacToe_MiniMax){
		this.ticTacToe_MiniMax = ticTacToe_MiniMax;
	}

	// SimpleBooleanProperty for overwatching the model
	private SimpleBooleanProperty value = new SimpleBooleanProperty();

	enum Value {
		Cross, Point, Empty, Danger
	};

	enum HumanPlayer {
		Human, Computer
	};

	private Value[][] board = new Value[3][3];
	public static final int DIMENSION = 3;
	private HumanPlayer player = HumanPlayer.Human;
	private Value sign = Value.Cross;

	// the acutely Position
	private int xPos = 0;
	private int yPos = 0;
	
	// the index for MiniMax
	private int index;
	
	// the score that return from the miniMax
	private int score;
	
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score = score;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
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

	// set the board empty
	public void setAllEmpty(int i, int j) {
		board[i][j] = Value.Empty;
		TicTacToe_MiniMax ticTacToe_MiniMax = new TicTacToe_MiniMax(this);
	}

	public void setSign(Value sign) {
		this.sign = sign;
	}

	public Value getSign() {
		return this.sign;
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

	// check the board for a winner after button-click
	public boolean checkWinner(int i, int j, Value v) {
		Boolean[] winner = new Boolean[DIMENSION];

		// check for winner in horizontal
		Arrays.fill(winner, false);
		for (int k = 0; k < DIMENSION; k++) {
			if (board[i][k] == v) {
				winner[k] = true;
			}
			if (!Arrays.toString(winner).contains("f")) {
				this.setScore(20);
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
				this.setScore(20);
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
					this.setScore(20);
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
					this.setScore(20);
				return true;
			}
		}
		return false;
	}

}
