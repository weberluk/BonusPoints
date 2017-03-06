package TicTacToe;

import java.util.Arrays;

import TicTacToe.TicTacToe_Model.Value;

public class TicTacToe_Computer {

	private TicTacToe_Model model;
	private int xPos = 0;
	private int yPos = 0;

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setStaticPosition() {
		xPos = 1;
		yPos = 1;
	}

	/**
	 * Check all the column and line for the possibilities
	 */
	public void getOpenPositionInBoard(int i, int j, Value v) {
		int emptyLeft = 0, emptyRight = 0, emptyDiagonal = 0, emtpyAntiDiagonal = 0, emptyDown = 0, emptyUp = 0;
		int[][] position;
		// when in the first column
		if (j == 0) {
			for (int k = 0; k < model.DIMENSION; k++) {
				if (model.getBoardPosition(i, k) == Value.Empty || model.getBoardPosition(i, k) == v) {
					emptyRight++;
				}
			}
			if (emptyRight == 2) {
				if (model.getBoardPosition(emptyRight - 1, j) == Value.Empty) {
					xPos = emptyRight - 1;
					yPos = j;
				} else {
					xPos = emptyRight;
					yPos = j;
				}
			}

		}
		// when in the second column
		if (j == 1) {
			j++;
			if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
				emptyRight++;
			} else {
				j = j - 2;
				if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
					emptyLeft++;
				}
			}
			if (emptyRight == 1 && emptyLeft == 1) {
				if (model.getBoardPosition(emptyRight, j) == Value.Empty) {
					xPos = emptyRight;
					yPos = j;
				} else {
					xPos = emptyLeft;
					yPos = j;
				}
			}
		}
		// when in the third column
		if (j == 2) {
			j--;
			if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
				emptyLeft++;
			} else {
				j--;
				if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
					emptyLeft++;
				}
			}
			if (emptyLeft == 2) {
				if (model.getBoardPosition(emptyLeft - 1, j) == Value.Empty) {
					xPos = emptyLeft - 1;
					yPos = j;
				} else {
					xPos = emptyLeft;
					yPos = j;
				}
			}

		}

		// when in the first line
		if (i == 0) {
			for (int k = 0; k < model.DIMENSION; k++) {
				if (model.getBoardPosition(i, k) == Value.Empty || model.getBoardPosition(i, k) == v) {
					emptyDown++;
				}
			}
			if (emptyDown == 2) {
				if (model.getBoardPosition(i, emptyDown - 1) == Value.Empty) {
					xPos = i;
					yPos = emptyDown - 1;
				} else {
					xPos = i;
					yPos = emptyDown;
				}
			}
		}
		// when in the second column
		if (i == 1) {
			i++;
			if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
				emptyDown++;
			} else {
				i = i - 2;
				if (model.getBoardPosition(i, j) == Value.Empty) {
					emptyUp++;
				}
			}
			if (emptyDown == 1 && emptyUp == 1) {
				if (model.getBoardPosition(i, emptyDown) == Value.Empty) {
					xPos = i;
					yPos = emptyDown;
				} else {
					xPos = i;
					yPos = emptyUp;
				}
			}
		}
		// when in the third column
		if (i == 2) {
			i--;
			if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
				emptyUp++;
			} else {
				i--;
				if (model.getBoardPosition(i, j) == Value.Empty) {
					emptyUp++;
				}
			}
			if (emptyUp == 2) {
				if (model.getBoardPosition(i, emptyUp - 1) == Value.Empty) {
					xPos = i;
					yPos = emptyUp - 1;
				} else {
					xPos = i;
					yPos = emptyUp;
				}
			}

		}
	}

}
