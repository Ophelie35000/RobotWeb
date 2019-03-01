package tests



import library._
import robot_web.AnalyseurPage

object App extends App {
  
  print(AnalyseurPage.resultats("https://search.vivastreet.com/annonces/rennes?lb=new&search=1&start_field=1&keywords=citroen&cat_1=&geosearch_text=Rennes&searchGeoId=13048",Word("citroen")))
}