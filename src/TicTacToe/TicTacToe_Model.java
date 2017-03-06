package TicTacToe;

import java.util.Arrays;

public class TicTacToe_Model {

	enum Value {
		Cross, Point, Empty
	};

	private Value[][] board = new Value[3][3];
	private final int DIMENSION = 3;
	private boolean user = false;

	public void setUser(boolean user) {
		this.user = user;
	}

	public boolean getUser() {
		return this.user;
	}

	// set the board empty
	public void setAllEmpty(int i, int j) {
		board[i][j] = Value.Empty;
	}

	// sets cross or Point in the right place
	public void setBoard(int i, int j) {
		if (board[i][j] == Value.Empty) {
			if (user == false) {
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
			if(!Arrays.toString(winner).contains("f")){
				return true;
			}
		}

		// check for winner in vertical
		Arrays.fill(winner, false);
		for (int k = 0; k < DIMENSION; k++) {
			if (board[k][j] == v) {
				winner[k] = true;
			}
			if(!Arrays.toString(winner).contains("f")){
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
				if(!Arrays.toString(winner).contains("f")){
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
				if(!Arrays.toString(winner).contains("f")){
					return true;
				}
			}
		return false;
	}

}
