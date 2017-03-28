package TicTacToe;

import java.util.ArrayList;
import TicTacToe.TicTacToe_Model;

class ResultMM {
	String[] matrix;
	int score;
	int depth;

	public ResultMM(String[] matrix, int score, int depth) {
		this.matrix = matrix;
		this.score = score;
		this.depth = depth;
	}

	public void updateMatrix(String[] matrix) {
		this.matrix = matrix;
	}

	public int getScore() {
		return score;
	}

	public int getIntrus() {
		for (int i = 0; i < 9; i++)
			if (matrix[i].equals("o"))
				return i;
		return -1;
	}
}

public class TicTacToe_MiniMax {
	private TicTacToe_Model model;

	// gameXO is the game
	static String[] gameXO = new String[9];
	// _sdepth is used to control the depth
	static int sdepth;

	public TicTacToe_MiniMax(TicTacToe_Model model) {
		this.model = model;

		// reInitialise the gameXO when i create a new controller
			for (int i = 0; i < 9; i++)
				gameXO[i] = " ";
	}
	
	public void setAllEmpty(){
		for (int i = 0; i < 9; i++)
			gameXO[i] = " ";
	}

	// inverse level from MIN to MAX
	public String inverse(String level) { 
		return (level.equals("MIN")) ? "MAX" : "MIN";
	}

	public int getScore(String[] demo) { // return the score:
											// if X win return -1;
											// if O win return 1;
											// else return 0, this mean draw
		if ((demo[0].equalsIgnoreCase("x") && demo[1].equalsIgnoreCase("x") && demo[2].equalsIgnoreCase("x"))
				|| (demo[3].equalsIgnoreCase("x") && demo[4].equalsIgnoreCase("x") && demo[5].equalsIgnoreCase("x"))
				|| (demo[6].equalsIgnoreCase("x") && demo[7].equalsIgnoreCase("x") && demo[8].equalsIgnoreCase("x"))
				|| (demo[0].equalsIgnoreCase("x") && demo[3].equalsIgnoreCase("x") && demo[6].equalsIgnoreCase("x"))
				|| (demo[1].equalsIgnoreCase("x") && demo[4].equalsIgnoreCase("x") && demo[7].equalsIgnoreCase("x"))
				|| (demo[2].equalsIgnoreCase("x") && demo[5].equalsIgnoreCase("x") && demo[8].equalsIgnoreCase("x"))
				|| (demo[0].equalsIgnoreCase("x") && demo[4].equalsIgnoreCase("x") && demo[8].equalsIgnoreCase("x"))
				|| (demo[2].equalsIgnoreCase("x") && demo[4].equalsIgnoreCase("x") && demo[6].equalsIgnoreCase("x")))
			return -1;
		if ((demo[0].equalsIgnoreCase("o") && demo[1].equalsIgnoreCase("o") && demo[2].equalsIgnoreCase("o"))
				|| (demo[3].equalsIgnoreCase("o") && demo[4].equalsIgnoreCase("o") && demo[5].equalsIgnoreCase("o"))
				|| (demo[6].equalsIgnoreCase("o") && demo[7].equalsIgnoreCase("o") && demo[8].equalsIgnoreCase("o"))
				|| (demo[0].equalsIgnoreCase("o") && demo[3].equalsIgnoreCase("o") && demo[6].equalsIgnoreCase("o"))
				|| (demo[1].equalsIgnoreCase("o") && demo[4].equalsIgnoreCase("o") && demo[7].equalsIgnoreCase("o"))
				|| (demo[2].equalsIgnoreCase("o") && demo[5].equalsIgnoreCase("o") && demo[8].equalsIgnoreCase("o"))
				|| (demo[0].equalsIgnoreCase("o") && demo[4].equalsIgnoreCase("o") && demo[8].equalsIgnoreCase("o"))
				|| (demo[2].equalsIgnoreCase("o") && demo[4].equalsIgnoreCase("o") && demo[6].equalsIgnoreCase("o")))
			return 1;
		return 0;
	}

	//test if the game is draw.
	//if demo is full means that game is draw
	//if demo has empty square, this mean that the game isn't finish
	public boolean drawGame(String[] demo) {
		for (int i = 0; i < 9; i++)
			if (demo[i].equals(" "))
				return false;
		return true;
	}

	// if the score of the game is 0 then return false, else we have a winner
	public boolean gameOver(String[] demo) {
		return (getScore(demo) != 0) ? true : false;
	}

	// generate successor if level is MAX, generate successor with o (o in lowercase)
	// if level is MIN, generate successor with x (x in lowercase)
	// if demo has no successor, return null
	public ArrayList<String[]> genere_succ(String[] demo, String level) {
		ArrayList<String[]> succ = new ArrayList<String[]>();
		for (int i = 0; i < demo.length; i++) {
			if (demo[i].equals(" ")) {
				String[] child = new String[9];
				for (int j = 0; j < 9; j++)
					child[j] = demo[j];
				if (level.equals("MAX")){
					child[i] = "o";
				}
				else {
					child[i] = "x";	
				}
				succ.add(child);
			}
		}
		return (succ.size() == 0) ? null : succ;
	}

	// this method is used to get the appropirate score 
	//if level is MAX, i search for the higher score in the nearer depth 
	//if level is MIN, i search for the lowest score in the nearer depth
	public ResultMM getResult(ArrayList<ResultMM> listScore, String level) {
		ResultMM result = listScore.get(0);
		if (level.equals("MAX")) {
			for (int i = 1; i < listScore.size(); i++) {
				if ((listScore.get(i).getScore() > result.getScore())
						|| (listScore.get(i).getScore() == result.getScore() && listScore.get(i).depth < result.depth))
					result = listScore.get(i);
			}
		} else {
			for (int i = 1; i < listScore.size(); i++) {
				if ((listScore.get(i).getScore() < result.getScore())
						|| (listScore.get(i).getScore() == result.getScore() && listScore.get(i).depth < result.depth))
					result = listScore.get(i);
			}
		}
		return result;
	}
	
	/*
	 * MinMax algorithm 
	 * 1- generate successor 
	 * 2- if no successor or game is finished return score 
	 * 3- if there is successor 
	 * a) apply MinMax for each successor
	 * b) after recursive call, i return the good score
	 */
	public ResultMM MinMax(String[] demo, String level, int fils,
			int depth) {

		// 1---------------
		ArrayList<String[]> children = genere_succ(demo, level);
		// 2------------------
		if (children == null || gameOver(demo)) {
			return new ResultMM(demo, getScore(demo), depth);
		} else {
			// 3------------------
			ArrayList<ResultMM> listScore = new ArrayList<ResultMM>();
			// pass into each child
			for (int i = 0; i < children.size(); i++) {
				// 3 a)---------------
				listScore.add(MinMax(children.get(i), inverse(level), 1, depth + 1));
			}
			// 3 b)----------------
			ResultMM res = getResult(listScore, level);
			if (fils == 1)
				res.updateMatrix(demo);
			return res;
		}
	}

	
	/**
	 * Step to do in this method 
	 * 1- update game put X in the game[index] 
	 * 2- test if game is finished (draw or X win) 
	 * 3- call MinMax algorithm and return the score and return the best position for O
	 * 4- update game put O in its position and set the new Position for the model
	 * 5- test if game is finished (draw or O win)
	 * -------------------------------------------
	 * return -1 to know that player X wins
	 * return -2 to know that the game is draw
	 **/
	// 1
	public int makeMove(
			int index) { 
		gameXO[index] = "X";
		// 2
		if (gameOver(gameXO)) {
			return -1;
		}
		if (drawGame(gameXO)) {
			return -2;
		}
		// 3
		ResultMM res = MinMax(gameXO, "MAX", 0, 0);
		int i = res.getIntrus();
		// 4
		gameXO[i] = "O";
		if (i == 0) {
			model.setXPos(0);
			model.setYPos(0);
		}
		if (i == 1) {
			model.setXPos(0);
			model.setYPos(1);
		}
		if (i == 2) {
			model.setXPos(0);
			model.setYPos(2);
		}
		if (i == 3) {
			model.setXPos(1);
			model.setYPos(0);
		}
		if (i == 4) {
			model.setXPos(1);
			model.setYPos(1);
		}
		if (i == 5) {
			model.setXPos(1);
			model.setYPos(2);
		}
		if (i == 6) {
			model.setXPos(2);
			model.setYPos(0);
		}
		if (i == 7) {
			model.setXPos(2);
			model.setYPos(1);
		}
		if (i == 8) {
			model.setXPos(2);
			model.setYPos(2);
		}

		// 5
		// return i+20 to know that o wins (i used this method for programming issues
		// return i-30 to know that the game is draw /i used this method for programming issues)
		if (gameOver(gameXO)) {
			return i + 20;
		}
		if (drawGame(gameXO)) {
			return i - 30;
		}
		return i;
	}
}