package gobblets.ihm.vue;

import java.io.IOException;

import gobblets.ihm.IHM;
import gobblets.ihm.Menu;
import gobblets.ihm.controleur.EcouteurMenus;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

public class Panneau extends BorderPane{
	private Plateau plateau;
	private Maisons maisons;
	private EcouteurMenus ecouteurMenus;
	private BarreDEtat barreDEtat;
	private MenuBar menus;
	public Panneau() {
		// TODO Auto-generated constructor stub
		super();
		barreDEtat=new BarreDEtat();
		setBottom(barreDEtat);
		try {
			FXMLLoader loader = new FXMLLoader(Panneau.class.getResource("menu.fxml"));
			menus = loader.load();
			setTop(menus);
			ecouteurMenus = loader.getController();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void initialisePlateau(Plateau plateau, Maisons maisons) {
		this.plateau=plateau;
		this.maisons=maisons;
		this.getPlateau().setAlignment(Pos.CENTER);
		setCenter(plateau);
		this.maisons.setAlignment(Pos.CENTER);
		setRight(this.maisons);
		updateMenuItem();
	}
	public EcouteurMenus getEcouteurMenus() {
		// TODO Auto-generated method stub
		return ecouteurMenus;
	}
	public void updateMenuItem() {
		menus.getMenus().get(0).setText(IHM.menu(Menu.MENU_FICHIER));
		menus.getMenus().get(1).setText(IHM.menu(Menu.MENU_LANGUE));
		menus.getMenus().get(2).setText(IHM.menu(Menu.MENU_AIDE));
		menus.getMenus().get(0).getItems().get(0).setText(IHM.menu(Menu.MENU_NOUVEAU));
		menus.getMenus().get(0).getItems().get(1).setText(IHM.menu(Menu.MENU_OUVRIR));
		menus.getMenus().get(0).getItems().get(3).setText(IHM.menu(Menu.MENU_ENREGISTRER));
		menus.getMenus().get(0).getItems().get(4).setText(IHM.menu(Menu.MENU_ENREGISTRERSOUS));
		menus.getMenus().get(0).getItems().get(6).setText(IHM.menu(Menu.MENU_QUITTER));
		menus.getMenus().get(2).getItems().get(0).setText(IHM.menu(Menu.MENU_APROPOS));
	}

	public void updateGame(gobblets.data.Plateau plateauDeJeu) {
		// TODO Auto-generated method stub
		getPlateau().updatePlateau(plateauDeJeu);
	}

	public BarreDEtat getBarreDEtat() {
		return barreDEtat;
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		// TODO Auto-generated method stub
		this.plateau=plateau;
	}

}
