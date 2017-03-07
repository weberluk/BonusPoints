package TicTacToe;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TicTacToe_View {
	private TicTacToe_Model model;
	private Stage stage;
	protected Button[][] buttons = new Button[model.DIMENSION][model.DIMENSION];
	protected MenuItem newGame = new MenuItem("New Game");
	protected MenuItem closeGame = new MenuItem("Close");
	
	protected Button btnComputer = new Button("Computer");

	

	public TicTacToe_View(Stage primaryStage, TicTacToe_Model model) {
		this.stage = primaryStage;
		this.model = model;

		stage.setTitle("Tic Tac Toe");

		btnComputer.setPrefSize(240,40);
		btnComputer.setStyle("-fx-text-fill: #0000ff");

		//MenuBar in the top
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuEdit = new Menu("Edit");
		Menu menuHelp = new Menu("Help");
		
		menuBar.getMenus().addAll(menuFile,menuEdit,menuHelp);
		menuFile.getItems().addAll(newGame,closeGame);
		
		//Hbox for the buttons on the bottomline
		HBox hbox = new HBox();
		hbox.getChildren().addAll(btnComputer);

		//GridPane fill all Buttons in and show it in the center
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		for (int i = 0; i < model.DIMENSION; i++) {
			for (int j = 0; j < model.DIMENSION; j++) {
				buttons[i][j] = new Button();
				buttons[i][j].setId(Integer.toString(i));
				buttons[i][j].setPrefSize(80, 80);
				pane.add(buttons[i][j], i, j);
			}
		}
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setBottom(hbox);
		root.setCenter(pane);
		
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
	public void stop() {
		stage.hide();
	}
	
	/**
	 * Block the buttons after pressed
	 */
	public void block() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setDisable(true);
			}
		}
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
