package edu.allegroTask.scala

object App {
  def main(args: Array[String]) {
    CountryCodes.readCodesFromFile()
    System.setProperty("hadoop.home.dir", "C:/winutils")
    val st = new DataExplorer()
    st.exploreData()
    DataHelper.saveResults("C:/Results/")
  }
}