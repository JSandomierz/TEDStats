package edu.allegroTask.scala

import scala.collection.mutable
import scala.io.Source

object CountryCodes {
val mapCountryCodes: mutable.HashMap[String,String] = mutable.HashMap()
  def readCodesFromFile(): Unit ={
    val bufferedSource = Source.fromFile("./resources/countries.csv")
    for (line <- bufferedSource.getLines) {
      val cols = line.split(";").map(_.trim)
      mapCountryCodes.put(cols(0),cols(1))
    }
    bufferedSource.close
  }
  def getCountryNameFromCode(code: String):String = {
    return mapCountryCodes.getOrElse(code, code)
  }
}
