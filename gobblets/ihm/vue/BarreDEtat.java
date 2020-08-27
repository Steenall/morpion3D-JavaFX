package gobblets.ihm.vue;

import gobblets.data.Etat;
import gobblets.data.Joueur;
import gobblets.ihm.Avertissement;
import gobblets.ihm.IHM;
import gobblets.ihm.OtherText;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class BarreDEtat extends GridPane {
	private Label joueur;
	private Label actionEnCours;
	private Joueur unJoueur;
	private int typeAction=0;
	public BarreDEtat() {
		super();
		joueur=new Label();
		actionEnCours=new Label();
		add(joueur, 0, 0);
		add(actionEnCours, 1, 0);
		setAlignment(Pos.CENTER);
		for (int i=0; i<2; ++i) {
			ColumnConstraints constraints = new ColumnConstraints();
			constraints.setPercentWidth(50);
			constraints.setHalignment(HPos.CENTER);
			getColumnConstraints().add(constraints);
		}
	}
	public void updateLangue() {
		if(unJoueur!=null)afficheJoueur(unJoueur);
		if(typeAction!=0)afficheActionEnCours(typeAction);
	}
	public void afficheJoueur(Joueur unJoueur) {
		this.unJoueur=unJoueur;
		joueur.setText(IHM.avertissement(Avertissement.TONTOUR)+unJoueur.getNom());
		switch (unJoueur.getCouleur()) {
			case BLEU:
				joueur.setTextFill(Color.CORNFLOWERBLUE);
				break;
			case CYAN:
				joueur.setTextFill(Color.DARKCYAN);
				break;
			case JAUNE:
				joueur.setTextFill(Color.GOLD);
				break;
			case MAGENTA:
				joueur.setTextFill(Color.DARKMAGENTA);
				break;
			case ROUGE:
				joueur.setTextFill(Color.INDIANRED);
				break;
			case VERT:
				joueur.setTextFill(Color.GREEN);
				break;
			default:
				joueur.setTextFill(Color.TRANSPARENT);
				break;
		}
	}
	public void afficheActionEnCours(int typeAction) {
		this.typeAction=typeAction;
		if(typeAction==1)actionEnCours.setText(IHM.avertissement(Avertissement.CHOIXORIGIN));
		else if(typeAction==2)actionEnCours.setText(IHM.avertissement(Avertissement.CHOIXDESTINATION));
		else actionEnCours.setText("");
	}
	public void gagne(Joueur joueurActif) {
		if(joueurActif==null) {
			joueur.setText(IHM.etat(Etat.MATCHNUL));
			return;
		}
		joueur.setText(IHM.other(OtherText.Congratulation)+joueurActif.getNom());
		switch (joueurActif.getCouleur()) {
			case BLEU:
				joueur.setTextFill(Color.CORNFLOWERBLUE);
				break;
			case CYAN:
				joueur.setTextFill(Color.DARKCYAN);
				break;
			case JAUNE:
				joueur.setTextFill(Color.GOLD);
				break;
			case MAGENTA:
				joueur.setTextFill(Color.DARKMAGENTA);
				break;
			case ROUGE:
				joueur.setTextFill(Color.INDIANRED);
				break;
			case VERT:
				joueur.setTextFill(Color.GREEN);
				break;
			default:
				joueur.setTextFill(Color.TRANSPARENT);
				break;
	}
		actionEnCours.setText("");
	}

} // public class BarreDEtat
