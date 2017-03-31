package Lotto;

import java.util.Locale;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Lotto.ServiceLocator;
import Lotto.Translator;

public class Lotto_View {

	private Lotto_Model model;
	private Stage stage;
	protected Button btnStart;
	protected Button btnGetChance;
	protected MenuItem newGame;
	protected MenuItem closeGame;
	protected MenuItem documentation;
	protected Button[][] regularButtons;
	protected Button[] superButton;
	protected Label[] labelsForNumbers;
	protected Label sLabel;
	protected Label superName;
	protected Button superNumberButton;
	protected Button[] lottoNumbersInButton;
	protected TextField tbox1;
	protected TextField tbox2;
	protected TextField tcoupons;
	protected Label nrTop;
	protected Menu menuFileLanguage;
	protected Menu menuFile;
	protected Menu menuHelp;
	protected Label lchance;
	Label superLabel;

	public Lotto_View(Stage primaryStage, Lotto_Model model) {
		this.model = model;
		this.stage = primaryStage;
		
	    ServiceLocator sl = ServiceLocator.getServiceLocator();  
	    Translator t = sl.getTranslator();
		
		//Title
		stage.setTitle("***Lotto***");
		
		//MenuStrings
		menuFile = new Menu();
		menuHelp = new Menu();
		newGame = new MenuItem();
		closeGame = new MenuItem();
		documentation = new MenuItem();
		menuFileLanguage = new Menu();
		
		//Other Controls
		superName = new Label();
		nrTop = new Label();
		btnStart = new Button();
		btnGetChance = new Button();

		//Without Text to translate
		regularButtons = new Button[model.LOTTOLENGTH][model.LOTTOHIGHT];
		superButton = new Button[model.LOTTOLENGTH];
		labelsForNumbers = new Label[model.LOTTOLENGTH];
		sLabel = new Label();
		superNumberButton = new Button();
		lottoNumbersInButton = new Button[model.LOTTOLENGTH];
		tbox1 = new TextField();
		tbox2 = new TextField();
		tcoupons = new TextField();
		
		//Labels
		lchance = new Label();
		superLabel = new Label();
		superName = new Label();
		
		// MenuBar in the top
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menuFile, menuHelp);
		menuFile.getItems().addAll(newGame, closeGame, menuFileLanguage);
		menuHelp.getItems().add(documentation);
		menuBar.getStyleClass().add("menu");
		menuFile.getStyleClass().add("menu");
		menuHelp.getStyleClass().add("menu");
		newGame.getStyleClass().add("menu");
		closeGame.getStyleClass().add("menu");
		documentation.getStyleClass().add("menu");
		
		//set the Menu for the language
		 for (Locale locale : sl.getLocales()) {
	           MenuItem language = new MenuItem(locale.getLanguage());
	           menuFileLanguage.getItems().add(language);
	           language.setOnAction( event -> {
					sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
	                sl.setTranslator(new Translator(locale.getLanguage()));
	                updateTexts();
	            });
		 }
	

		// GridPane in the center
		GridPane gPaneRegular = new GridPane();
		GridPane gPaneSuper = new GridPane();
		gPaneSuper.setPrefSize(100, 100);
		int c = 1;

		// Regular Numbers in the Lotto
		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				regularButtons[i][j] = new Button();
				regularButtons[i][j].getStyleClass().add("Buttons");
				regularButtons[i][j].setText("" + c);
				regularButtons[i][j].setPrefSize(50, 50);
				gPaneRegular.add(regularButtons[i][j], i, j);
				c++;
			}
		}
			// Super Numbers in the lotto
			c = 0;
			for (int i = 0; i < model.LOTTOLENGTH; i++) {
				c++;
				superButton[i] = new Button();
				superButton[i].getStyleClass().add("Buttons");
				superButton[i].setText("" + c);
				superButton[i].setPrefSize(50, 50);
				gPaneSuper.add(superButton[i], i, 0);
			}

			// Empty Place1 (Super)
			HBox hboxEmpty = new HBox();
			superLabel.getStyleClass().add("labelSuperZahlen");
			hboxEmpty.setPrefSize(50, 50);
			hboxEmpty.getChildren().addAll(superLabel);

			// Empty Place2 (Top)
			VBox vboxEmpty2 = new VBox();
			vboxEmpty2.setPrefSize(100, 50);
			this.tbox1.setDisable(true);
			this.tbox2.setDisable(true);
			this.tbox1.setPrefSize(700, 20);
			this.tbox2.setPrefSize(700, 20);
			this.tbox1.getStyleClass().add("text-area");
			this.tbox2.getStyleClass().add("text-area");
			vboxEmpty2.getChildren().addAll(tbox1, tbox2);

			// Empty Place2 (Left)
			VBox vboxEmpty3 = new VBox();
			vboxEmpty3.setPrefSize(50, 100);

			// For adjustment the middle part
			BorderPane bPane = new BorderPane();
			bPane.setTop(gPaneRegular);
			bPane.setCenter(hboxEmpty);
			bPane.setBottom(gPaneSuper);

			// Buttons on bottom-line
			HBox hbox = new HBox();
			btnStart.setPrefSize(100, 50);
			btnStart.getStyleClass().add("bigButtons");
			btnGetChance.setPrefSize(100, 50);
			btnGetChance.getStyleClass().add("bigButtons");
			tcoupons.setPrefSize(200, 50);
			lchance.getStyleClass().add("nrTop");
			tcoupons.getStyleClass().add("text-area");
			hbox.getChildren().addAll(btnStart, btnGetChance, lchance, tcoupons);

			// Numbers in a VBox on the right side
			VBox vboxNumbers = new VBox();
			nrTop.getStyleClass().add("nrTop");
			nrTop.setVisible(false);
			vboxNumbers.getChildren().addAll(nrTop);

			// Fill Buttons for the Lotto-Numbers
			for (int i = 0; i < model.LOTTOLENGTH; i++) {
				this.lottoNumbersInButton[i] = new Button();
				this.lottoNumbersInButton[i].setVisible(false);
				this.lottoNumbersInButton[i].getStyleClass().add("buttonLottoNumbers");
				this.lottoNumbersInButton[i].setPrefSize(80, 80);
				this.lottoNumbersInButton[i].setDisable(true);
				vboxNumbers.getChildren().add(this.lottoNumbersInButton[i]);
			}

			for (int i = 0; i < model.LOTTOLENGTH; i++) {
				labelsForNumbers[i] = new Label();
				labelsForNumbers[i].setStyle("-fx-font-size: 10pt; -fx-text-fill: red");
				labelsForNumbers[i].setVisible(false);
				vboxNumbers.getChildren().addAll(labelsForNumbers[i]);

			}
			vboxNumbers.setPrefSize(250, 300);
			superName.getStyleClass().add("nrTop");
			superName.setVisible(false);
			superNumberButton.getStyleClass().add("buttonLottoNumbers");
			superNumberButton.setPrefSize(100, 100);
			superNumberButton.setVisible(false);
			superNumberButton.setDisable(true);
			vboxNumbers.getChildren().addAll(superName, superNumberButton);

			// For the top in the top
			BorderPane topPane = new BorderPane();
			topPane.setTop(menuBar);
			topPane.setCenter(vboxEmpty2);

			BorderPane root = new BorderPane();
			root.getStyleClass().add("pane");
			root.setPrefSize(700, 500);
			root.setRight(vboxNumbers);
			root.setLeft(vboxEmpty3);
			root.setTop(topPane);
			root.setCenter(bPane);
			root.setBottom(hbox);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
			primaryStage.setScene(scene);
			
			//set the language
			updateTexts();
		}

	/**
	 * Starting the view - make it visible
	 */
	public void start() {
		stage.show();

	}

	/**
	 * Stopping the view - make it invisible
	 */
	public void stop() {
		stage.hide();
	}

	/**
	 * Getter for the stage, so that the controller can access window events
	 * 
	 * @return stage itself
	 */
	public Stage getStage() {
		return stage;
	}

	protected void updateTexts() {
		
	    Translator t = ServiceLocator.getServiceLocator().getTranslator();
	    
	    //Title
	    stage.setTitle(t.getString("program.name"));

		// The menu entries
		newGame.setText(t.getString("program.menu.file.newGame"));
		menuFileLanguage.setText(t.getString("program.menu.file.language"));
		menuHelp.setText(t.getString("program.menu.help"));
		closeGame.setText(t.getString("program.menu.file.close"));
		documentation.setText(t.getString("program.menu.help.documentation"));
		menuFile.setText(t.getString("program.menu.file"));
		menuHelp.setText(t.getString("program.menu.help"));
		
		// Other controls
		nrTop.setText(t.getString("program.label.nrTop"));
		superName.setText(t.getString("program.label.superName"));
		btnStart.setText(t.getString("program.button.btnStart"));
		btnGetChance.setText(t.getString("program.button.btnChance"));		
		
		//Labels
		String chance = "         ";
		chance += t.getString("program.label.lchance");
		lchance.setText(chance);
		superLabel.setText(t.getString("program.label.superLabel"));
	}
}
