package SplashScreen_TicTacToe;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Splash_View {
	ProgressBar progressBar;
	private Label lblStatus;
    protected Stage stage;
    protected Scene scene;
    protected Splash_Model model;
	
    public Splash_View(Stage stage, Splash_Model model) {
        stage.initStyle(StageStyle.TRANSPARENT); 
        this.stage = stage;
        this.model = model;
        
        scene = create_GUI();
        stage.setScene(scene);
    }
    
    
    protected Scene create_GUI() {
        BorderPane root = new BorderPane();
        root.setId("splash");

        lblStatus = new Label("Loading");
        lblStatus.getStyleClass().add("label");
        root.setCenter(lblStatus);
        
        progressBar = new ProgressBar();
        HBox bottomBox = new HBox();
        bottomBox.setId("progressbox");
        bottomBox.getChildren().add(progressBar);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 300, 300, Color.TRANSPARENT);
        scene.getStylesheets().addAll(
                this.getClass().getResource("splash.css").toExternalForm());

        return scene;
    }
    
    public void start() {
        stage.show();
    }
    
    public void stop() {
        stage.hide();
    }
    
    public Stage getStage() {
        return stage;
    }
}
