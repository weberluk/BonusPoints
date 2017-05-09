package Lotto.UnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Lotto.Configuration;
import Lotto.Lotto_Model;
import Lotto.ServiceLocator;
import Lotto.Translator;

public class Lotto_ModelTest {

	@Before
	public void setUp() throws Exception {
		ServiceLocator sl = ServiceLocator.getServiceLocator();

		// Initialize the resources in the service locator
		sl.setLogger(sl.configureLogging());

		sl.setConfiguration(new Configuration());

		String language = sl.getConfiguration().getOption("Language");
		sl.setTranslator(new Translator(language));
	}

	@Test
	public void testGetChance() {
		Lotto_Model model = new Lotto_Model();
		try {
			assertNotNull(model.getChance(50000, 49, 6));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckSuperPositiv() { // because both are initialised with 0
		Lotto_Model model = new Lotto_Model();
		String expectet = "The Super Number is correct";
		String result = model.checkWinSuper();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testCheckSuperNegativ() { // testing negativ
		Lotto_Model model = new Lotto_Model();
		String expectet = "The Super Number is not correct";
		model.setsuperNumber(9);
		String result = model.checkWinSuper();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckRegular6() { // for all right
		Lotto_Model model = new Lotto_Model();
		String expectet = "You have 6 correct";
		ArrayList<Integer> userTipp = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			userTipp.add(i);
		}
		model.setUserTipp(userTipp);
		ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			regularNumbersLotto.add(i);
		}
		model.setRegularNumbersLotto(regularNumbersLotto);
		String result = model.checkWinRegular();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckRegular5() { // for 5 right
		Lotto_Model model = new Lotto_Model();
		String expectet = "You have 5 correct";
		ArrayList<Integer> userTipp = new ArrayList<Integer>();
		for (int i = 1; i < 8; i++) {
			userTipp.add(i);
		}
		model.setUserTipp(userTipp);
		ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			regularNumbersLotto.add(i);
		}
		model.setRegularNumbersLotto(regularNumbersLotto);
		String result = model.checkWinRegular();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckRegular4() { // for 4 right
		Lotto_Model model = new Lotto_Model();
		String expectet = "You have 4 correct";
		ArrayList<Integer> userTipp = new ArrayList<Integer>();
		for (int i = 2; i < 9; i++) {
			userTipp.add(i);
		}
		model.setUserTipp(userTipp);
		ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			regularNumbersLotto.add(i);
		}
		model.setRegularNumbersLotto(regularNumbersLotto);
		String result = model.checkWinRegular();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckRegular3() { // for 3 right
		Lotto_Model model = new Lotto_Model();
		String expectet = "You have 3 correct";
		ArrayList<Integer> userTipp = new ArrayList<Integer>();
		for (int i = 3; i < 10; i++) {
			userTipp.add(i);
		}
		model.setUserTipp(userTipp);
		ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			regularNumbersLotto.add(i);
		}
		model.setRegularNumbersLotto(regularNumbersLotto);
		String result = model.checkWinRegular();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckRegular2() { // for 2 right
		Lotto_Model model = new Lotto_Model();
		String expectet = "You have 2 correct";
		ArrayList<Integer> userTipp = new ArrayList<Integer>();
		for (int i = 4; i < 11; i++) {
			userTipp.add(i);
		}
		model.setUserTipp(userTipp);
		ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			regularNumbersLotto.add(i);
		}
		model.setRegularNumbersLotto(regularNumbersLotto);
		String result = model.checkWinRegular();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckRegular1() { // for 1 right
		Lotto_Model model = new Lotto_Model();
		String expectet = "You have 1 correct";
		ArrayList<Integer> userTipp = new ArrayList<Integer>();
		for (int i = 5; i < 12; i++) {
			userTipp.add(i);
		}
		model.setUserTipp(userTipp);
		ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			regularNumbersLotto.add(i);
		}
		model.setRegularNumbersLotto(regularNumbersLotto);
		String result = model.checkWinRegular();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckRegularNothing() { // for 0 right
		Lotto_Model model = new Lotto_Model();
		String expectet = "Nothing is correct";
		ArrayList<Integer> userTipp = new ArrayList<Integer>();
		for (int i = 6; i < 13; i++) {
			userTipp.add(i);
		}
		model.setUserTipp(userTipp);
		ArrayList<Integer> regularNumbersLotto = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			regularNumbersLotto.add(i);
		}
		model.setRegularNumbersLotto(regularNumbersLotto);
		String result = model.checkWinRegular();
		try {
			assertEquals(expectet, result);
		} catch (Exception e) {
			fail();
		}
	}

}
