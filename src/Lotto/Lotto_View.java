package Lotto;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Lotto_View {
	
	private Lotto_Model model;
	private Stage stage;
	protected Button btnStart = new Button("Spielen");
	protected Button btnGetChance = new Button("My Chance");
	protected MenuItem newGame = new MenuItem("Restart");
	protected MenuItem closeGame = new MenuItem("Close");
	protected Button[][] regularButtons = new Button[model.LOTTOLENGTH][model.LOTTOHIGHT];
	protected Button[] superButton = new Button[model.LOTTOLENGTH];
	protected Label[] labelsForNumbers = new Label[model.LOTTOLENGTH];
	protected Label sLabel = new Label();
	protected Label superName = new Label("Die Superzahl ist");
	protected Button superNumberButton = new Button();
	protected Button[] lottoNumbersInButton = new Button[model.LOTTOLENGTH];
	protected TextArea tbox = new TextArea();

	public Lotto_View(Stage primaryStage, Lotto_Model model) {
		this.model = model;
		this.stage = primaryStage;
		
		stage.setTitle("***Lotto***");
	
		//MenuBar in the top
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuEdit = new Menu("Edit");
		Menu menuHelp = new Menu("Help");
		
		menuBar.getMenus().addAll(menuFile,menuEdit,menuHelp);
		menuFile.getItems().addAll(newGame,closeGame);
		menuBar.getStyleClass().add("menu");
		menuFile.getStyleClass().add("menu");
		menuEdit.getStyleClass().add("menu");
		menuHelp.getStyleClass().add("menu");
		newGame.getStyleClass().add("menu");
		closeGame.getStyleClass().add("menu");
		
		//GridPane in the center
		GridPane gPaneRegular = new GridPane();
		GridPane gPaneSuper = new GridPane();
		gPaneSuper.setPrefSize(100, 100);
		int c = 0;
		
		//Regular Numbers in the Lotto
		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				regularButtons[i][j] = new Button();
				regularButtons[i][j].getStyleClass().add("Buttons");
				regularButtons[i][j].setText(""+c);
				regularButtons[i][j].setPrefSize(50, 50);
				gPaneRegular.add(regularButtons[i][j], i, j);
				c++;
			}
		}
		//Super Numbers in the lotto
		c = 0;
		for (int i = 0; i < model.LOTTOLENGTH; i++){
			c++;
			superButton[i] = new Button();
			superButton[i].getStyleClass().add("Buttons");
			superButton[i].setText(""+c);
			superButton[i].setPrefSize(50, 50);
			gPaneSuper.add(superButton[i], i, 0);
		}
		
		//Empty Place1 (Super)
		HBox hboxEmpty = new HBox();
		Label superLabel = new Label("Super Zahlen:");
		superLabel.getStyleClass().add("labelSuperZahlen");
		hboxEmpty.setPrefSize(50, 50);
		hboxEmpty.getChildren().addAll(superLabel);
		
		//Empty Place2 (Top)
		HBox hboxEmpty2 = new HBox();
		hboxEmpty2.setPrefSize(100, 50);
		this.tbox.setDisable(true);
		this.tbox.setPrefSize(700,50);
		this.tbox.getStyleClass().add("text-area");
		hboxEmpty2.getChildren().add(tbox);
		
		//Empty Place2 (Left)
		VBox vboxEmpty3 = new VBox();
		vboxEmpty3.setPrefSize(50, 100);		
		
		//For adjustment the middle part
		BorderPane bPane = new BorderPane();
		bPane.setTop(gPaneRegular);
		bPane.setCenter(hboxEmpty);
		bPane.setBottom(gPaneSuper);
		
		//Buttons on bottom-line
		HBox hbox = new HBox();
		btnStart.setPrefSize(100, 50);
		btnStart.getStyleClass().add("bigButtons");
		btnGetChance.setPrefSize(100, 50);
		btnGetChance.getStyleClass().add("bigButtons");
		hbox.getChildren().addAll(btnStart,btnGetChance);
		
		//Numbers in a VBox on the right side
		VBox vboxNumbers = new VBox();
		Label nrTop = new Label("Die Zahlen sind:");
		nrTop.getStyleClass().add("nrTop");
		//nrTop.setStyle("-fx-font-size: 15pt; -fx-text-fill: red");
		vboxNumbers.getChildren().addAll(nrTop);
		
		//Fill Buttons for the Lotto-Numbers
		for(int i = 0; i < model.LOTTOLENGTH; i++){
			this.lottoNumbersInButton[i] = new Button();
			this.lottoNumbersInButton[i].setVisible(false);
			this.lottoNumbersInButton[i].getStyleClass().add("buttonLottoNumbers");
			this.lottoNumbersInButton[i].setPrefSize(80, 80);
			this.lottoNumbersInButton[i].setDisable(true);
			vboxNumbers.getChildren().add(this.lottoNumbersInButton[i]);
		}
		
		for(int i = 0; i < model.LOTTOLENGTH; i++){
			labelsForNumbers[i] = new Label();
			labelsForNumbers[i].setStyle("-fx-font-size: 10pt; -fx-text-fill: red");
			labelsForNumbers[i].setVisible(false);
			vboxNumbers.getChildren().addAll(labelsForNumbers[i]);
			
		}
		vboxNumbers.setPrefSize(250, 300);
		superName.getStyleClass().add("nrTop");
		superName.setVisible(false);
		Label separater1 = new Label();
		superNumberButton.getStyleClass().add("buttonLottoNumbers");
		superNumberButton.setPrefSize(100, 100);
		superNumberButton.setVisible(false);
		superNumberButton.setDisable(true);
		vboxNumbers.getChildren().addAll(superName, superNumberButton);
		
		//For the top in the top
		BorderPane topPane = new BorderPane();
		topPane.setTop(menuBar);
		topPane.setCenter(hboxEmpty2);
		
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
	public void stop(){
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

}
