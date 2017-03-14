package Lotto;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Lotto_Controller {

	final private Lotto_View view;
	final private Lotto_Model model;

	protected Boolean[][] clicked = new Boolean[6][7];
	private int clicksRegular = 0;
	private int xPos;
	private int yPos;

	protected Boolean[] superclicked = new Boolean[6];
	private int clicksSuper = 0;
	private int superclicks = 1;

	public void setXPos(int xpos) {
		this.xPos = xpos;
	}

	public void setYPos(int ypos) {
		this.yPos = ypos;
	}

	public int getXPos() {
		return this.xPos;
	}

	public int getYPos() {
		return this.yPos;
	}

	public Lotto_Controller(Lotto_Model model, Lotto_View view) {
		this.view = view;
		this.model = model;
		setAllButtonsFalse();

		view.btnStart.setOnAction((event) -> {
			model.fillLottoNumbers();
			for (int i = 0; i < model.LOTTOLENGTH; i++) {
				view.lottoNumbersInButton[i].setText(Integer.toString(model.getRegularNumbers(i)));
				view.lottoNumbersInButton[i].setVisible(true);
			}
			view.superName.setVisible(true);
			view.sLabel.setText(Integer.toString(model.getsuperNumber()));
			view.sLabel.setVisible(true);
			view.superNumberButton.setText(Integer.toString(model.getsuperNumber()));
			view.superNumberButton.setVisible(true);
			view.tbox.setText(model.checkWin());
			view.btnStart.setDisable(true);
		});

		view.btnGetChance.setOnAction((event) -> {

		});

		view.newGame.setOnAction((event) -> {
			this.cleanUp();
		});

		view.closeGame.setOnAction((event) -> {
			view.stop();
		});

		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				final Button thisButton = view.regularButtons[i][j];

				view.regularButtons[i][j].setOnAction((event) -> {
					compareButton(Integer.parseInt(thisButton.getText()));
					this.setButtonPressed(this.getXPos(), this.getYPos());
				});
			}
		}
		view.superButton[0].setOnAction((event) -> {
			setSuperButtonPressed(0);
		});
		view.superButton[1].setOnAction((event) -> {
			setSuperButtonPressed(1);
		});
		view.superButton[2].setOnAction((event) -> {
			setSuperButtonPressed(2);
		});
		view.superButton[3].setOnAction((event) -> {
			setSuperButtonPressed(3);
		});
		view.superButton[4].setOnAction((event) -> {
			setSuperButtonPressed(4);
		});
		view.superButton[5].setOnAction((event) -> {
			setSuperButtonPressed(5);
		});
	}

	// event.getSource();

	public void compareButton(int btnTxt) {
		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				int k = Integer.parseInt(view.regularButtons[i][j].getText());
				if (k == btnTxt) {
					this.setXPos(i);
					this.setYPos(j);
				}
			}
		}
	}

	public void setButtonPressed(int i, int j) {
		if (clicked[i][j] == false) {
			if (this.clicksRegular < model.LOTTOLENGTH) {
				view.regularButtons[i][j].setStyle("-fx-background-color: #800000;");
				clicked[i][j] = true;
				model.userTipp.add(Integer.parseInt(view.regularButtons[i][j].getText()));
				this.clicksRegular++;
			}
		} else {
			view.regularButtons[i][j].setStyle(null);
			clicked[i][j] = false;
			model.userTipp.remove(view.regularButtons[i][j].getText());
			this.clicksRegular--;
		}
	}

	public void setSuperButtonPressed(int i) {
		if (this.superclicked[i] == false) {
			if (this.clicksSuper < this.superclicks) {
				view.superButton[i].setStyle("-fx-background-color: #800000;");
				this.superclicked[i] = true;
				this.clicksSuper++;
			}
		} else {
			view.superButton[i].setStyle(null);
			this.superclicked[i] = false;
			this.clicksSuper--;
		}

	}

	public void setAllButtonsFalse() {
		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				clicked[i][j] = new Boolean(false);
				superclicked[i] = false;
			}
		}

	}
	
	public void setTextBox(String input){
		view.tbox.setText(input);
	}

	public void cleanUp() {
		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				view.regularButtons[i][j].setStyle(null);
				this.clicked[i][j] = false;
				view.superButton[i].setStyle(null);
				this.superclicked[i] = false;
				view.lottoNumbersInButton[i].setVisible(false);
				this.clicksRegular = 0;
				this.clicksSuper = 0;
				model.clear();
				view.btnStart.setDisable(false);
			}
		}
		view.superNumberButton.setVisible(false);
	}
}
