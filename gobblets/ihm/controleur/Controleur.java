package gobblets.ihm.controleur;

import java.io.File;

import gobblets.data.Etat;
import gobblets.data.Joueur;
import gobblets.data.JoueurIA;
import gobblets.ihm.Erreur;
import gobblets.ihm.IHM;
import gobblets.ihm.Menu;
import gobblets.ihm.OtherText;
import gobblets.ihm.vue.Dialogues;
import gobblets.ihm.vue.Maisons;
import gobblets.ihm.vue.Panneau;
import gobblets.ihm.vue.Plateau;
import gobblets.logic.Jeu;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/** Centralise le contrôle de Gribouille.
 * @author Brutus Philippe et Jeanpierre Laurent
 */
public class Controleur {
  private Panneau panneau;
  private Maisons maisons;
  private Jeu jeu;
  private File enregistrer;
  private boolean peutEnregistrer;

  /** L'écouteur menu */
  private EcouteurMenus ecouteurMenus;
  public EcouteurMenus getEcouteurMenus() {
	  return ecouteurMenus;
  }
  public Controleur(Panneau panneau) {
    this.panneau = panneau;
    ecouteurMenus = panneau.getEcouteurMenus();
    ecouteurMenus.setControleur(this);
    peutEnregistrer=false;
  }
  public void quitter() {
      System.exit(0);
  }
  public void enregistrerSous() {
	// TODO Auto-generated method stub
	  if(!isPeutEnregistrer()) {
		  Dialogues.erreur(Erreur.SAVE);
		  return;
	  }
	  FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Enregistrer une sauvegarde");
	    fileChooser.getExtensionFilters().addAll(
	        new ExtensionFilter("Fichiers de sauvegarde Gobblet's Gobbler's", "*.ser"),
	        new ExtensionFilter("Tous fichiers", "*.*"));
	    File fichier = fileChooser.showSaveDialog(null);
	    if (fichier != null) {
	      if (! jeu.enregistreSous(fichier)) {
	        Alert erreur = new Alert(Alert.AlertType.ERROR);
	        erreur.setTitle("Enregistrement d'un dessin");
	        erreur.setHeaderText(null);
	        erreur.setContentText(String.format(IHM.erreur(Erreur.SAVE)+" %s !", fichier.getAbsolutePath()));
	        erreur.setResizable(true);
	        erreur.showAndWait();
	      }else enregistrer=fichier;
	    }
  }
  public void ouvrir() {
	// TODO Auto-generated method stub
	  FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle(IHM.menu(Menu.MENU_OUVRIR));
      fileChooser.getExtensionFilters().addAll(
          new ExtensionFilter(IHM.other(OtherText.SAVEFILE),"*.ser"),
          new ExtensionFilter(IHM.other(OtherText.ALLFILE),"*.*"));
      File fichier = fileChooser.showOpenDialog(null);
      jeu=new Jeu(true);
      if (fichier != null) {
        if (!jeu.chargement(fichier)) {
          Alert erreur = new Alert(Alert.AlertType.ERROR);
          erreur.setHeaderText(null);
          erreur.setContentText(String.format(IHM.erreur(Erreur.SAVE)+" %s !", fichier.getAbsolutePath()));
          erreur.setResizable(true);
          erreur.showAndWait();
        }
        else {
        	enregistrer=fichier;
      	  getPanneau().getBarreDEtat().afficheActionEnCours(1);
      	  maisons = new Maisons();
    	  maisons.updateCouleur(Jeu.getJ1().getCouleur(), Jeu.getJ2().getCouleur());
    	  maisons.updateMaison(jeu.getPlateau().getMaisonJoueur1(), jeu.getPlateau().getMaisonJoueur1());
    	  updateJoueur(Jeu.getJoueurActif());
    	  getPanneau().initialisePlateau(new Plateau(jeu.getPlateau()), maisons);
    	  peutEnregistrer=true;
    	  if(Jeu.getJoueurActif() instanceof JoueurIA)unTour();
		  if(Jeu.getJoueurActif().equals(Jeu.getJ1()))maisons.changeJoueur(1);
		  else maisons.changeJoueur(2);
        }
      }
  }
  public void nouveau() {
	// TODO Auto-generated method stub
	  jeu=new Jeu();
	  Dialogues.supprimerCouleurBloquee();
	  Dialogues.supprimerNomBloque();
	  maisons = new Maisons();
	  maisons.updateCouleur(Jeu.getJ1().getCouleur(), Jeu.getJ2().getCouleur());
	  updateJoueur(Jeu.getJoueurActif());
	  getPanneau().initialisePlateau(new Plateau(jeu.getPlateau()), maisons);
	  peutEnregistrer=true;
	  getPanneau().getBarreDEtat().afficheActionEnCours(1);
	  if(Jeu.getJoueurActif() instanceof JoueurIA)unTour();
	  if(Jeu.getJoueurActif().equals(Jeu.getJ1()))maisons.changeJoueur(1);
	  else maisons.changeJoueur(2);
  }
  public void changeLangue() {
	  getPanneau().updateMenuItem();
	  if(jeu.getEtat()==Etat.JEUENCOURS)getPanneau().getBarreDEtat().updateLangue();
	  else {
		  Joueur temp=null;
		  if(jeu.getEtat()==Etat.JOUEUR1GAGNE)temp=Jeu.getJ1();
		  else if(jeu.getEtat()==Etat.JOUEUR2GAGNE)temp=Jeu.getJ2();
		  getPanneau().getBarreDEtat().gagne(temp);
	  }
	  if(maisons!=null)maisons.updateText();
  }
  public void unTour() {
	  jeu.unTour();
	  boolean end=false;
	  if(jeu.getFinDuTour()) {
		  jeu.changeJoueur();
		  if(Jeu.getJoueurActif().equals(Jeu.getJ1()))maisons.changeJoueur(1);
		  else maisons.changeJoueur(2);
		  getPanneau().getBarreDEtat().afficheJoueur(Jeu.getJoueurActif());
		  if(Maisons.selected!=null) {
			  if(Jeu.getJoueurActif().equals(Jeu.getJ1())) {
				  maisons.enleveUnBouton(1);
			  }else {
				  maisons.enleveUnBouton(0);
			  }
		  }
		  if(jeu.getEtat()!=Etat.JEUENCOURS) {
			  end =true;
			  peutEnregistrer=false;
		  }
	  }
	  getPanneau().getBarreDEtat().afficheActionEnCours(1);
	  if(Plateau.selected!=null)Plateau.selected.setBorder(Plateau.unselect);
	  maisons.updateCouleur();
	  if(Plateau.selected2!=null)Plateau.selected2.setBorder(Plateau.unselect);
	  Maisons.selected=null;
	  Plateau.selected=null;
	  Plateau.selected2=null;
	  getPanneau().updateGame(jeu.getPlateau());
	  maisons.updateMaison(jeu.getPlateau().getMaisonJoueur1(), jeu.getPlateau().getMaisonJoueur2());
	  if(end) {
		  panneau.getPlateau().end();
		  maisons.end();
		  jeu.changeJoueur();
		  if(Jeu.getJoueurActif().equals(Jeu.getJ1()))maisons.changeJoueur(1);
		  else maisons.changeJoueur(2);
		  panneau.getBarreDEtat().gagne(Jeu.getJoueurActif());
		  jeu.afficheGagnant();
		  return;
	  }
	  else if(Jeu.getJoueurActif() instanceof JoueurIA)unTour();
	  
  }
	public void updateJoueur(Joueur j) {
		getPanneau().getBarreDEtat().afficheJoueur(j);
	}
	public boolean isPeutEnregistrer() {
		return peutEnregistrer;
	}
	public void enregistrer() {
		// TODO Auto-generated method stub
		  if(!isPeutEnregistrer()) {
			  Dialogues.erreur(Erreur.SAVE);
			  return;
		  }
		  if (enregistrer==null) {
		      enregistrerSous();
		    } else {
		    	if (! jeu.enregistreSous(enregistrer)) {
		    		Alert erreur = new Alert(Alert.AlertType.ERROR);
		  	        erreur.setTitle("Enregistrement d'un dessin");
		  	        erreur.setHeaderText(null);
		  	        erreur.setContentText(String.format(IHM.erreur(Erreur.SAVE)+" %s !", enregistrer.getAbsolutePath()));
		  	        erreur.setResizable(true);
		  	        erreur.showAndWait();
		  	      }
		    }
	}
	public Panneau getPanneau() {
		return panneau;
	}
	public Jeu getJeu() {
		// TODO Auto-generated method stub
		return jeu;
	}

} // public class Controleur
