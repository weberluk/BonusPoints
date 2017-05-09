package TicTacToe_Server;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TicTacToe_Server_View {
	private TicTacToe_Server model;
	private Stage stage;
	
    protected Label lblPort = new Label("Port ");
    protected TextField txtPort = new TextField("5100");
    protected Button btnGo = new Button("Go");
    protected TextArea txtLog = new TextArea();
    protected ScrollPane scrollPane;

	public TicTacToe_Server_View(Stage primaryStage, TicTacToe_Server model, TextArea textArea) {
		this.model = model;
		this.stage = primaryStage;
		this.txtLog = textArea;
		
		stage.setTitle("Tic Tac Toe Server");
		
        BorderPane root = new BorderPane();
		
        HBox topBox = new HBox();
        topBox.setId("TopBox");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        root.setTop(topBox);
        
        topBox.getChildren().addAll(lblPort, txtPort, spacer, btnGo);
        txtPort.setId("Port");
        btnGo.getStyleClass().add("bigButtons");
        btnGo.setPrefSize(50, 50);
        txtPort.getStyleClass().add("text-area");
        lblPort.getStyleClass().add("label");
        txtLog.getStyleClass().add("text-areaLog");
        txtLog.setWrapText(true);
        
        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
        scrollPane.setContent(txtLog);
        
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		primaryStage.setScene(scene);
	}

	public void start() {
		stage.show();
		
	}

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

}
