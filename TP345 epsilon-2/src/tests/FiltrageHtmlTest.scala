package tests
import library._
import robot_web.FiltrageHTMLImpl

object FiltrageHTMLTest extends App {
  println("Début test");
  normalValidTest();
  
  def normalExpressionTest() = {
    val exp = And(Or(Word("chat"), Word("chien")), Or(Word("vendre"), Word("donner")));
    println(FiltrageHTMLImpl.compareStringWithExpression("chien à donner", exp));
    println(FiltrageHTMLImpl.compareStringWithExpression("chat à donner", exp));
    println(FiltrageHTMLImpl.compareStringWithExpression("chien à vendre", exp));
    println(FiltrageHTMLImpl.compareStringWithExpression("chat à donner", exp));
    
    //Erreur
    println(FiltrageHTMLImpl.compareStringWithExpression("cha à donner", exp));
    println(FiltrageHTMLImpl.compareStringWithExpression("chat à ", exp));
    println(FiltrageHTMLImpl.compareStringWithExpression("vendre", exp));
    
    val exp2 = And(Word("chat"), Word("chien"));
    println(FiltrageHTMLImpl.compareStringWithExpression("chat", exp2));
    println(FiltrageHTMLImpl.compareStringWithExpression("Un chat qui aime le CHIEN", exp2));
    println(FiltrageHTMLImpl.compareStringWithExpression("", exp2));
    
    val exp3 = Word("chien");
    println(FiltrageHTMLImpl.compareStringWithExpression("chat", exp2));
  }
  
  def normalValidTest() = {
    //val test = Tag("body", List(), List(Tag("div", List(("class", "shortdescription")), List(Text("chat à vendre"))), Text("foo bar")));
    val html = UrlProcessor.fetch("https://www.vivastreet.com/animal-services/guignen-35580/pension-chien-et-chat-promenade/195065492");
//    println(html);
    println(FiltrageHTMLImpl.filtreHtml(html, And(Word("Bretagne"), Word("chien"))));
  }
  
  def listAttributsTest() {
    val list = List(("foo", "bar"), ("chat", "chien"), ("java", "scala"));
    println(FiltrageHTMLImpl.browseAttributs(list, "class", "kiwii-padding-top-xxsmall kiwii-padding-left-large kiwii-padding-right-medium"));
  }
}