package gui;

import java.io.File
import java.net.URL
import java.util.ResourceBundle

import javafx.application.Platform
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Cursor
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import library.ExpressionParser
import java.awt.Desktop
import robot_web.RobotWebCore

/**
 * Classe gérant toute la partie évenementielle de l'interface graphique
 */
class GuiController extends Initializable {
	
  /**
   * Définition chargement (à l'aide du @FXML) des composants de l'interface (noeuds)
   */
  
  /**
   * Le container de type VBox (Tous éléments placés à l'intérieur seront organisés à la verticale)
   */
  @FXML
  var GUI : VBox = null;
  
  /**
   * Un champs texte où sera saisie la requête
   */
	@FXML
	var RQ_TEXT_FIELD : TextField = null;
	
	/**
	 * Le bouton de recherche
	 */
	@FXML 
	var SEARCH_BUTTON : Button = null;
	
	/**
	 * Le label définissant le statut de l'application
	 */
	@FXML
	var STATE_LABEL : Label = null;
	
	/**
	 * Méthode appelée lors du lancement de l'interface s'occupant d'initialiser ce dernier 
	 */
	override def initialize(location : URL, resources : ResourceBundle ) = {
	  //On dit au Printer d'afficher sur l'interface au lieu de dans la console
	  Printer.activeGui = true;
	  //On définit dans une variable globale l'instance de GuiController de façon à pouvoir modifier le message de statut de l'extérieur de l'instance
	  GuiController.instance = this;
	  //On lie l'événement "Cliquer sur le bouton" à la fonction runSearch
		SEARCH_BUTTON.setOnMousePressed(e => runSearch());
	}

	/**Définit le message de statut de l'application
	 * @param message de statut le message a afficher 
	 */
  def setStateMessage(message: String) = {
    //Comme la recherche se fait sur un autre Thread, resynchronise la modification du message avec le Thread de l'interface
    Platform.runLater(new Runnable(){
      override def run() = STATE_LABEL.setText(message);
      });
  }

  /**
   * Lance la recherche sur un autre Thread
   */
  private def runSearch() = {
    //On parse l'expression de façon à récupérer l'arbre
    val p = ExpressionParser.LocalParser.parse(RQ_TEXT_FIELD.getText);
    //Si le parse a abouti
    if (p.successful) {
      //On change l'icône du curseur (sablier sur Windows...), on désactive le bouton de recherche puis on change le message de statut
      GUI.setCursor(Cursor.WAIT);
      SEARCH_BUTTON.setDisable(true);
      setStateMessage("Recherche en cours");
      //On crée une nouvelle tâche qui permettra l'exécution de la méthode call() sur un autre Thread (lancement de la recherche en arrière-plan)
      val task: Task[Unit] = new Task[Unit]() {
        override def call() = {
          //Lancement de la recherche
          RobotWebCore.runSearch(p.get);
          //Une fois celle-ci finie, on remet le curseur normal puis on réactive le bouton
          GUI.setCursor(Cursor.DEFAULT);
          SEARCH_BUTTON.setDisable(false);
          //On crée le fichier résultat
          val f = new File("Result/file.html");
          if (f.exists())
            Desktop.getDesktop().browse(f.toURI());
        }
      };
      //On lance le Thread (et donc la méthode call() )
      new Thread(task).start();
    } else {
      //Si le parse de la requête a échoué, on renvoie un message d'erreur
      setStateMessage("Requête invalide ! Exemple : (chat and chat) or perroquet");
    }
  }
}

object GuiController {
  var instance: GuiController = null;
}