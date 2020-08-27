package gobblets.ihm.vue;

import gobblets.logic.PiecePasDisponibleException;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Case extends Button{
	private gobblets.data.Case destination;
	public Case(gobblets.data.Case destination) {
		this.destination=destination;
		updateButton(destination);
	}
	public gobblets.data.Case getDestination() {
		return destination;
	}
	public void updateButton(gobblets.data.Case destination) {
		this.destination=destination;
		try {
			this.setText(String.valueOf(destination.plusGrandePiece().getTaillePiece().getSymbole()));
			switch (destination.plusGrandePiece().getCouleurPiece()) {
			case BLEU:
				setTextFill(Color.CORNFLOWERBLUE);
				break;
			case CYAN:
				setTextFill(Color.DARKCYAN);
				break;
			case JAUNE:
				setTextFill(Color.GOLD);
				break;
			case MAGENTA:
				setTextFill(Color.DARKMAGENTA);
				break;
			case ROUGE:
				setTextFill(Color.INDIANRED);
				break;
			case VERT:
				setTextFill(Color.GREEN);
				break;
			default:
				setTextFill(Color.TRANSPARENT);
				break;
			}
		} catch (PiecePasDisponibleException e) {
			this.setText("");
		}
	}
}
