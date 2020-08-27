package gobblets.data;

import gobblets.interaction.Action;

public class JoueurHumain extends Joueur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4402757012168990580L;

	public JoueurHumain(String nom, Couleur coul) {
		super(nom, coul);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action choisirAction(Plateau plat) {
		// TODO Auto-generated method stub
		return null;
	}
	public String toString() {
		return super.toString()+"Il s'agit d'un joueur humain";
	}
}
