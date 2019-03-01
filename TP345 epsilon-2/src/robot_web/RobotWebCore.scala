package robot_web
import library._
import java.io._
import gui.Printer

object RobotWebCore extends App {
  

  /**
   * File, représente objet qui permettra d'écrire dans un fichier txt/html
   */
  var file = new FileWriter("Result/file.html")
  
  var lastRequest = "";
  
  /**
   * Expression récupéré suite a une entrée clavier
   */
   var time = ""
   var expression: Expression = null;
   if(!Printer.activeGui)
     runSearch(ExpressionParser.readExp)
  
  
  
  
  
  /**
   * @param e, expression
   * @return liste de string, correpondant aux expressions
   * 				 Exemple : ExptoString(moto and voiture) va renvoyer "moto+voiture"
   */
  
  /*private*/ def ExptoString(e : Expression) : List[String] = {
    var l = List()
    e match {
      case Word(s) => s :: l
      case And(Or(e1,e2),Or(e3,e4)) => ExptoString(And(e1,e3)) ++ ExptoString(And(e1,e4)) ++ ExptoString(And(e2,e3)) ++ ExptoString(And(e2,e4)) ++ l
      case And(Or(e1,e2),e3) => ExptoString(And(e1,e3)) ++ ExptoString(And(e2,e3)) ++ l
      case And(e3 , Or(e1,e2)) => ExptoString(And(e1,e3)) ++ ExptoString(And(e2,e3)) ++ l
      case And(e1,e2) => ExptoString(e1).head+ "+" + ExptoString(e2).head :: l 
      case Or(e1,e2) => ExptoString(e1) ++ ExptoString(e2) ++ l

    }
  }
  
  /** version 1 (Contruction de la liste avec une boucle)
 	* @param ls, liste de string (correspondant à des expressions de recherche)
 	* @return Liste de String, qui est liste d'URL
 	*/
  /*private*/ def createUrls(ls : List[String]) : List[String] = {
    var finalList : List[String] = List()
    for (s <- ls)
    {
      finalList = "https://search.vivastreet.com/annonces/fr?lb=new&search=1&start_field=1&keywords="+s+"&cat_1=&geosearch_text=&searchGeoId=0" :: finalList
    }
    finalList
   }
  
  /**version 2 (Utilisation des fonctions de collections)
   * @param ls, liste de string (correspondant à des expressions de recherche)
	 * @return Liste de String, qui est liste d'URL
 	 */
  /*private*/ def createUrls2(ls : List[String]) : List[String] = {
    ls.map(s => "https://search.vivastreet.com/annonces/fr?lb=new&search=1&start_field=1&keywords="+s+"&cat_1=&geosearch_text=&searchGeoId=0")
  }
  
  /*private*/ def runSearch(e : Expression) = { 
    
    file = new FileWriter("Result/file.html");
    expression = e;
    //println(e);
  
    val startTime = System.nanoTime()
    
    /**
     * Liste de String obtenu 
     */
    var stringExp = ExptoString(e)
   
    /**
     * List de String contenant les URL obtenus à partir de nos expressions rentrées lors de la recherche
     */
    var urlsList = createUrls2(stringExp)
    
    /**
     * Création de la liste de couple par une liste de String (Nous avons que les URl dans le couple (titre,url))
     */
    var titleUrls : List[(String,String)] = List()
    
    /**
     * Remplissage de la liste avec les couple (titres(recupéré grace a analyseur page), URL)
     */
    for (tu <- urlsList) {
      titleUrls = AnalyseurPage.resultats(tu, e) ++ titleUrls
    } 
    /**
     * temps de la recherche
     */
   time = ((System.nanoTime() - startTime) / Math.pow(10, 9)).formatted("%.4f") 
    /**
     * Conversion de notre liste de couple de String en objet HTML grace à resultat2html
     */
    var resultPage = Production.resultat2html(titleUrls)
    
    /**
     * Conversion de la page HTML en string avec html2String
     */
    
    var pageString = html2String.process(resultPage)
    
    /**
     * Ecriture du string dans un fichier txt qui sera ensuite html
     */
    
    Printer.print("Recherche terminée en " +  time + " seconde(s) !\nLe fichier se trouve dans le dossier Result." );
    try{
    file.write(pageString)
    } finally file.close()
    }
  
}