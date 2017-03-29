package TicTacToe;

import java.util.Locale;

import Lotto.ServiceLocator;
import Lotto.Translator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TicTacToe_View {
	private TicTacToe_Model model;
	private Stage stage;
	protected Button[][] buttons;
	protected MenuItem newGame;
	protected MenuItem closeGame;
	protected MenuItem documentation;
	protected MenuItem changeUser;
	protected Menu menuFile;
	protected Menu menuHelp;
    protected Menu menuFileLanguage;
    protected Menu menuUser;

	protected Button btnComputer;
	protected TextField tbox;
	protected TextField points;
	protected TextField input;
	protected TextArea chat;
	protected Button btnSend;

	public TicTacToe_View(Stage primaryStage, TicTacToe_Model model) {
		this.stage = primaryStage;
		this.model = model;

		stage.setTitle("Tic Tac Toe");
		
		buttons = new Button[model.DIMENSION][model.DIMENSION];
		newGame = new MenuItem("new Game");
		closeGame = new MenuItem("Close Game");
		documentation = new MenuItem("Documentation");
		menuFile = new Menu("File");
		menuHelp = new Menu("Help");
		btnComputer = new Button("Computer");
		tbox = new TextField();
		points = new TextField();
		input = new TextField();
		chat = new TextArea();
		btnSend = new Button("Send");
		menuFileLanguage = new Menu("Language");
		menuUser = new Menu("User");
		changeUser = new MenuItem("Change User");

		btnComputer.setPrefSize(240, 40);
		btnSend.setPrefSize(200,40);
		// btnComputer.setStyle("-fx-text-fill: #0000ff");
		btnComputer.getStyleClass().add("computerButton");
		btnSend.getStyleClass().add("computerButton");

		// MenuBar in the topSplash_Model
		MenuBar menuBar = new MenuBar();
		menuBar.getStyleClass().add("menu");
		menuFile.getStyleClass().add("menu");
		menuHelp.getStyleClass().add("menu");
		newGame.getStyleClass().add("menu");
		changeUser.getStyleClass().add("menu");
		menuUser.getStyleClass().add("menu");
		closeGame.getStyleClass().add("menu");
		documentation.getStyleClass().add("menu");

		menuBar.getMenus().addAll(menuFile, menuHelp,menuUser);
		menuFile.getItems().addAll(newGame, closeGame,menuFileLanguage);
		menuHelp.getItems().add(documentation);
		menuUser.getItems().add(changeUser);

		// Hbox for the buttons on the bottomline
		HBox hbox = new HBox();
		hbox.getChildren().addAll(btnComputer,btnSend);

		// Pane for the Buttom
		BorderPane bottomPane = new BorderPane();
		bottomPane.setTop(hbox);
		tbox.setPrefSize(20, 20);
		tbox.setDisable(true);
		tbox.getStyleClass().add("text-area");
		bottomPane.setCenter(tbox);
		
		// Pane for the right Side
		BorderPane rightPane = new BorderPane();
		points.getStyleClass().add("text-point");
		points.setPrefSize(100, 20);
		points.setDisable(true);
		chat.getStyleClass().add("text-point");
		chat.setPrefSize(200, 100);
		chat.setDisable(false);
		chat.setPrefColumnCount(10);
		chat.setPrefRowCount(10);
		chat.setWrapText(true);
		input.setPrefSize(100, 20);
		rightPane.setBottom(input);
		rightPane.setCenter(chat);
		rightPane.setTop(points);
		

		// GridPane fill all Buttons in and show it in the center
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		for (int i = 0; i < model.DIMENSION; i++) {
			for (int j = 0; j < model.DIMENSION; j++) {
				buttons[i][j] = new Button();
				buttons[i][j].getStyleClass().add("buttons");
				buttons[i][j].setId(Integer.toString(i));
				buttons[i][j].setPrefSize(80, 80);
				pane.add(buttons[i][j], i, j);
			}
		}
		
	       for (Locale locale : ServiceLocator.getServiceLocator().getLocales()) {
	           MenuItem language = new MenuItem(locale.getLanguage());
	           menuFileLanguage.getItems().add(language);
	           language.setOnAction( event -> {
	        	    ServiceLocator sl = ServiceLocator.getServiceLocator();
					ServiceLocator.getServiceLocator().getConfiguration().setLocalOption("Language", locale.getLanguage());
					ServiceLocator.getServiceLocator().setTranslator(new Translator(locale.getLanguage()));
	                updateTexts();
	            });
	       }
	       
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setBottom(bottomPane);
		root.setCenter(pane);
		root.setRight(rightPane);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		primaryStage.setScene(scene);
		
		
		
		//updateTexts();
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

	protected void updateTexts() {

		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Title
		stage.setTitle(t.getString("program.name"));
		
		//Menu string
		newGame.setText(t.getString("program.menu.newGame"));
		closeGame.setText(t.getString("program.menu.closeGame"));
		documentation.setText(t.getString("program.menu.documentation"));
		menuFile.setText(t.getString("program.menu.file"));
		menuHelp.setText(t.getString("program.menu.help"));
		menuFileLanguage.setText(t.getString("program.menu.language"));
		menuUser.setText(t.getString("program.menu.user"));
		changeUser.setText(t.getString("program.menu.changeUser"));
		
		//Other controls
		btnComputer.setText(t.getString("program.button.computer"));
		btnSend.setText(t.getString("program.button.send"));
		

	}

}
