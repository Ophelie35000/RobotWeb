package gui

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.layout.VBox
import javafx.scene.Scene
import javafx.fxml.FXMLLoader
import javafx.scene.image.Image


class Gui extends Application {
  
  /**
   * Affichage de l'interface
   */
	override def start(primaryStage: Stage) {
	  //Récupération et chargement du fichier Gui.fxml du dossier resources, fichier représentant la structure de l'application
		val loader: FXMLLoader = new FXMLLoader();
		val vBox: VBox = loader.load(getClass().getResourceAsStream("/GUI.fxml"));
		//Application de la feuille de style Gunivers.css sur l'interface de façon à changer la coloration
		vBox.getStylesheets.add(getClass().getResource("/Gunivers.css").toString());
		//Création de la scène et mise en place du container en son sein
		val scene: Scene = new Scene(vBox, 679, 201);
    primaryStage.setScene(scene);
    //Affichage du Stage
		primaryStage.show();
		vBox.requestFocus();
		//Définition du nom et du logo de l'application, puis interdiction du redimensionnement
		primaryStage.setTitle("Web Robot");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/search.png")));
    primaryStage.setResizable(false);
	}
}

object Gui {
  def main(args: Array[String]) {
    Application.launch(classOf[Gui], args: _*)
  }
}