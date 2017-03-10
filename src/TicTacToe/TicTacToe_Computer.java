package TicTacToe;

import java.util.Arrays;

import TicTacToe.TicTacToe_Model.Value;

public class TicTacToe_Computer {
	private TicTacToe_Model model;
	
	public TicTacToe_Computer(TicTacToe_Model model){
		this.model = model;
	}
	
	private Boolean[][] getFreePosition = new Boolean[model.DIMENSION][model.DIMENSION];
	private Boolean[][] getSameSign = new Boolean[model.DIMENSION][model.DIMENSION];
	
	private Boolean[] compPlayHorizontal = new Boolean[model.DIMENSION];
	private Boolean[] compPlayVertikal = new Boolean[model.DIMENSION];
	private Boolean[] compPlayDiagonal = new Boolean[model.DIMENSION];
	private Boolean[] compPlayAntiDiagonal = new Boolean[model.DIMENSION];
	/**
	 * Check all the column and line for the possibilities
	 */
	
	public void checkComputerPlay(){
		Value[][] value = new Value[model.DIMENSION][model.DIMENSION];
		
		for (int i = 0; i < model.DIMENSION; i++){
			for (int j = 0; j < model.DIMENSION; j++){
				if(checkComputPlay(i,j) == true){
					if(!Arrays.toString(compPlayHorizontal).contains("f")){
						for(int k = 0; k < model.DIMENSION; k++){
							if (model.getBoardPosition(i, k) == Value.Empty){
								model.setXPos(i);
								model.setYPos(k);
							}
						}
					}
					if(!Arrays.toString(compPlayVertikal).contains("f")){
						for(int k = 0; k < model.DIMENSION; k++){
							if (model.getBoardPosition(k, j) == Value.Empty){
								model.setXPos(k);
								model.setYPos(j);
							}
						}
					}
					if(!Arrays.toString(compPlayDiagonal).contains("f")){
						if(i == j){
							for(int k = 0; k < model.DIMENSION; k++){
								if (model.getBoardPosition(i, j) == Value.Empty){
									model.setXPos(i);
									model.setYPos(j);
								}
							}
						}
					}
					if(!Arrays.toString(compPlayAntiDiagonal).contains("f")){
						if(i == j){
							for(int k = 0; k < model.DIMENSION; k++){
								if (model.getBoardPosition(k, ((model.DIMENSION-1)- k)) == Value.Empty){
									model.setXPos(k);
									model.setYPos((model.DIMENSION-1)-k);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public boolean checkComputPlay (int i, int j) {


		// check Compplay in horizontal
		Arrays.fill(compPlayHorizontal, false);
		for(int k = 0; k < model.DIMENSION; k++){
			if(model.getBoardPosition(i, k) == Value.Empty || model.getBoardPosition(i, j) == model.getSign()){
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
			if (model.getBoardPosition(k, ((model.DIMENSION-1)- k)) == Value.Empty || model.getBoardPosition(k, ((model.DIMENSION-1)- k)) == model.getSign()) {
				compPlayAntiDiagonal[k] = true;
			}
			if (!Arrays.toString(compPlayAntiDiagonal).contains("f")) {
				return true;
			}
		}
		return false;
	}
	

//	/**
//	 * Check all the column and line for the possibilities
//	 */
//	public void getOpenPositionInBoard(int i, int j) {
//		int emptyLeft = 0, emptyRight = 0, emptyDiagonal = 0, emtpyAntiDiagonal = 0, emptyDown = 0, emptyUp = 0;
//		int[][] position;
//		Value v = model.getSign();
//		// when in the first column
//		if (j == 0) {
//			for (int k = 0; k < model.DIMENSION; k++) {
//				if (model.getBoardPosition(i, k) == Value.Empty || model.getBoardPosition(i, k) == v) {
//					emptyRight++;
//				}
//			}
//			if (emptyRight == 2) {
//				if (model.getBoardPosition(emptyRight - 1, j) == Value.Empty) {
//					model.setXPos(emptyRight - 1);
//					model.setYPos(j);
//				} else {
//					model.setXPos(emptyRight);
//					model.setYPos(j);
//				}
//			}
//
//		}
//		// when in the second column
//		if (j == 1) {
//			j++;
//			if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
//				emptyRight++;
//			} else {
//				j = j - 2;
//				if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
//					emptyLeft++;
//				}
//			}
//			if (emptyRight == 1 && emptyLeft == 1) {
//				if (model.getBoardPosition(emptyRight, j) == Value.Empty) {
//					model.setXPos(emptyRight);
//					model.setYPos(j);
//				} else {
//					model.setXPos(emptyLeft);
//					model.setYPos(j);
//				}
//			}
//		}
//		// when in the third column
//		if (j == 2) {
//			j--;
//			if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
//				emptyLeft++;
//			} else {
//				j--;
//				if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
//					emptyLeft++;
//				}
//			}
//			if (emptyLeft == 2) {
//				if (model.getBoardPosition(emptyLeft - 1, j) == Value.Empty) {
//					model.setXPos(emptyLeft);
//					model.setYPos(j);
//				} else {
//					model.setXPos(emptyLeft);
//					model.setYPos(j);
//				}
//			}
//
//		}
//
//		// when in the first line
//		if (i == 0) {
//			for (int k = 0; k < model.DIMENSION; k++) {
//				if (model.getBoardPosition(i, k) == Value.Empty || model.getBoardPosition(i, k) == v) {
//					emptyDown++;
//				}
//			}
//			if (emptyDown == 2) {
//				if (model.getBoardPosition(i, emptyDown - 1) == Value.Empty) {
//					model.setXPos(i);
//					model.setYPos(emptyDown - 1);
//				} else {
//					model.setXPos(i);
//					model.setYPos(emptyDown);
//				}
//			}
//		}
//		// when in the second column
//		if (i == 1) {
//			i++;
//			if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
//				emptyDown++;
//			} else {
//				i = i - 2;
//				if (model.getBoardPosition(i, j) == Value.Empty) {
//					emptyUp++;
//				}
//			}
//			if (emptyDown == 1 && emptyUp == 1) {
//				if (model.getBoardPosition(i, emptyDown) == Value.Empty) {
//					model.setXPos(i);
//					model.setYPos(emptyDown);
//				} else {
//					model.setXPos(i);
//					model.setYPos(emptyUp);
//				}
//			}
//		}
//		// when in the third column
//		if (i == 2) {
//			i--;
//			if (model.getBoardPosition(i, j) == Value.Empty || model.getBoardPosition(i, j) == v) {
//				emptyUp++;
//			} else {
//				i--;
//				if (model.getBoardPosition(i, j) == Value.Empty) {
//					emptyUp++;
//				}
//			}
//			if (emptyUp == 2) {
//				if (model.getBoardPosition(i, emptyUp - 1) == Value.Empty) {
//					model.setXPos(i);
//					model.setYPos(emptyUp - 1);
//				} else {
//					model.setXPos(i);
//					model.setXPos(emptyUp);
//				}
//			}
//
//		}
//	}

}
