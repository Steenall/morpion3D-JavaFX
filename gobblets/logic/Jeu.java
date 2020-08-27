package gobblets.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import gobblets.data.Couleur;
import gobblets.data.Etat;
import gobblets.data.Joueur;
import gobblets.data.JoueurIA;
import gobblets.data.Piece;
import gobblets.data.Plateau;
import gobblets.ihm.Erreur;
import gobblets.ihm.OtherText;
import gobblets.ihm.vue.Dialogues;
import gobblets.ihm.vue.Maisons;
import gobblets.interaction.Action;
import gobblets.interaction.Deplacement;
import gobblets.interaction.Placement;

/**
 * @author Evan Fregeais
 *
 */
public class Jeu {
	private Etat etat;
	private static Joueur joueurActif;
	private static Joueur j1;
	private static Joueur j2;
	private Plateau plateau;
	private int nbTours;
	private boolean finDuTour;
	public Jeu(boolean empty) {
		if(!empty)return;
		else {
			nbTours=0;
			etat=Etat.JEUENCOURS;
			finDuTour=false;
		}
		
	}
	public Jeu() {
		etat=Etat.JEUENCOURS;
		plateau= Plateau.initPlateau();
		finDuTour=false;
		nbTours=0;
		j1=Dialogues.saisirJoueur();
		j2=Dialogues.saisirJoueur();
		plateau=Plateau.initPlateau();
		for(int i=0;i<6;i++) {
			j1.ajoutePiece(plateau.getMaisonJoueur1().get(i));
			j2.ajoutePiece(plateau.getMaisonJoueur2().get(i));
			j1.getPieces().get(i).setCouleurPiece(j1.getCouleur());
			j2.getPieces().get(i).setCouleurPiece(j2.getCouleur());
			plateau.getMaisonJoueur1().get(i).setCouleurPiece(j1.getCouleur());
			plateau.getMaisonJoueur2().get(i).setCouleurPiece(j2.getCouleur());
		}
		int quiCommence = 0 + (int)(Math.random() * ((2 - 0)));
		if(quiCommence>=1) {
			joueurActif=j1;
		}else {
			joueurActif=j2;
		}
	}
	public Etat getEtat() {
		return etat;
	}
	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	public void unTour() {
		finDuTour=false;
		Action choix;
		try{
			if(joueurActif instanceof JoueurIA) {
				choix = joueurActif.choisirAction(plateau);
			}else {
				if(Maisons.selected!=null) {
					choix=new Placement(Maisons.selected.getTaille(), gobblets.ihm.vue.Plateau.selected.getDestination());
				}
				else {
					choix = new Deplacement(gobblets.ihm.vue.Plateau.selected.getDestination(), gobblets.ihm.vue.Plateau.selected2.getDestination());
				}
			}
			if(choix.verifier(joueurActif)) {
				choix.appliquer(joueurActif);
				finDuTour=true;
				if(choix instanceof Placement) {
					ArrayList<Piece> temp;
					if(joueurActif.equals(j1)) {
						temp = plateau.getMaisonJoueur1();
					}else {
						temp = plateau.getMaisonJoueur2();
					}
					for(Piece i : temp) {
						if(i.getTaillePiece().equals(((Placement) choix).getTaille())){
							temp.remove(i);
							break;
						}
					}
				}
			}
			else {
				if(choix instanceof Placement) {
					if(!joueurActif.aPieceDeTaille(((Placement) choix).getTaille())) {
						Dialogues.erreur(Erreur.PASDETAILLEDISPONIBLE);
					}else if(!((Placement) choix).getDestination().acceptePiece(((Placement) choix).getTaille())) {
						Dialogues.erreur(Erreur.CASEBLOQUEE);
					}
				}
				else if(choix instanceof Deplacement) {
					if(!((Deplacement) choix).getOrigin().plusGrandePiece().appartientA(joueurActif)) {
						Dialogues.erreur(Erreur.PASTAPIECE);
					}else if(!((Deplacement) choix).getDestination().acceptePiece(((Deplacement) choix).getOrigin().plusGrandePiece().getTaillePiece())) {
						Dialogues.erreur(Erreur.CASEBLOQUEE);
					}
				}
			}
			
		}catch(PiecePasDisponibleException e){
			Dialogues.erreur(e.getErreur());
		}catch(CaseBloqueeException e){
			Dialogues.erreur(e.getErreur());
		}
		int winJ1=0, winJ2=0;
		for(int i=0;i<3;i++) {
			Couleur tempL = plateau.verifierLigne(i);
			Couleur tempC = plateau.verifierColonne(i);
			Couleur tempDP= plateau.verifierDiagonale('P');
			Couleur tempDS= plateau.verifierDiagonale('S');
			if(tempL!=null) {
				if(j1.getCouleur().equals(tempL))winJ1++;
				else winJ2++;
			}
			if(tempC!=null) {
				if(j1.getCouleur().equals(tempC))winJ1++;
				else winJ2++;
			}
			if(tempDP!=null) {
				if(j1.getCouleur().equals(tempDP))winJ1++;
				else winJ2++;
			}
			if(tempDS!=null) {
				if(j1.getCouleur().equals(tempDS))winJ1++;
				else winJ2++;
			}
		}
		if(winJ1==winJ2&&winJ1>0)etat= Etat.MATCHNUL;
		else if(winJ1>winJ2)etat= Etat.JOUEUR1GAGNE;
		else if(winJ2>winJ1)etat= Etat.JOUEUR2GAGNE;
		else etat= Etat.JEUENCOURS;
		
	}
	public void changeJoueur() {
		if(joueurActif.equals(j2)) {
			joueurActif=j1;
		}else {
			joueurActif=j2;
		}
	}
	public Plateau getPlateau() {
		return plateau;
	}
	public static Joueur getJ1() {
		return j1;
	}
	public static Joueur getJ2() {
		return j2;
	}
	public static Joueur getJoueurActif() {
		return joueurActif;
	}
	/**
	 * Charge un fichier de sauvegarde
	 * @param nomFichier
	 */
	public void chargement(String nomFichier) {
		ObjectInputStream ois = null;
		try {
			final FileInputStream fichier = new FileInputStream(nomFichier);
			ois = new ObjectInputStream(fichier);
			plateau = (Plateau) ois.readObject();
			j1 = (Joueur) ois.readObject();
			j2 = (Joueur) ois.readObject();
			joueurActif = (Joueur) ois.readObject();
			etat = (Etat) ois.readObject();
			nbTours=(int) ois.readObject();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		/*play();
		afficheGagnant();*/
	}
	/**
	 * Sauvegarde la partie dans un fichier save_GobbletsGobblers.ser dans la racine du projet
	 * @throws IOException 
	 */
	public void sauvegarder() throws IOException {
		ObjectOutputStream oos = null;
		final FileOutputStream fichier = new FileOutputStream("save_GobbletsGobblers.ser");
		oos = new ObjectOutputStream(fichier);
		oos.writeObject(plateau);
		oos.writeObject(j1);
		oos.writeObject(j2);
		oos.writeObject(joueurActif);
		oos.writeObject(etat);
		oos.writeObject(nbTours);
		oos.flush();
		if (oos != null) {
			oos.flush();
			oos.close();
		}
		
	}
	/**
	 * Affiche le contenu de readme.md (le fichier est affiché partie par partie, c'est-à-dire qu'il faut appuyer sur entrée pour voir le texte dans une autre langue)
	 * Note: Il y a deux chemins possible pour le fichier, le premier si le projet est éxécuté sous Eclipse, le second dans un .jar
	 */
	public void afficheGagnant() {
		if(etat.equals(Etat.JOUEUR1GAGNE)) {
			Dialogues.other(OtherText.Congratulation,j1);
		}else if(etat.equals(Etat.JOUEUR2GAGNE)) {
			Dialogues.other(OtherText.Congratulation,j2);
		}else if(etat.equals(Etat.MATCHNUL)) {
			Dialogues.other(OtherText.MatchNul);
		}
	}
	public boolean chargement(File fichierACharger) {
		// TODO Auto-generated method stub
		ObjectInputStream ois = null;
		try {
			final FileInputStream fichier = new FileInputStream(fichierACharger);
			ois = new ObjectInputStream(fichier);
			plateau = (Plateau) ois.readObject();
			j1 = (Joueur) ois.readObject();
			j2 = (Joueur) ois.readObject();
			joueurActif = (Joueur) ois.readObject();
			etat = (Etat) ois.readObject();
			nbTours=(int) ois.readObject();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
			return false;
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}
	public boolean enregistreSous(File fichierAEnregistrer) {
		// TODO Auto-generated method stub
		ObjectOutputStream oos = null;
		FileOutputStream fichier;
		try {
			fichier = new FileOutputStream(fichierAEnregistrer);oos = new ObjectOutputStream(fichier);
			oos.writeObject(plateau);
			oos.writeObject(j1);
			oos.writeObject(j2);
			oos.writeObject(joueurActif);
			oos.writeObject(etat);
			oos.writeObject(nbTours);
			oos.flush();
			if (oos != null) {
				oos.flush();
				oos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public boolean getFinDuTour() {
		// TODO Auto-generated method stub
		return finDuTour;
	}
}
