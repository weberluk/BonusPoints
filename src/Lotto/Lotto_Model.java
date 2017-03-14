package Lotto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Lotto_Model {

	public static final int LOTTOLENGTH = 6;
	public static final int LOTTOHIGHT = 7;

	public final int UPPERBOUND = 42;
	private Random rand = new Random();

	private Boolean[] checkSumRegular = new Boolean[LOTTOLENGTH];
	private int rightRegualarChecker = 0;
	private boolean checkSuper = false;

	protected ArrayList<Integer> userTipp = new ArrayList<Integer>();
	private int userSuperTipp = 0;

	private ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
	private int superNumber = 0;
	
	public int getRegularNumbersLotto(){
		return this.regularNumbersLotto.size();
	}
	public int getSuperNumberCount(){
		return superNumber;
	}
	public void clear(){
		this.regularNumbersLotto.clear();
		this.superNumber = 0;
		this.rightRegualarChecker = 0;
		this.checkSuper = false;
		Arrays.fill(this.checkSumRegular, 0, 5, false);
		this.userSuperTipp = 0;
		this.userTipp.clear();
	}

	// check also contains - there are no doublets - do it again recursive
	public void fillLottoNumbers() {
		for (int i = 0; i < LOTTOLENGTH; i++) {
			int tempRandom = rand.nextInt(UPPERBOUND) + 1;
			if (!regularNumbersLotto.contains(tempRandom)) {
				this.regularNumbersLotto.add(tempRandom);
			} else {
				if (this.regularNumbersLotto.size() < this.LOTTOLENGTH) {
					fillLottoNumbers();
				}
			}
		}
		superNumber = rand.nextInt(6) + 1;
	}

	public int getRegularNumbers(int i) {
		return regularNumbersLotto.get(i);
	}

	public int getsuperNumber() {
		return superNumber;
	}

	public void setsuperNumber(int superNumber) {
		this.userSuperTipp = superNumber;
	}

	public void calculateChance(int[] input) {

	}

	public String checkWin() {
		ArrayList<Integer> userTip = new ArrayList<Integer>();
		for (int i = 0; i < LOTTOLENGTH; i++) {
			userTip.add(userTipp.get(i));

			if (userTip.contains(regularNumbersLotto.get(i))) {
				this.checkSumRegular[i] = true;
			} else {
				this.checkSumRegular[i] = false;
			}
		}
		if (this.userSuperTipp == superNumber) {
			this.checkSuper = true;
		}
		return checkHowManyRight();
	}

	private String checkHowManyRight() {
		for (int i = 0; i < LOTTOLENGTH; i++) {
			if (this.checkSumRegular[i] == true) {
				this.rightRegualarChecker++;
			}
		}
		switch (this.rightRegualarChecker) {

		case 1:
			return "You win 1";
		case 2:
			return "You win 2";
		case 3:
			return "You win 3";
		case 4:
			return "You win 4";
		case 5:
			return "You win 5";
		case 6:
			return "You win 6";
		}
		return "Nothing is right";

	}
}
