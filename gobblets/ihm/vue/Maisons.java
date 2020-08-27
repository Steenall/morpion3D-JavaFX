package gobblets.ihm.vue;

import java.util.ArrayList;

import gobblets.data.Couleur;
import gobblets.data.Piece;
import gobblets.data.Taille;
import gobblets.ihm.Erreur;
import gobblets.ihm.IHM;
import gobblets.ihm.OtherText;
import gobblets.logic.Jeu;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Maisons extends GridPane {
	private Label maisonJ1;
	private Label maisonJ2;
	private Color coulJ1;
	private Color coulJ2;
	private boolean end;
	public static gobblets.ihm.vue.Piece selected;
	public static final Color select =Color.LIGHTBLUE;
	public static final Border selectBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), BorderStroke.THICK));
	public Maisons() {
		super();
		end=false;
		//this.autosize();
		maisonJ1 = new Label(IHM.other(OtherText.MaisonJoueur1));
		maisonJ1.setFont(new Font(18.0));
		add(maisonJ1, 0,0,6,1);
		GridPane.setHalignment(maisonJ1, HPos.CENTER);
		for(int i=0;i<6;i++) {
			gobblets.ihm.vue.Piece button = new gobblets.ihm.vue.Piece();
			button.setFont(new Font(30.0));
			button.setMinSize(30, 75);
			button.setStyle("-fx-background-color: transparent ;");
			button.setOnAction((event) -> onMousePressed(button));
		    add(button , i,1);
		}
		maisonJ2 = new Label(IHM.other(OtherText.MaisonJoueur2));
		maisonJ2.setFont(new Font(18.0));
		add(maisonJ2, 0,2,6,1);
		GridPane.setHalignment(maisonJ2, HPos.CENTER);
		for(int i=0;i<6;i++) {
			gobblets.ihm.vue.Piece button = new gobblets.ihm.vue.Piece();
			button.setStyle("-fx-background-color: transparent ;");
			button.setFont(new Font(30.0));
			button.setMinSize(25, 75);
			button.setOnAction((event) -> onMousePressed(button));
		    add(button , i,3);
		}
		for(int i=0;i<2;i++) {
			((Button)this.getChildren().get(1)).setText(String.valueOf(Taille.PETITE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(1)).setTaille(Taille.PETITE);
			((Button)this.getChildren().get(2)).setText(String.valueOf(Taille.PETITE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(2)).setTaille(Taille.PETITE);
			((Button)this.getChildren().get(8)).setText(String.valueOf(Taille.PETITE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(8)).setTaille(Taille.PETITE);
			((Button)this.getChildren().get(9)).setText(String.valueOf(Taille.PETITE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(9)).setTaille(Taille.PETITE);
		}
		for(int i=0;i<2;i++) {
			((Button)this.getChildren().get(3)).setText(String.valueOf(Taille.MOYENNE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(3)).setTaille(Taille.MOYENNE);
			((Button)this.getChildren().get(4)).setText(String.valueOf(Taille.MOYENNE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(4)).setTaille(Taille.MOYENNE);
			((Button)this.getChildren().get(10)).setText(String.valueOf(Taille.MOYENNE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(10)).setTaille(Taille.MOYENNE);
			((Button)this.getChildren().get(11)).setText(String.valueOf(Taille.MOYENNE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(11)).setTaille(Taille.MOYENNE);
		}
		for(int i=0;i<2;i++) {
			((Button)this.getChildren().get(5)).setText(String.valueOf(Taille.GRANDE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(5)).setTaille(Taille.GRANDE);
			((Button)this.getChildren().get(6)).setText(String.valueOf(Taille.GRANDE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(6)).setTaille(Taille.GRANDE);
			((Button)this.getChildren().get(12)).setText(String.valueOf(Taille.GRANDE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(12)).setTaille(Taille.GRANDE);
			((Button)this.getChildren().get(13)).setText(String.valueOf(Taille.GRANDE.getSymbole()));
			((gobblets.ihm.vue.Piece)this.getChildren().get(13)).setTaille(Taille.GRANDE);
		}
	}	
	private void onMousePressed(gobblets.ihm.vue.Piece button) {
		// TODO Auto-generated method stub
		if(end)return;
		int verifJoueur=getRowIndex(button);
		if((verifJoueur==1&&Jeu.getJoueurActif().equals(Jeu.getJ2()))||(verifJoueur==3&&Jeu.getJoueurActif().equals(Jeu.getJ1()))) {
			Dialogues.erreur(Erreur.PASTAPIECE);
			return;
		}
		if(selected!=null) {
			updateCouleur();
		}
		if(selected!=button) {
			updateCouleur();
			selected = button;
			button.setTextFill(select);
			IHM.getControleur().getPanneau().getBarreDEtat().afficheActionEnCours(2);
		}else {
			selected=null;
			IHM.getControleur().getPanneau().getBarreDEtat().afficheActionEnCours(1);
		}
	}
	public void updateText() {
		maisonJ1.setText(IHM.other(OtherText.MaisonJoueur1));
		maisonJ2.setText(IHM.other(OtherText.MaisonJoueur2));
	}
	public void updateMaison(ArrayList<Piece> maisonJoueur1, ArrayList<Piece> maisonJoueur2) {
		int j=0;
		for(Piece i :maisonJoueur1) {
			((Button)this.getChildren().get(j+1)).setText(String.valueOf(i.getTaillePiece().getSymbole()));
			((gobblets.ihm.vue.Piece) this.getChildren().get(j+1)).setTaille(i.getTaillePiece());
			j++;
		}
		while(j<6) {
			((Button)this.getChildren().get(j+1)).setText("   ");
			((Button)this.getChildren().get(j+1)).setDisable(true);
			((gobblets.ihm.vue.Piece) this.getChildren().get(j+1)).setTaille(null);
			j++;
		}
		j=0;
		for(Piece i :maisonJoueur2) {
			((Button)this.getChildren().get(j+8)).setText(String.valueOf(i.getTaillePiece().getSymbole()));
			((gobblets.ihm.vue.Piece) this.getChildren().get(j+8)).setTaille(i.getTaillePiece());
			j++;
		}
		while(j<6) {
			((Button)this.getChildren().get(j+8)).setText("   ");
			((Button)this.getChildren().get(j+8)).setDisable(true);
			((gobblets.ihm.vue.Piece) this.getChildren().get(j+8)).setTaille(null);
			j++;
		}
	}
	public void updateCouleur(){
		for(int i=0;i<6;i++) {
			((Button)this.getChildren().get(i+1)).setTextFill(coulJ1);
			((Button)this.getChildren().get(i+8)).setTextFill(coulJ2);
		}
	}
	public void updateCouleur(Couleur j1, Couleur j2) {
		switch (j1){
		case BLEU:
			coulJ1=Color.CORNFLOWERBLUE;
			break;
		case CYAN:
			coulJ1=Color.DARKCYAN;
			break;
		case JAUNE:
			coulJ1=Color.GOLD;
			break;
		case MAGENTA:
			coulJ1=Color.DARKMAGENTA;
			break;
		case ROUGE:
			coulJ1=Color.INDIANRED;
			break;
		case VERT:
			coulJ1=Color.GREEN;
			break;
		default:
			coulJ1=Color.TRANSPARENT;
			break;
		}
		switch (j2){
		case BLEU:
			coulJ2=Color.CORNFLOWERBLUE;
			break;
		case CYAN:
			coulJ2=Color.DARKCYAN;
			break;
		case JAUNE:
			coulJ2=Color.GOLD;
			break;
		case MAGENTA:
			coulJ2=Color.DARKMAGENTA;
			break;
		case ROUGE:
			coulJ2=Color.INDIANRED;
			break;
		case VERT:
			coulJ2=Color.GREEN;
			break;
		default:
			coulJ2=Color.TRANSPARENT;
			break;
		}
		updateCouleur();
	}
	public void enleveUnBouton(int numjoueur) {
		Button temp = null;
		//int emplacement =0;
		for(int i=0;i<6;i++) {
			if(!(this.getChildren().get(i+1+numjoueur*7)).isDisable()) {
				temp = (Button) this.getChildren().get(i+1+numjoueur*7);
			}
		}
		if(temp!=null) {
			temp.setDisable(true);
		}
	}
	public void end() {
		// TODO Auto-generated method stub
		end=!end;
	}
	public void changeJoueur(int i) {
		// TODO Auto-generated method stub
		if(i==1) {
			((Label) this.getChildren().get(0)).setBorder(selectBorder);
			((Label) this.getChildren().get(7)).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.NONE,new CornerRadii(0), BorderStroke.THICK)));
		}
		else if(i==2) {
			((Label) this.getChildren().get(7)).setBorder(selectBorder);
			((Label) this.getChildren().get(0)).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.NONE,new CornerRadii(0), BorderStroke.THICK)));
		}
	}
}
