package Lotto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import com.sun.media.jfxmedia.logging.Logger;

public class Lotto_Model {
	
	ServiceLocator servicelocator = ServiceLocator.getServiceLocator();
    Translator t = servicelocator.getTranslator();

	public static final int LOTTOLENGTH = 6;
	public static final int LOTTOHIGHT = 7;

	public final int UPPERBOUND = 42;
	private Random rand = new Random();

	private Boolean[] checkSumRegular = new Boolean[LOTTOLENGTH];
	private int rightRegualarChecker = 0;
	private boolean checkSuper = false;

	protected ArrayList<Integer> userTipp = new ArrayList<Integer>();
	protected int userSuperTipp = 0;

	private ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
	private int superNumber = 0;
	
	public int getRegularNumbersLotto(){
		return this.regularNumbersLotto.size();
	}
	public int getSuperNumberCount(){
		return superNumber;
	}
	public void clear(){
		servicelocator.getLogger().info("Clear all");
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

	public String checkWinRegular() {
		ArrayList<Integer> userTip = new ArrayList<Integer>();
		for (int i = 0; i < LOTTOLENGTH; i++) {
			userTip.add(userTipp.get(i));

			if (userTip.contains(regularNumbersLotto.get(i))) {
				this.checkSumRegular[i] = true;
			} else {
				this.checkSumRegular[i] = false;
			}
		}
		return this.checkHowManyRight();
	}
	public String checkWinSuper() {
		return this.checkSuper();
	}

	
	//check de Regular
	private String checkHowManyRight() {
		for (int i = 0; i < LOTTOLENGTH; i++) {
			if (this.checkSumRegular[i] == true) {
				this.rightRegualarChecker++;
			}
		}
		switch (this.rightRegualarChecker) {

		case 1:
			servicelocator.getLogger().info("1 Choice is equal");
			return "You have 1 correct";
//			return t.getString("program.text.win1");
		case 2:
			servicelocator.getLogger().info("2 Choice is equal");
			return "You have 2 correct";
//			return t.getString("program.text.win2");
		case 3:
			servicelocator.getLogger().info("3 Choice is equal");
			return "You have 3 correct";
//			return t.getString("program.text.win3");
		case 4:
			servicelocator.getLogger().info("4 Choice is equal");
			return "You have 4 correct";
//			return t.getString("program.text.win4");
		case 5:
			servicelocator.getLogger().info("5 Choice is equal");
			return "You have 5 correct";
//			return t.getString("program.text.win5");
		case 6:
			servicelocator.getLogger().info("6 Choice is equal");
			return "You have 6 correct";
//			return t.getString("program.text.win6");
		}
		return "Nothing is correct";
//		return t.getString("program.text.winNothing");

	}
	
	//check the super
	private String checkSuper(){
		servicelocator.getLogger().info("Check the SuperNumber");
		if(this.userSuperTipp == superNumber){
			return "The Supernumber is not correct";
//			String result = " ";
//			result += t.getString("program.text.superwin");
//			return result;
		}
		return "The Supernumber is correct";
	}
	
	    
	// The Chance for winning - the most popular numbers - this Method is written with some help with the Internet
    public String getChance(int coupons, int maxNumbers, int choiceNumbers)
    {                       
        HashSet<Integer> hWinner = new HashSet<Integer>();
                 
        int[][] lotto = getResult(coupons, maxNumbers, choiceNumbers);
        int[] winnerNumbers = lotto[0];
        for (int i = 0; i < winnerNumbers.length; i++) {
            hWinner.add( winnerNumbers[i] );
        }
             
        int[] countCorrect = new int[choiceNumbers+1];
         
        for (int i = 1; i < lotto.length; i++) {
            int numbersCorrect = 0;
            int[] coupon = lotto[i];
            for (int j = 0; j < coupon.length; j++) {
                int number = coupon[j];
                if(  hWinner.contains(number))
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
        result += "\nNummerbereich ist 1 .. "+maxNumbers;
        
        //add the pickSize
        result += "\nGewählte Nummern: "+choiceNumbers;
        
        //add the coupons
        result += "\nGrösse der Berechnungssumme: "+coupons;
        
        //probability for winning
        for (int i = 0; i < countCorrect.length; i++) {
           result += ("\n"+i+" Treffer: " +countCorrect[i] + " ~ "+nf.format(100*(countCorrect[i]/(double)coupons))+  "%");
        }  
        return result;
    }
	
	
    private final NumberFormat nf = new DecimalFormat("#.#####");
    
    private int[][] getResult(int coupons, int maxNumbers, int choiceNumbers) {
        int[][] lottos = new int[coupons+1][choiceNumbers];
 
        //prepare the range of possibilities
        for (int coupon = 0; coupon < coupons+1; coupon++) {
            int size = maxNumbers;
            int[] possibleNumbers = new int[size];
            int[] lottoNumbers = new int[choiceNumbers];
 
            //these numbers are possible
            for (int i = 0; i < possibleNumbers.length; i++)
                possibleNumbers[i] = i + 1;
 
            //generate random numbers
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
}
