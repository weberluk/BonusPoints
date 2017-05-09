package TicTacToe_Server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TicTacToe_Server_Controller {
	TicTacToe_Server model;
	TicTacToe_Server_View view;

	public TicTacToe_Server_Controller(TicTacToe_Server model, TicTacToe_Server_View view) {
		this.model = model;
		this.view = view;
		
		
        // register ourselves to listen for button clicks
        view.btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer port = new Integer(view.txtPort.getText());
                model.startServerSocket(port);
            }
        });
        
        view.txtLog.textProperty().addListener(e -> {
        	view.scrollPane.vvalueProperty().bind(view.scrollPane.heightProperty());
        });
	}

}
