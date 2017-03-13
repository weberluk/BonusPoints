package Lotto;

import java.util.ArrayList;
import java.util.Random;

public class Lotto_Model {
	
	public static final int LOTTOLENGTH = 6;
	public static final int LOTTOHIGHT = 7;
	
	public final int MAXLOTTO = 6;
	public final int UPPERBOUND = 42;
	Random rand = new Random();
	
	protected ArrayList<Integer>userTipp = new ArrayList<Integer>();
	protected int userSuperTipp = 0;

	private Integer[] regularNumbersLotto = new Integer[MAXLOTTO];
	private int superNumber = 0;

	public void fillLottoNumbers() {
		for (int i = 0; i < MAXLOTTO; i++) {
			regularNumbersLotto[i] = rand.nextInt(UPPERBOUND) + 1;
		}
		superNumber = rand.nextInt(6)+1;
	}
	
	public int getRegularNumbers(int i){
			return regularNumbersLotto[i];
	}
	
	public int getsuperNumber(){
		return superNumber;
	}
	
	public void calculateChance(int[] input){
		
	}

	public void checkWin() {
		ArrayList<Integer> userTip = new ArrayList<Integer>();
		for (int i = 0; i < MAXLOTTO; i++){
			userTip.add(userTipp.get(i));
			
			if(userTip.contains(regularNumbersLotto[i])){
				System.out.println("You win!");
			} else {
				System.out.println("Sorry");
			}
		}

		
	}
}
