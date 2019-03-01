package tests
import library._
import robot_web.FiltrageURL


object FiltrageURLTest extends App {
  val two_links = Tag("html",List(),
                   List(Tag("body2",List(),List(
                            Text("&nbsp"),
                            Tag("center",List(),List(
                            		Tag("a", List(("href","http://www.irisa2.fr")),	
                            				List(Text("Lien"),Tag("img",List(),List()))))))),
                        Tag("body",List(),List(
                            Text("&nbsp"),
                            Tag("center",List(),List(
                            		Tag("a", List(("href","http://www.irisa.fr")),	
                            				List(Text("Lien"),Tag("img",List(),List())))))))))
  val one_link = Tag("html",List(),
                   List(Tag("head",List(),
                            List(Tag("meta",List(("content","text/html"),("charset","iso-8859-1")),List()),
                            		Tag("title",List(),List(Text("MyPage"))))),
                        Tag("body",List(),List(
                            Text("&nbsp"),
                            Tag("center",List(),List(
                            		Tag("a", List(("href","http://www.irisa.fr")),	
                            				List(Text("Lien"),Tag("img",List(),List())))))))))
 val no_link = Tag("html",List(),
                   List(Tag("head",List(),
                            List(Tag("meta",List(("content","text/html"),("charset","iso-8859-1")),List()),
                            		Tag("title",List(),List(Text("MyPage"))))),
                        Tag("body",List(),List(
                            Text("&nbsp"),
                            Tag("center",List(),List(
                            		Tag("a2", List(("href","http://www.irisa.fr")),	
                            				List(Text("Lien"),Tag("img",List(),List())))))))))       
  val many = Tag("html",List(),
                   List(Tag("head",List(),
                            List(Tag("meta",List(("content","text/html"),("charset","iso-8859-1")),List()),
                            		Tag("title",List(),List(Text("MyPage"))))),
                        Tag("body",List(),List(
                            Text("&nbsp"),
                            Tag("center",List(),List(
                            		Tag("a", List(("href","http://www.irisa1.fr")),	
                            				List(Text("Lien"),Tag("img",List(),List()))))))),
                        Tag("body2",List(),List(
                            Text("&nbsp"),
                            Tag("center",List(),List(
                            		Tag("a", List(("href","http://www.irisa2.fr")),	
                            				List(Text("Lien"),Tag("img",List(),List()))))))),
                        Tag("body3",List(),List(
                            Text("&nbsp"),
                            Tag("center",List(),List(
                            		Tag("a", List(("href","https://www.vivastreet.com/achat-vente-sport-velo/clichy-92110/chariot-de-golf/195169623")),	
                            				List(Text("Lien"),Tag("img",List(),List())))))))))                          				
  println("Début")
  FiltrageURL.filtreAnnonce(many).foreach(o => println(o))
}