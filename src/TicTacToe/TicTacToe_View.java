package TicTacToe;

import java.util.Locale;

import Lotto.ServiceLocator;
import Lotto.Translator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	protected Menu menuFile;
	protected Menu menuHelp;
    protected Menu menuFileLanguage;

	protected Button btnComputer;
	protected TextField tbox;

	public TicTacToe_View(Stage primaryStage, TicTacToe_Model model) {
		this.stage = primaryStage;
		this.model = model;

		stage.setTitle("Tic Tac Toe");
		
		buttons = new Button[model.DIMENSION][model.DIMENSION];
		newGame = new MenuItem();
		closeGame = new MenuItem();
		documentation = new MenuItem();
		menuFile = new Menu();
		menuHelp = new Menu();
		btnComputer = new Button();
		tbox = new TextField();

		btnComputer.setPrefSize(240, 40);
		// btnComputer.setStyle("-fx-text-fill: #0000ff");
		btnComputer.getStyleClass().add("computerButton");

		// MenuBar in the topSplash_Model
		MenuBar menuBar = new MenuBar();
		menuBar.getStyleClass().add("menu");
		menuFile.getStyleClass().add("menu");
		menuHelp.getStyleClass().add("menu");
		newGame.getStyleClass().add("menu");
		closeGame.getStyleClass().add("menu");
		documentation.getStyleClass().add("menu");

		menuBar.getMenus().addAll(menuFile, menuHelp);
		menuFile.getItems().addAll(newGame, closeGame);
		menuHelp.getItems().add(documentation);
		
		
//	       for (Locale locale : ServiceLocator.getServiceLocator().getLocales()) {
//	           MenuItem language = new MenuItem(locale.getLanguage());
//	           menuFileLanguage.getItems().add(language);
//	           language.setOnAction( event -> {
//					ServiceLocator.getServiceLocator().getConfiguration().setLocalOption("Language", locale.getLanguage());
//					ServiceLocator.getServiceLocator().setTranslator(new Translator(locale.getLanguage()));
//	                updateTexts();
//	            });
//	       }

		// Hbox for the buttons on the bottomline
		HBox hbox = new HBox();
		hbox.getChildren().addAll(btnComputer);

		// Pane for the Buttom
		BorderPane bottomPane = new BorderPane();
		bottomPane.setTop(hbox);
		tbox.setPrefSize(20, 20);
		tbox.setDisable(true);
		tbox.getStyleClass().add("text-area");
		bottomPane.setCenter(tbox);

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
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setBottom(bottomPane);
		root.setCenter(pane);

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
		newGame.setText(t.getString("newGame"));
		closeGame.setText(t.getString("closeGame"));
		documentation.setText(t.getString("documentation"));
		menuFile.setText(t.getString("file"));
		menuHelp.setText(t.getString("help"));
		
		//Other controls
		btnComputer.setText(t.getString("computer"));

	}

}
