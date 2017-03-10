package Lotto;

public class Lotto_Controller {

	final private Lotto_View view;
	final private Lotto_Model model;

	protected Boolean[][] clicked = new Boolean[6][7];

	public Lotto_Controller(Lotto_Model model, Lotto_View view) {
		this.view = view;
		this.model = model;	
		setAllButtonsFalse();


		view.btnStart.setOnAction((event) -> {
			model.fillLottoNumbers();

			for (int i = 0; i < model.LOTTOLENGTH; i++) {
				view.labelsForNumbers[i].setText(Integer.toString(model.getRegularNumbers(i)));
				view.labelsForNumbers[i].setVisible(true);
			}
			view.superName.setVisible(true);
			view.sLabel.setText(Integer.toString(model.getsuperNumber()));
			view.sLabel.setVisible(true);
			model.checkWin();

		});

		view.btnGetChance.setOnAction((event) -> {

		});

		view.newGame.setOnAction((event) -> {

		});

		view.closeGame.setOnAction((event) -> {
			view.stop();
		});
		for (int i = 0; i < model.UPPERBOUND; i++){
			for (int j = 0; j < model.UPPERBOUND; j++){
				view.regularButtons[i][j].setOnAction((event) -> {
//					setButtonPressed();
					//Button idendifizieren über den Text und so rückschlüssen ziehen
					//event.getSource();
				});
			}
		} 
	}

	public void setButtonPressed(int i, int j) {
		if (clicked[i][j] == false) {
			view.regularButtons[i][j].setStyle("-fx-background-color: #800000;");
			clicked[i][j] = true;
			model.userTipp.add(view.regularButtons[i][j].getText());
		} else {
			view.regularButtons[i][j].setStyle(null);
			clicked[i][j] = false;
			model.userTipp.remove(view.regularButtons[i][j].getText());
		}

	}

	public void setAllButtonsFalse() {
		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				clicked[i][j] = new Boolean(false);
			}
		}

	}

	public void cleanUp() {

	}
}
