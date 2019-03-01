package robot_web

import library._

object FiltrageURL extends FiltrageURLs {
  
  /**
   * A partir d'un String, vérifie que ce dernier termine bien par un Int
   * 
   * @param s est un String
   * @return un booleen si le string/ l'URL termine part un Int
   */
  private def isLastInt(s: String): Boolean = {
    val tab: Array[String] = s.split("/")
    try {
      tab(tab.length-1).toLong
      return true
    } catch {
      case t: Throwable => return false
    }
    return false
  }
  
  /**
   * A partir d'une liste de pairs de String, rend la première URL du site de référence trouvée
   * 
   * @param l une liste de pairs de deux String
   * @return un string correspondant à la première URL d'annonce du site de référence, null si aucune n'est trouvée
   */
  private def getURL(l: List[(String, String)]): String = {
    l.foreach(p => {
      if (p._1 == "href" && p._2.contains("www.vivastreet.com/") && isLastInt(p._2)) {
        return p._2
      }
    })
    return null
  }
  
  /**
   * A partir d'un document Html h, rend la liste des URLs accessibles à partir
   * de h (ces URLs sont des hyperliens h) tels que ces URLs sont tous des URLs
   * d'annonces du site de référence
   *
   * @param h le document Html
   * @return la liste des URLs d'annonces contenues dans h
   */
  def filtreAnnonce(h: Html): List[String] = {
    var listeURLs: List[String] = List()
    h match {
      case Tag("a", x, y) => {
        val s: String = getURL(x)
        if (s != null && !listeURLs.contains(s)) {
          listeURLs = listeURLs.::(s)
        }
        y.foreach(html => listeURLs = listeURLs.:::(filtreAnnonce(html)))
      }
      case Tag(_, _, x) => x.foreach(html => listeURLs = listeURLs.:::(filtreAnnonce(html)))
      case _            => null
    }

    return listeURLs
  }

}
