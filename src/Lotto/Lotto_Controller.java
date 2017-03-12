package Lotto;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Lotto_Controller {

	final private Lotto_View view;
	final private Lotto_Model model;

	protected Boolean[][] clicked = new Boolean[6][7];
	private int xPos;
	private int yPos;
	
	public void setXPos(int xpos){
		this.xPos = xpos;
	}
	public void setYPos(int ypos){
		this.yPos = ypos;
	}
	public int getXPos(){
		return this.xPos;
	}
	public int getYPos(){
		return this.yPos;
	}

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

		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				final Button thisButton = view.regularButtons[i][j];
				
				view.regularButtons[i][j].setOnAction(( event) -> {
					compareButton(thisButton.textProperty().getValue());
					this.setButtonPressed(this.getXPos(), this.getYPos());
				});
			}
		}
	}
	//event.getSource();
	
	public void compareButton(String btnTxt){
		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				if(view.regularButtons[i][j].equals(btnTxt)){
					this.setXPos(i);
					this.setYPos(j);
				}
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
