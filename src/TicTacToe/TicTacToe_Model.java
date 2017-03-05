package TicTacToe;

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

	public void setBoard(int i, int j) {
		// sets cross or Point in the right place
		if (board[i][j] == Value.Empty) {
			if (user == false) {
				board[i][j] = Value.Cross;
			} else {
				board[i][j] = Value.Point;
			}
		}
	}

	public void checkWinner(int i, int j) {
		boolean winner = false;
				
		
		// check for winner in length
		for (int k = 0; k < DIMENSION; k++) {
			for (int l = 0; l < DIMENSION; l++) {
				if (board[k][l] == Value.Cross || board[k][l] == Value.Point) {
					winner = true;
				} else{
					winner = false;
				}
			}
		}

	}

}
