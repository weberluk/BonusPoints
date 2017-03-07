package Lotto;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	protected Button btnStart = new Button("Start");
	protected Button btnGetChance = new Button("My Chance");
	protected MenuItem newGame = new MenuItem("Restart");
	protected MenuItem closeGame = new MenuItem("Close");

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
		GridPane gPane = new GridPane();
		gPane.setPrefSize(200, 200);
		
		//Buttons on bottomline
		HBox vbox = new HBox();
		vbox.getChildren().addAll(btnStart,btnGetChance);
		
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setCenter(gPane);
		root.setBottom(vbox);
		
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
