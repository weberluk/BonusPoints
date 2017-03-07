package TicTacToe;

import java.util.Arrays;

public class TicTacToe_Model {

	TicTacToe_Computer computer = new TicTacToe_Computer();

	enum Value {
		Cross, Point, Empty
	};

	enum HumanPlayer {
		Human, Computer
	};

	private Value[][] board = new Value[3][3];
	public final int DIMENSION = 3;
	private HumanPlayer player = HumanPlayer.Human;
	private Value sign = Value.Cross;

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
		if (player == HumanPlayer.Human) {
			if (board[i][j] == Value.Empty) {
				if (sign == Value.Cross) {
					board[i][j] = Value.Cross;
				} else {
					board[i][j] = Value.Point;
				}
			}
		} else {
			if (board[i][j] == Value.Empty) {
				if (player == HumanPlayer.Computer) {
					if (sign == Value.Cross) {
						computer.getOpenPositionInBoard(i, j, Value.Cross);
						board[i][j] = Value.Cross;
					} else {
						computer.getOpenPositionInBoard(i, j, Value.Point);
						board[i][j] = Value.Point;
					}
				}
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
				return true;
			}
		}
		return false;
	}

	public Value[][] getFreePositions() {
		Value[][] positions = new Value[3][3];

		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				if (board[i][j] == Value.Empty) {
					positions[i][j] = board[i][j];
				}

			}
		}
		return positions;
	}

	public Value[][] getSameSignPositions() {
		Value[][] positions = new Value[3][3];
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				if (getSign() == Value.Cross) {
					if (board[i][j] == Value.Cross) {
						positions[i][j] = board[i][j];
					} else {
						if (board[i][j] == Value.Point) {
							positions[i][j] = board[i][j];
						}
					}

				}

			}
		}
		return positions;
	}
}
