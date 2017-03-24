package TicTacToe;

import java.util.Arrays;

import javafx.beans.property.SimpleBooleanProperty;

public class TicTacToe_Model {

	private TicTacToe_Computer computer;

	public void setComputer(TicTacToe_Computer computer) {
		this.computer = computer;
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

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	public int getXPos() {
		computer.checkComputerPlay();
		return this.xPos;
	}

	public int getYPos() {
		//TODO Achtung Methode tauscchen
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
				return true;
			}
		}
		return false;
	}

}
