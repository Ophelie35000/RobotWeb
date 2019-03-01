package robot_web
import library._

/**
 * AnalysePage
 * @author slocke
 *
 */
object AnalyseurPage extends AnalysePage {

  def resultats(url: String, exp: Expression): List[(String, String)] = { // takes a url of the search and a expression ( And / or... ) and returns the list of titles and url coresponding

    
    /**
     * List used to store the valid Html files with the corresponding urls
     * Liste dans laquelle est stocker l'expresssion HTML d'une page et son URL correspondante
     */
    var htmlList: List[(String, Html)] = List()
    
    /**
     * list used to store every url and their title
     * Liste dans laquelle est stocker L'url d'un site et son titre
     */
    var finalList: List[(String, String)] = List()
    
    /**
     *  conversion of the url received in param to its corresponding HTML type
     *  on demande le contenut le la page donnee en Param 
     */
    var h: Html = UrlProcessor.fetch(url)
    
//    println(getNumberResult(h));
    
    /**
     * list containing all the urls of the results  present on the result page 
     * ajoute tout les URL des resultats present sur la page de recherche
     */
    var filteredUrls: List[String] = FiltrageURL.filtreAnnonce(h)
    
    /**
     * checks if all the filtered URLS contain the right words and adds them to a list
     * on utilise filtrage HTML pour verifier que le contenu de chaque page contient bien les chose recherche dans l'expression
     */
    for (urls <- filteredUrls) {
      var htmls = UrlProcessor.fetch(urls)
      if (FiltrageHTMLImpl.filtreHtml(htmls, exp))
        htmlList = (urls, htmls) :: htmlList
    }

    /**
     * looks for the title in the HTLM page and adds the title to the final list with its url
     * On cherche le titre de chaque resultat et on ajoute la relation titre - url  dans la liste finale
     */
    for (urlsHtmls <- htmlList) {
      finalList = (findTitle2(urlsHtmls._2 :: List()), urlsHtmls._1) :: finalList
    }
    finalList
  }

  /**
   * version 1, not to use unless v2 does not work
   * @param html, a Htlm class that represents a Html Page
   * @return String , title of the param Htlm page
   */

  private def findTitle(h: Html): String = {
    h match {
      case Text(title)         => title
      case Tag("html", _, hd)  => findTitle(hd.head)
      case Tag("head", _, hd)  => findTitle(hd.head)
      case Tag("title", _, hd) => findTitle(hd.head)
      case Tag(_, _, hd)       => findTitle(hd.head)
    }
  }

  /**
   * version 2,
   * @param h list of Html documents, at program call, the only html present in the list is our Html page
   * 				h Expression html
   * @return String , the title of the Html page
   * 				String, Le titre de la page Html donne en param
   */
  private def findTitle2(h: List[Html]): String = {
    h match {
      case Text(title) :: rest         => title
      case Tag("html", _, child) :: _  => findTitle2(child)
      case Tag("head", _, child) :: _  => findTitle2(child)
      case Tag("title", _, child) :: _ => findTitle2(child)
      case Tag(_, _, _) :: reste       => findTitle2(reste)
      case Nil                         => "Title Not Found , error in find title, returned Nil"
    }
  }
  
  /**Retourne le nombre de résultats trouvés par le moteur de recherche de vivastreet
   * @param h un arbre Html
   */
  private def getNumberResult(h: Html): Int = {
    h match {
      case Tag("h2", _, Text(x) :: _) => "[^0-9]".r.replaceAllIn(x, "").toInt;
      case Tag(_, _, list) => getNumberResultList(list);
      case Text(_) => -1;
    }
  }
  
  private def getNumberResultList(h: List[Html]): Int = {
    h match {
      case Nil => -1;
      case x :: y => Math.max(getNumberResult(x), getNumberResultList(y)); 
    }
  }
}