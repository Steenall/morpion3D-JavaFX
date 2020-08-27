package gobblets.ihm.vue;

import gobblets.ihm.IHM;
import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Plateau extends GridPane {
	public static final Border select = new Border(new BorderStroke(Color.LIMEGREEN, BorderStrokeStyle.SOLID,new CornerRadii(0), BorderStroke.THICK));
	public static final Border unselect = new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID,new CornerRadii(0), BorderStroke.MEDIUM));
	public static Case selected;
	public static Case selected2;
	private boolean end;
	private gobblets.data.Plateau plateau;
	public Plateau(gobblets.data.Plateau plateau) {
		end=false;
		this.plateau = plateau;
		setPadding(new Insets(30, 30, 30, 30));
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				Case button = new Case(plateau.getPlateau()[j][i]);
				button.setMinSize(75, 75);
				button.setFont(new Font(30.0));
				button.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID,new CornerRadii(0), BorderStroke.MEDIUM)));
				//button.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> onMousePressed(evt));
				button.setOnAction((event) -> onMousePressed(button));
			    add(button , i,j);
			}
		}
	}
	public void updatePlateau(gobblets.data.Plateau plateau) {
		this.plateau=plateau;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				((Case) getChildren().get(i*3+j)).updateButton(this.plateau.getPlateau()[j][i]);
			}
		}
	}
	private void onMousePressed(Case button) {
		// TODO Auto-generated method stub
		if(end)return;
		button.setBorder(select);
		if(selected !=null) {
			if(selected==button) {
				selected.setBorder(unselect);
				selected=null;
				IHM.getControleur().getPanneau().getBarreDEtat().afficheActionEnCours(1);
			}
			else {
				selected2=button;
				IHM.getControleur().unTour();
			}
		}else {
			selected=button;
			if(Maisons.selected!=null)IHM.getControleur().unTour();
			else IHM.getControleur().getPanneau().getBarreDEtat().afficheActionEnCours(2);
		}
	}
	public void end() {
		// TODO Auto-generated method stub
		end=!end;
	}
}
