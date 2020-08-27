package gobblets.ihm.controleur;


import gobblets.ihm.IHM;
import gobblets.ihm.langues.Allemand;
import gobblets.ihm.langues.Anglais;
import gobblets.ihm.langues.Francais;
import gobblets.ihm.vue.Confirmation;
import gobblets.ihm.vue.Dialogues;
import javafx.event.ActionEvent;


public class EcouteurMenus {

  /** Le contrôleur de l'application */
  private Controleur controleur;

  /** Affiche la boîte de dialogue A-Propos */
  public void onAPropos(ActionEvent actionEvent) {
    Dialogues.getAPropos().showAndWait();
  }
  public void onChangeLangueFR(ActionEvent actionEvent) {
	  IHM.setLanguage(new Francais());
	  controleur.changeLangue();
  }
  public void onChangeLangueEN(ActionEvent actionEvent) {
	  IHM.setLanguage(new Anglais());
	  controleur.changeLangue();
  }
  public void onChangeLangueGER(ActionEvent actionEvent) {
	  IHM.setLanguage(new Allemand());
	  controleur.changeLangue();
  }
  /** Quitte l'application */
  public void onQuitter(ActionEvent actionEvent) {
	  boolean confirmer =confirmationAvecEnregistrementEventuel("Quitter Gribouille");
	  if(confirmer) {
		  controleur.quitter();  
	  }
  }
  public void onNouveau(ActionEvent actionEvent) {
	  if(!controleur.isPeutEnregistrer()) {
		  controleur.nouveau();
		  return;
	  }
	  boolean confirmer =confirmationAvecEnregistrementEventuel("Quitter Gribouille");
	  if(confirmer) {
		  controleur.nouveau();  
	  }
  }
  public void onOuvrir(ActionEvent actionEvent) {
	  boolean confirmer=true;
	  if(controleur.isPeutEnregistrer()) {
		  confirmer =confirmationAvecEnregistrementEventuel("Quitter Gribouille");
	  }
	  if(confirmer) {
		  controleur.ouvrir();  
	  }
  }
  public void onEnregistrer(ActionEvent actionEvent) {
	  controleur.enregistrer();
  }
  public void onEnregistrerSous(ActionEvent actionEvent) {
	  controleur.enregistrerSous();
  }

  /** Choisit le contrôleur pour agir sur les outils */
  public void setControleur(Controleur controleur) {
    this.controleur = controleur;
  }

  public boolean confirmationAvecEnregistrementEventuel(String nomOP) {
	  Confirmation res = Dialogues.confirmation(nomOP);
	  if(res==Confirmation.QuitterEtEnregistrer) {
		  controleur.enregistrer();
		  return true;
	  }else if(res==Confirmation.QuitterSansEnregistrer) {
		  return true;
	  }
	  return false;
  }
} // public class EcouteurMenus
