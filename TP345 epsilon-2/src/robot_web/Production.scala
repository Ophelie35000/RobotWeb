package robot_web

import library._

object Production extends ProductionResultat {
  
  /**
   *	Permet de générer un AST HTML contenant les liens et leur titres, indiqué dans l.
   * 	Le nombre de résultat est indiqué dans le titre de la page. Le temps pris est également affiché.
   *  Si l est vide, le corps de l'arbre HTML n'affiche que "Aucun résultat trouvé".
   * 
   *  @param l Liste de couple de string
   *  @return un objet HTML, contenant les liens indiqué dans l
   */
  
  def resultat2html(l: List[(String, String)]): Html = {
    
    var results: List[Html] = List()
    
    l match {
      case Nil => results = Text("Aucun résultat trouvé.")::results
      case _ => for((title, url) <- l) {
          results = Tag("a", List(("href",url)), List(Text(title), Tag("br", List(), List())))::results
        }
    }
    
    val head: Html = Tag("head", List(), List(
        Tag("meta", List(("content","text/html"),("charset","UTF-8")),List()),
        Tag("link", List(("rel", "stylesheet"), ("type", "text/css"), ("href", "../resources/style.css")), List()),
        Tag("title",List(),List(Text("Robot web : "+l.length+" résultats")))))

    
    val body: Html = Tag("body",List(),List(
        Tag("h1", List() , List(Text(ExptoString(RobotWebCore.expression)))), // récupère l'expression originale, convertie en String
        Text("&nbsp"),
        Tag("center",List(),results),
        Tag("p", List(), List(Text("La recherche a abouti en " + RobotWebCore.time + "s")))))  // récupère le temps pris par le robot
  
    Tag("html",List(),List(head, body))    
  }
  
  /**Transforme l'expression en String, afin de pouvoir l'afficher
   * @param e une expression
   */
  private def ExptoString(e : Expression) : String = {
    e match {
      case Word(s) => s
      case And(e1,e2) => " (" + ExptoString(e1) + " and " + ExptoString(e2) +") "
      case Or(e1,e2) =>  " (" + ExptoString(e1) + " or " + ExptoString(e2) +") "
    }
  }
}
