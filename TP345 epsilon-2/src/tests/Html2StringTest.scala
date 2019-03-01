package tests
import library._
import robot_web.html2String


object HTML2StringTest extends App {
   val head: Html = Tag("head", List(), List(
        Tag("meta", List(("content","text/html"),("charset","UTF-8")),List()),
        Tag("title",List(),List(Text("Hello")))))
  
   val body: Html = Tag("body",List(),List(
        Text("&nbsp"),
        Tag("center",List(),List(Text("")))))
        
  
  println(html2String.process(head))
  println(html2String.process(body))
  println(html2String.process(ExempleHtml.exemple))
}