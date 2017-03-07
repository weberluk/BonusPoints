package Lotto;

import java.util.Random;

public class Lotto_Model {
	
	public static final int LOTTOLENGTH = 6;
	public static final int LOTTOHIGHT = 7;
	
	public final int MAXLOTTO = 6;
	public final int UPPERBOUND = 42;
	Random rand = new Random();

	private Integer[] regularNumbersLotto = new Integer[MAXLOTTO];
	private int superNumber = 0;

	public void fillLottoNumbers() {
		for (int i = 0; i < MAXLOTTO; i++) {
			regularNumbersLotto[i] = rand.nextInt(UPPERBOUND) + 1;
		}
		superNumber = rand.nextInt(6)+1;
	}
	
	public void calculateChance(int[] input){
		
	}
}
