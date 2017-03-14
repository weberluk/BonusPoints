package Lotto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import com.sun.media.jfxmedia.logging.Logger;

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
		LottoStart.LOGGER.info("Clear all");
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
			LottoStart.LOGGER.info("1 Choice is equal");
			return "You win 1";
		case 2:
			LottoStart.LOGGER.info("2 Choice is equal");
			return "You win 2";
		case 3:
			LottoStart.LOGGER.info("3 Choice is equal");
			return "You win 3";
		case 4:
			LottoStart.LOGGER.info("4 Choice is equal");
			return "You win 4";
		case 5:
			LottoStart.LOGGER.info("5 Choice is equal");
			return "You win 5";
		case 6:
			LottoStart.LOGGER.info("6 Choice is equal");
			return "You win 6";
		}
		return "Nothing is right";

	}
	    
	// The Chance for winning - the most popular numbers
    private final NumberFormat nf = new DecimalFormat("#.#####");
    
    private int[][] getResult(int coupons, int maxNumbers, int choiceNumbers) {
        Random rand = new Random();
        int[][] lottos = new int[coupons+1][choiceNumbers];
 
        // O(numbersOfCoupons * k)
        for (int coupon = 0; coupon < coupons+1; coupon++) {
            int size = maxNumbers;
            int[] possibleNumbers = new int[size];
            int[] lottoNumbers = new int[choiceNumbers];
 
            // O(numbersRange * k)
            // store possible numbers for random selection
            for (int i = 0; i < possibleNumbers.length; i++)
                possibleNumbers[i] = i + 1;
 
            // O(numbersOfuse * k)
            // pick random numbersOfuse numbers not same
            for (int i = 0; i < lottoNumbers.length; i++) {
                int last = size - 1;
                int next = rand.nextInt(size);
                int temp = possibleNumbers[last];
                int value = possibleNumbers[next];
                possibleNumbers[next] = temp;
                lottoNumbers[i] = value;
                size--;
            }
            lottos[coupon] = lottoNumbers;
        }
        return lottos;
    }
     
    public String getChance(int coupons, int n, int pickSize)
    {                       
        HashSet<Integer> winnerNumbersLookup = new HashSet<Integer>();
                 
        int[][] lotto = getResult(coupons, n, pickSize);
        int[] winnerNumbers = lotto[0];
        for (int i = 0; i < winnerNumbers.length; i++) {
            winnerNumbersLookup.add( winnerNumbers[i] );
        }
             
        int[] countCorrect = new int[pickSize+1];
         
        for (int i = 1; i < lotto.length; i++) {
            int numbersCorrect = 0;
            int[] coupon = lotto[i];
            for (int j = 0; j < coupon.length; j++) {
                int number = coupon[j];
                if(  winnerNumbersLookup.contains(number))
                    numbersCorrect++;
            }
            countCorrect[numbersCorrect]++;         
        }
         
        String result = "Die Nummern, welche am Meisten gewinnen: " + "\n";
        
        //add the winnerNumbers in the String - which are the most winner
        Arrays.sort(winnerNumbers);
        for (int i = 0; i < winnerNumbers.length; i++) {
            result += (winnerNumbers[i]+ " ");
        }
        
        //add the range
        result += "\nNummerbereich ist 1 .. "+n;
        
        //add the pickSize
        result += "\nGewählte Nummern: "+pickSize;
        
        //add the coupons
        result += "\nGrösse der Berechnungssumme: "+coupons;
        
        //probability for winning
        for (int i = 0; i < countCorrect.length; i++) {
           result += ("\n"+i+" Treffer: " +countCorrect[i] + " ~ "+nf.format(100*(countCorrect[i]/(double)coupons))+  "%");
        }  
        return result;
    }
}
