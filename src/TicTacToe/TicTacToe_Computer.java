package TicTacToe;

import java.util.Arrays;

import TicTacToe.TicTacToe_Model;
import TicTacToe.TicTacToe_Model.Value;

public class TicTacToe_Computer {
	private TicTacToe_Model model;

	public TicTacToe_Computer(TicTacToe_Model model) {
		this.model = model;
	}

	private Boolean[][] getFreePosition = new Boolean[model.DIMENSION][model.DIMENSION];
	private Boolean[][] getSameSign = new Boolean[model.DIMENSION][model.DIMENSION];

	// forplay
	private Boolean[] compPlayHorizontal = new Boolean[model.DIMENSION];
	private Boolean[] compPlayVertikal = new Boolean[model.DIMENSION];
	private Boolean[] compPlayDiagonal = new Boolean[model.DIMENSION];
	private Boolean[] compPlayAntiDiagonal = new Boolean[model.DIMENSION];

	// conterplay
	private Boolean[] conterPlayHorizontal = new Boolean[model.DIMENSION];
	private Boolean[] conterPlayVertikal = new Boolean[model.DIMENSION];
	private Boolean[] conterPlayDiagonal = new Boolean[model.DIMENSION];
	private Boolean[] conterPlayAntiDiagonal = new Boolean[model.DIMENSION];

	/**
	 * Check all the column and line for the possibilities
	 */

	public void checkComputerPlay() {
		Value[][] value = new Value[model.DIMENSION][model.DIMENSION];

		for (int i = 0; i < model.DIMENSION; i++) {
			for (int j = 0; j < model.DIMENSION; j++) {
				if (checkConter(i, j) == true) {
					if (!Arrays.toString(conterPlayHorizontal).contains("f")) {
						for (int k = 0; k < model.DIMENSION; k++) {
							if (model.getBoardPosition(i, k) == Value.Empty) {
								model.setXPos(i);
								model.setYPos(k);
							}
						}
					}
					if (!Arrays.toString(conterPlayVertikal).contains("f")) {
						for (int k = 0; k < model.DIMENSION; k++) {
							if (model.getBoardPosition(k, j) == Value.Empty) {
								model.setXPos(k);
								model.setYPos(j);
							}
						}
					}
					if (!Arrays.toString(conterPlayDiagonal).contains("f")) {
						if (i == j) {
							for (int k = 0; k < model.DIMENSION; k++) {
								if (model.getBoardPosition(i, j) == Value.Empty) {
									model.setXPos(i);
									model.setYPos(j);
								}
							}
						}
					}
					if (!Arrays.toString(conterPlayAntiDiagonal).contains("f")) {
						if (i == j) {
							for (int k = 0; k < model.DIMENSION; k++) {
								if (model.getBoardPosition(k, ((model.DIMENSION - 1) - k)) == Value.Empty) {
									model.setXPos(k);
									model.setYPos((model.DIMENSION - 1) - k);
								}
							}
						}
					}
				} else {
					if (checkComputPlay(i, j) == true) {
						if (!Arrays.toString(compPlayHorizontal).contains("f")) {
							for (int k = 0; k < model.DIMENSION; k++) {
								if (model.getBoardPosition(i, k) == Value.Empty) {
									model.setXPos(i);
									model.setYPos(k);
								}
							}
						}
						if (!Arrays.toString(compPlayVertikal).contains("f")) {
							for (int k = 0; k < model.DIMENSION; k++) {
								if (model.getBoardPosition(i, k) == Value.Empty) {
									model.setXPos(i);
									model.setYPos(k);
								}
							}
						}
						if (!Arrays.toString(compPlayDiagonal).contains("f")) {
							for (int k = 0; k < model.DIMENSION; k++) {
								if (model.getBoardPosition(i, k) == Value.Empty) {
									model.setXPos(i);
									model.setYPos(k);
								}
							}
						}
						if (!Arrays.toString(compPlayAntiDiagonal).contains("f")) {
							for (int k = 0; k < model.DIMENSION; k++) {
								if (model.getBoardPosition(i, k) == Value.Empty) {
									model.setXPos(i);
									model.setYPos(k);
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean checkComputPlay(int i, int j) {

		// check Compplay in horizontal
		Arrays.fill(compPlayHorizontal, false);
		for (int k = 0; k < model.DIMENSION; k++) {
			if (model.getBoardPosition(i, k) == Value.Empty || model.getBoardPosition(i, j) == model.getSign()) {
				compPlayHorizontal[k] = true;
			}
			if (!Arrays.toString(compPlayHorizontal).contains("f")) {
				return true;
			}
		}

		// check Compplay in vertical
		Arrays.fill(compPlayVertikal, false);
		for (int k = 0; k < model.DIMENSION; k++) {
			if (model.getBoardPosition(k, j) == Value.Empty || model.getBoardPosition(i, j) == model.getSign()) {
				compPlayVertikal[k] = true;
			}
			if (!Arrays.toString(compPlayVertikal).contains("f")) {
				return true;
			}
		}

		// check Compplay in diagonal
		Arrays.fill(compPlayDiagonal, false);
		if (i == j) {
			for (int k = 0; k < model.DIMENSION; k++) {
				if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == model.getSign()) {
					compPlayDiagonal[k] = true;
				}
				if (!Arrays.toString(compPlayDiagonal).contains("f")) {
					return true;
				}
			}
		}
		// check Compplay anti-diagonal
		Arrays.fill(compPlayAntiDiagonal, false);
		for (int k = 0; k < model.DIMENSION; k++) {
			if (model.getBoardPosition(k, ((model.DIMENSION - 1) - k)) == Value.Empty
					|| model.getBoardPosition(k, ((model.DIMENSION - 1) - k)) == model.getSign()) {
				compPlayAntiDiagonal[k] = true;
			}
			if (!Arrays.toString(compPlayAntiDiagonal).contains("f")) {
				return true;
			}
		}
		return false;
	}

	public boolean checkConter(int i, int j) {
		// check Compplay Conter in horizontal
		Arrays.fill(conterPlayHorizontal, false);
		for (int k = 0; k < model.DIMENSION; k++) {
			if (model.getBoardPosition(i, k) == Value.Empty || model.getBoardPosition(i, j) != model.getSign()) {
				conterPlayHorizontal[k] = true;
			}
			if (!Arrays.toString(conterPlayHorizontal).contains("f")) {
				return true;
			}
		}

		// check Compplay Conter in vertical
		Arrays.fill(conterPlayVertikal, false);
		for (int k = 0; k < model.DIMENSION; k++) {
			if (model.getBoardPosition(k, j) == Value.Empty || model.getBoardPosition(i, j) != model.getSign()) {
				conterPlayVertikal[k] = true;
			}
			if (!Arrays.toString(conterPlayVertikal).contains("f")) {
				return true;
			}
		}

		// check Compplay Conter in diagonal
		Arrays.fill(conterPlayDiagonal, false);
		if (i == j) {
			for (int k = 0; k < model.DIMENSION; k++) {
				if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) != model.getSign()) {
					conterPlayDiagonal[k] = true;
				}
				if (!Arrays.toString(conterPlayDiagonal).contains("f")) {
					return true;
				}
			}
		}
		// check Compplay Conter anti-diagonal
		Arrays.fill(conterPlayAntiDiagonal, false);
		for (int k = 0; k < model.DIMENSION; k++) {
			if (model.getBoardPosition(k, ((model.DIMENSION - 1) - k)) == Value.Empty
					|| model.getBoardPosition(k, ((model.DIMENSION - 1) - k)) != model.getSign()) {
				conterPlayAntiDiagonal[k] = true;
			}
			if (!Arrays.toString(conterPlayAntiDiagonal).contains("f")) {
				return true;
			}
		}
		return false;
	}
}
