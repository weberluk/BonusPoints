package Lotto;

public class Lotto_Controller {

	final private Lotto_View view;
	final private Lotto_Model model;

	public Lotto_Controller(Lotto_Model model, Lotto_View view) {
		this.view = view;
		this.model = model;

		view.btnStart.setOnAction((event) -> {
			model.fillLottoNumbers();
			
		});

		view.btnGetChance.setOnAction((event) -> {

		});

		view.newGame.setOnAction((event) -> {

		});

		view.closeGame.setOnAction((event) -> {
			view.stop();
		});

	}

	public void cleanUp() {

	}
}
