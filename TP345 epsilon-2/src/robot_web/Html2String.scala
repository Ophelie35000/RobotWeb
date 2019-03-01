package robot_web
import library._

object html2String extends Html2String{
  
  /** Produit la chaîne de caractère correspondant à un document Html
    
    @param h le document Html
    @return la chaîne de caractère représentant h
 */ 
  
  def process(h:Html):String={
    h match {
    case Tag(t,lp,lf) => "<"+t+ processLP(lp)+"> "+"\n"+ processLH(lf) + "</"+t+">\n"
    case Text(t) => t
    }
  }
  
  /** Produit la chaîne de caractère correspondant à une liste d'html
    
    @param lh la liste d'html
    @return la chaîne de caractère représentant lh
 */
  
  private def processLH(lh : List[Html]): String={
    lh match{
      case Nil => ""
      case h :: t =>process(h) + processLH(t)
    }
  }
  
  /** Produit la chaîne de caractère correspondant à une liste de couples de string
    
    @param lp la liste de couples de string
    @return la chaîne de caractère représentant lp
 */
  
  private def processLP(lp : List[(String, String)]): String ={
    lp match{
      case Nil =>""
      case h::Nil => " "+h._1 +"="+ '"' + h._2+ '"'
      case h::t => " "+h._1 +"="+ '"' + h._2+ '"'+";" + processLP(t)
    }
  }
}
