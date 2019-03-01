package tests



import library._
import robot_web.RobotWebCore

object AppTest extends App {
 /*
   * Commandes Test
   * On rentre une expression au clavier
   * Elle est convertit en String
   * Puis on observe le resultat
   * cela nous permet de voir si on obtient bien l'expression, que la conversion en String est correct
   * Et que la liste des URL est cohérente
   */
  var e = ExpressionParser.readExp
  var s = RobotWebCore.ExptoString(e)
  println(s)
  println(e)
  println(RobotWebCore.createUrls(s))
  
  //Test deja implementes
  println(RobotWebCore.ExptoString(And(Word("nantes"), Word("rennes"))) == List("nantes+rennes"))
  println(RobotWebCore.ExptoString(And(Word("rennes"), Word("nantes"))) == List("nantes+rennes"))
  println(RobotWebCore.ExptoString(And(Or(Word("paris"),Word("rennes")),Or(Word("camion"),Or(Word("voiture"),Or(Word("avion"),Word("skate")))))) != List("paris+camion, voiture+paris, avion+paris, skate+paris, rennes+camion, voiture+rennes, avion+rennes, skate+rennes"))
  println(RobotWebCore.ExptoString(Or(Word("rennes"), Word("nantes"))) == List("rennes, nantes"))
  println()
  println(RobotWebCore.createUrls(RobotWebCore.ExptoString(And(Word("nantes"), Word("rennes")))) == List("https://search.vivastreet.com/annonces/fr?lb=new&search=1&start_field=1&keywords=nantes+rennes&cat_1=&geosearch_text=&searchGeoId=0"))
  println(RobotWebCore.createUrls(RobotWebCore.ExptoString(Or(Word("nantes"), Word("rennes")))) == List("https://search.vivastreet.com/annonces/fr?lb=new&search=1&start_field=1&keywords=nantes+rennes&cat_1=&geosearch_text=&searchGeoId=0"))
}