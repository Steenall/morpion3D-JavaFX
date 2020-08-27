package gobblets.ihm.vue;

import java.util.Optional;

import gobblets.data.Couleur;
import gobblets.data.Joueur;
import gobblets.data.JoueurHumain;
import gobblets.data.JoueurIA;
import gobblets.ihm.Avertissement;
import gobblets.ihm.Erreur;
import gobblets.ihm.IHM;
import gobblets.ihm.Menu;
import gobblets.ihm.OtherText;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/** Construit des boîte de dialogue.
 * @author Brutus Philippe et Jeanpierre Laurent
 */
public class Dialogues {
	private static int couleurBloquee=-1;
	private static String nomBloque="";
  /** Retourne une nouvelle boîte de dialogue à-propos. */
  public static Alert getAPropos() {
    Alert dialogue = new Alert(Alert.AlertType.INFORMATION);
    Label header = new Label("Gobblet's\nGobbler's");
    header.setStyle("-fx-font-size: 48pt; -fx-text-fill: orange; -fx-font-weight: bolder; -fx-text-alignment: center");
    dialogue.getDialogPane().setHeader(header);
    HBox content = new HBox(new Label("\n"+IHM.other(OtherText.Realiser)+"Evan Fregeais (21903855). ") );
    content.setAlignment(Pos.BOTTOM_CENTER);
    dialogue.getDialogPane().setContent(content);
    dialogue.setTitle(IHM.menu(Menu.MENU_APROPOS));
    return dialogue;
  }
  public static Confirmation confirmation(String titre) {
		Alert dialogue = new Alert(Alert.AlertType.CONFIRMATION);
		HBox content = new HBox(new Label("Des modifications ont été apportées au dessin sans être enregistrées.\n"
				+ "Cette opération fait perdre les modifications non enregistrées !\n"
				+ "Voulez-vous les enregistrer avant ?"));
		content.setAlignment(Pos.BOTTOM_CENTER);
	    dialogue.getDialogPane().setContent(content);
	    dialogue.getButtonTypes().add(ButtonType.NO);
	    dialogue.setTitle(titre);
		Optional<ButtonType> res = dialogue.showAndWait();
		if(res.isPresent()&&res.get()!=ButtonType.CANCEL) {
			if(res.get() ==ButtonType.OK) {
				return Confirmation.QuitterEtEnregistrer;
			}else if(res.get() ==ButtonType.NO){
				return Confirmation.QuitterSansEnregistrer;
			}
		}
		return Confirmation.Annuler;  
  }
  public static Joueur saisirJoueur() {
	  final Joueur joueur;
	  Stage popupwindow=new Stage();
	  popupwindow.setTitle("Saisir un joueur");
	  Label labelNom= new Label("Veuillez rentrer un nom");
	  labelNom.setFont(new Font(14.0));
	  Label labelCouleur = new Label("Veuillez saisir une couleur");
	  labelCouleur.setFont(new Font(14.0));
	  Button valider= new Button("Valider");
	  valider.setFont(new Font(16.0));
	  valider.setPadding(new Insets(2,15,2,15));
	  TextField fieldNom = new TextField ();
	  Label labelIA = new Label(IHM.other(OtherText.IA));
	  labelIA.setFont(new Font(14.0));
	  CheckBox isIA = new CheckBox();
	  ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList(
      		IHM.couleur(Couleur.BLEU),
      		IHM.couleur(Couleur.CYAN),
      		IHM.couleur(Couleur.JAUNE),
      		IHM.couleur(Couleur.MAGENTA),
      		IHM.couleur(Couleur.ROUGE),
      		IHM.couleur(Couleur.VERT)));
	  valider.setOnAction(e -> {
		  if(cb.getSelectionModel().getSelectedItem()!=null&&fieldNom.getText().isEmpty()==false&&!fieldNom.getText().equals(nomBloque)&&cb.getSelectionModel().getSelectedIndex()!=couleurBloquee) {
			  popupwindow.close();
		  }else {
			  Alert alert = new Alert(Alert.AlertType.ERROR);
			  if(cb.getSelectionModel().getSelectedItem()==null||cb.getSelectionModel().getSelectedIndex()==couleurBloquee) {
				  if(fieldNom.getText().isEmpty()||fieldNom.getText().equals(nomBloque))alert.setContentText(IHM.erreur(Erreur.COULEURINVALIDE)+"\n"+IHM.erreur(Erreur.NOMINVALIDE));
				  else alert.setContentText(IHM.erreur(Erreur.COULEURINVALIDE));
			  }else {
				  alert.setContentText(IHM.erreur(Erreur.NOMINVALIDE));
			  }
			  alert.showAndWait();
		  }
	  });
	  cb.setStyle("-fx-font-size: 12px ;");
	  fieldNom.setFont(new Font(12.0));
	  GridPane layout = new GridPane();
	  layout.setHgap(10); 
	  layout.setVgap(20); 
	  layout.add(labelNom,0,0);
	  layout.add(fieldNom,1,0);
	  layout.add(labelCouleur,0,1);
	  layout.add(cb,1,1);
	  layout.add(labelIA, 0, 2);
	  layout.add(isIA, 1, 2);
	  layout.add(valider,1,3);
	  layout.setAlignment(Pos.CENTER);
	  Scene scene1= new Scene(layout, 400, 200);
	  popupwindow.setScene(scene1);
	  popupwindow.setOnCloseRequest((event) ->{
		  event.consume();
	  });
	  popupwindow.showAndWait();
	  nomBloque=fieldNom.getText();
	  couleurBloquee=cb.getSelectionModel().getSelectedIndex();
	  Couleur temp = null;
	  switch (cb.getSelectionModel().getSelectedIndex()) {
	  	case 0:
	  		temp=Couleur.BLEU;
	  		break;
	  	case 1:
	  		temp=Couleur.CYAN;
	  		break;
	  	case 2:
	  		temp=Couleur.JAUNE;
	  		break;
	  	case 3:
	  		temp=Couleur.MAGENTA;
	  		break;
	  	case 4:
	  		temp=Couleur.ROUGE;
	  		break;
	  	default:
	  		temp=Couleur.VERT;
	  		break;
	  }
	  if(isIA.isSelected()) {
		  joueur=new JoueurIA(fieldNom.getText(),temp);
	  }else joueur=new JoueurHumain(fieldNom.getText(),temp);
	  return joueur;
  }
  public static void erreur(Erreur uneErreur) {
	  Alert temp = new Alert(Alert.AlertType.ERROR);
	  temp.setHeaderText(IHM.erreur(Erreur.ERREUR));
	  temp.setContentText(IHM.erreur(uneErreur));
	  temp.showAndWait();
  }
  public static void avertissement(Avertissement unAvertissement) {
	  Alert temp = new Alert(Alert.AlertType.WARNING);
	  temp.setHeaderText(IHM.avertissement(Avertissement.AVERTISSEMENT));
	  temp.setContentText(IHM.avertissement(unAvertissement));
	  temp.showAndWait();
  }
  public static void other(OtherText otherText) {
	  Alert temp = new Alert(Alert.AlertType.INFORMATION);
	  temp.setHeaderText(IHM.etat(IHM.getControleur().getJeu().getEtat()));
	  temp.setContentText(IHM.other(otherText));
	  temp.show();
  }
  public static void other(OtherText otherText,Joueur j) {
	  Alert temp = new Alert(Alert.AlertType.INFORMATION);
	  temp.setHeaderText(IHM.etat(IHM.getControleur().getJeu().getEtat()));
	  temp.setContentText(IHM.other(otherText)+j.getNom());
	  temp.show();
  }
  public static void supprimerCouleurBloquee() {
	  couleurBloquee=-1;
  }
  public static void supprimerNomBloque() {
	  nomBloque="";
  }
}
