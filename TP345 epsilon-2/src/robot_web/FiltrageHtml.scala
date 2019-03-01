package robot_web

import library._

object FiltrageHTMLImpl extends FiltrageHtml {

  private val matchAttributs = "kiwii-padding-top-xxsmall kiwii-padding-left-large kiwii-padding-right-medium";
  private var text : String = "";

  def filtreHtml(h: Html, e: Expression): Boolean = {
    browsePage(h, (s: String) => ());
    compareStringWithExpression(text, e);
  }
    
    /**
     * Parcours l'arbre Html à la recherche d'une sous-chaîne correspondante à l'Expression et la concatène à text.
     * <br/>La recherche se centre sur la description et le titre de l'annonce.
     * @param h un arbre Html
     * @param e une  Expression à retrouver dans l'arbre
     * @param function une fonction prenant un String et ne renvoyant rien à appliquer sur les Html Text trouvés
     */
    private def browsePage(h : Html, function : String => Unit) = {
      h match {
        case Text(s) => function(s);
        case Tag("div", attributs, children) => browseList(children, if (browseAttributs(attributs, "class", matchAttributs)) (s : String) => (text = (text + '\n' + s)) else function);
        case Tag(_, _, children) => browseList(children, function);
      }
    }
    
    /**
     * Parcours une liste de Html et exécute browsePage sur chacun des éléments
     * @param list une liste de Html
     * @param e une  Expression à retrouver dans l'arbre
     * @param function une fonction prenant un String et ne renvoyant rien à appliquer sur les Html Text trouvés
     */
    private def browseList(list : List[Html], function : String => Unit) : Unit = {
      list match {
        case Nil => ();
        case x :: y => browsePage(x, function); browseList(y, function);
      }
    }

    /**
     * Cherche le couple ```key``` ```value``` dans la liste
     * @param list une liste de couple (String, String)
     * @param e une  Expression à retrouver dans l'arbre
     * @return true si le couple est trouvé est trouvé, false sinon
     */
    /*private*/ def browseAttributs(list : List[(String, String)], key : String, value : String) : Boolean =
      list.contains((key, value));
  
    //TODO gérer le cas d'un mot inclus dans un autre
    /**
     * Cherche l'Expression dans la chaîne de caractère donnée
     * @param str une chaîne de caractères
     * @param e une Expression à chercher
     * @return true si une sous-chaîne de str corresponds à l'expression 
     */
  /* private */ def compareStringWithExpression(str : String, e : Expression) : Boolean = {
        e match {
          case Word(s) => str.toLowerCase.contains(s.toLowerCase); 
          case Or(e1, e2) => compareStringWithExpression(str, e1) || compareStringWithExpression(str, e2);
          case And(e1, e2) => compareStringWithExpression(str, e1) && compareStringWithExpression(str, e2);
        }
    }
  
}