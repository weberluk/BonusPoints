package Lotto;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
		
		//GridPane in the center
		GridPane gPaneRegular = new GridPane();
		GridPane gPaneSuper = new GridPane();
		int c = 0;
		
		//Regular Numbers in the Lotto
		for (int i = 0; i < model.LOTTOLENGTH; i++) {
			for (int j = 0; j < model.LOTTOHIGHT; j++) {
				c++;
				regularButtons[i][j] = new Button();
				regularButtons[i][j].setText(Integer.toString(c));
				regularButtons[i][j].setPrefSize(50, 50);
				gPaneRegular.add(regularButtons[i][j], i, j);
			}
		}
		//Super Numbers in the lotto
		c = 0;
		for (int i = 0; i < model.LOTTOLENGTH; i++){
			c++;
			superButton[i] = new Button();
			superButton[i].setText(Integer.toString(c));
			superButton[i].setPrefSize(50, 50);
			gPaneSuper.add(superButton[i], i, 0);
		}
		
		//Empty Place
		HBox hboxEmpty = new HBox();
		Label superLabel = new Label("Super Zahlen:");
		superLabel.setStyle("-fx-font-size: 25pt");
		hboxEmpty.setPrefSize(100, 50);
		hboxEmpty.getChildren().addAll(superLabel);
		
		
		//For adjustment the middle part
		BorderPane bPane = new BorderPane();
		bPane.setTop(gPaneRegular);
		bPane.setCenter(hboxEmpty);
		bPane.setBottom(gPaneSuper);
		
		//Buttons on bottom-line
		HBox hbox = new HBox();
		btnStart.setPrefSize(100, 50);
		btnGetChance.setPrefSize(100, 50);
		hbox.getChildren().addAll(btnStart,btnGetChance);
		
		//Numbers in a VBox on the right side
		VBox vboxNumbers = new VBox();
		Label nrTop = new Label("Die Zahlen sind:");
		nrTop.setStyle("-fx-font-size: 15pt; -fx-text-fill: red");
		vboxNumbers.getChildren().addAll(nrTop);
		
		for(int i = 0; i < model.LOTTOLENGTH; i++){
			labelsForNumbers[i] = new Label();
			labelsForNumbers[i].setStyle("-fx-font-size: 10pt; -fx-text-fill: red");
			labelsForNumbers[i].setVisible(false);
			vboxNumbers.getChildren().addAll(labelsForNumbers[i]);
			
		}
		sLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: red");
		sLabel.setVisible(false);
		vboxNumbers.setPrefSize(150, 200);
		superName.setStyle("-fx-font-size: 12pt; -fx-text-fill: red");
		superName.setVisible(false);
		Label separater1 = new Label();
		vboxNumbers.getChildren().addAll(separater1, superName,sLabel);
		
		BorderPane root = new BorderPane();
		root.setRight(vboxNumbers);
		root.setTop(menuBar);
		root.setCenter(bPane);
		root.setBottom(hbox);
		
		Scene scene = new Scene(root);
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
