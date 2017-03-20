package Lotto;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import Lotto.Lotto_View;
import Lotto.Lotto_Model;

public class Lotto_Controller {
	
	final private Lotto_View view;
	final private Lotto_Model model;
    private ServiceLocator sl = ServiceLocator.getServiceLocator();
	
    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();
	
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
			sl.getLogger().info("Calculating the Lotto-Numbers");
			for (int i = 0; i < model.LOTTOLENGTH; i++) {
				view.lottoNumbersInButton[i].setText(Integer.toString(model.getRegularNumbers(i)));
				view.lottoNumbersInButton[i].setVisible(true);
			}
			view.superName.setVisible(true);
			view.nrTop.setVisible(true);
			view.sLabel.setText(Integer.toString(model.getsuperNumber()));
			view.sLabel.setVisible(true);
			view.superNumberButton.setText(Integer.toString(model.getsuperNumber()));
			view.superNumberButton.setVisible(true);
			sl.getLogger().info("Check for winner now");
			view.tbox1.setText(model.checkWinRegular());
			view.tbox2.setText(model.checkWinSuper());
			view.btnStart.setDisable(true);
		});

		view.btnGetChance.setOnAction((event) -> {
			sl.getLogger().info("Calculating the chance for winning");
			int coupons = 0;
			try {
				coupons = Integer.parseInt(view.tcoupons.getText());
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Gewinn Chance");
				alert.setHeaderText("Hier ist deine Gewinn Chance");
				alert.setContentText(model.getChance(coupons, 49, 6));

				alert.showAndWait();
			} catch (Exception e) {
				view.tbox1.setText("Eingabe: Keine Zahl, oder Zahl zu gross!");
			}
		});

		view.newGame.setOnAction((event) -> {
			this.cleanUp();
		});

		view.closeGame.setOnAction((event) -> {
			sl.getLogger().info("terminating the program");
			view.stop();
		});
		
		view.documentation.setOnAction((event) -> {
			sl.getLogger().info("Dokumentation");
            String file = fileChooser.getInitialFileName();
//            if (file != null) {
//                openFile(file);
//            }
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
			sl.getLogger().info("New Regular Button is pressed: " + view.regularButtons[i][j].getText());
			if (this.clicksRegular < model.LOTTOLENGTH) {
				sl.getLogger().info("There are place for another number: " + view.regularButtons[i][j].getText());
				view.regularButtons[i][j].setStyle("-fx-background-color: #800000;");
				clicked[i][j] = true;
				model.userTipp.add(Integer.parseInt(view.regularButtons[i][j].getText()));
				this.clicksRegular++;
			} else {
				sl.getLogger().info("This number cannot add, because there is no place: " + view.regularButtons[i][j].getText());
			}
		} else {
			sl.getLogger().info("This button is no longer valid: " + view.regularButtons[i][j].getText());
			view.regularButtons[i][j].setStyle(null);
			clicked[i][j] = false;
			model.userTipp.remove(view.regularButtons[i][j].getText());
			this.clicksRegular--;
		}
	}

	public void setSuperButtonPressed(int i) {
		if (this.superclicked[i] == false) {
			sl.getLogger().info("New Super-Button is pressed: " + view.superButton[i].getText());
			if (this.clicksSuper < this.superclicks) {
				view.superButton[i].setStyle("-fx-background-color: #800000;");
				this.superclicked[i] = true;
				model.userSuperTipp = Integer.parseInt(view.superButton[i].getText());
				this.clicksSuper++;
			}
		} else {
			sl.getLogger().info("This Super-Button is no longer valid: " + view.superButton[i].getText());
			view.superButton[i].setStyle(null);
			this.superclicked[i] = false;
			model.userSuperTipp = 0;
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

	public void setTextBox1(String input) {
		view.tbox1.setText(input);
	}
	public void setTextBox2(String input){
		view.tbox2.setText(input);
	}

	public void cleanUp() {
		sl.getLogger().info("Clean all up");
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
				view.tbox1.clear();
				view.tbox2.clear();
			}
		}
		view.superNumberButton.setVisible(false);
	}
	
	private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
        	sl.getLogger().info("File cannot open");
        }
    }
}
