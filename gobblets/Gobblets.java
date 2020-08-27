package gobblets;

import gobblets.ihm.IHM;
import gobblets.ihm.controleur.Controleur;
import gobblets.ihm.langues.Francais;
import gobblets.ihm.vue.Panneau;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gobblets extends Application {
	public static void main(String[] args) {
		IHM.setLanguage(new Francais());
		Application.launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
	    Panneau panneau = new Panneau();
	    Scene scene = new Scene(panneau);
	    IHM.setControleur(new Controleur(panneau));
	    stage.setScene(scene);
	    stage.setMinWidth(700);
	    stage.setMinHeight(400);
	    stage.setTitle("Gobblet's Gobbler's");
	    stage.show();
	    stage.setOnCloseRequest((event) ->{
	    	if(!IHM.getControleur().isPeutEnregistrer())System.exit(0);
	    	if(panneau.getEcouteurMenus().confirmationAvecEnregistrementEventuel("Quitter Gobblet's Gobbler's")) {
	    		System.exit(0);
	    	}else event.consume();
		}
		);
	}

}
