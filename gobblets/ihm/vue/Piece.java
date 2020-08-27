package gobblets.ihm.vue;

import gobblets.data.Taille;
import javafx.scene.control.Button;

public class Piece extends Button{
	private Taille taille;
	public Piece() {
		super();
	}
	public void setTaille(Taille taille){
		this.taille=taille;
	}
	public Taille getTaille() {
		return taille;
	}
}
