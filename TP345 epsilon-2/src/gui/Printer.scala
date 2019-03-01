package gui

/**
 * Singleton gérant l'affichage des messages en fonction de la présence de l'interface
 */
object Printer {
  
  /**
   * Si l'interface est actif
   */
  var activeGui = false;
  
  /**
   * Affichage de string dans la console si l'affichage est désactivé ou en tant que message de Statut sinon
   * @param string le string à afficher
   */
  def print(string: String) = {
     activeGui match {
       case false => println(string);
       case true  => GuiController.instance.setStateMessage(string);
     }
  }
  
}