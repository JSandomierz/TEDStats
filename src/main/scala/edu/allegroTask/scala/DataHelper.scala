package edu.allegroTask.scala

import scala.collection.parallel.mutable.ParHashMap
import scala.xml.NodeSeq

object DataHelper {
  var numF02WithoutOriginal = 0
  var numF02WithTotal = 0
  var numF02WithPatrs = 0
  var numF02count = 0
  //val mapFormTypeCount = new ParHashMap[String, BigInt]()

  val mapCostsInEuro = new ParHashMap[String, BigDecimal]()
  val mapCountNonZero = new ParHashMap[String, BigInt]()
  val mapCount = new ParHashMap[String, BigInt]()

  def extractCosts(nodes: NodeSeq): BigDecimal ={
    var result = BigDecimal(0)
    for( node <- nodes ){
      val ammount = BigDecimal( node.text )
      val currency = node.attribute("CURRENCY").getOrElse("").toString
      if(!currency.contentEquals("")){
        result += CurrencyConverter.toEuro( currency, ammount )
      }
    }
    return result
  }
  def printStats(): Unit ={
    println("MapCostsInEuro")
    println(DataHelper.mapCostsInEuro)
    println("MapCount")
    println(DataHelper.mapCountNonZero)
    println(s"STATS F02Count: $numF02count, totals: $numF02WithTotal, parts: $numF02WithPatrs, noOrig: $numF02WithoutOriginal")
    //println("MapFormTypeCount")
    //println(Helper.mapFormTypeCount)
  }
  def saveResults(path: String): Unit ={
    val numMeanCountryContracts: BigDecimal = BigDecimal( this.mapCount.values.sum.toString() ) / this.mapCount.size
    val mapMeanCountiesOrders = this.mapCostsInEuro.map( (f:(String,BigDecimal))=>(f._1, f._2/BigDecimal( this.mapCountNonZero.get(f._1).get ) ))
    val mapAdditional = Map[String,BigDecimal]( ("numMeanCountryContracts", numMeanCountryContracts) )

    FileBrowser.saveCsv[String,BigDecimal](path, "costsInEuro.csv", List("Country","SumEuro"), this.mapCostsInEuro.toMap.seq)
    FileBrowser.saveCsv[String,BigInt](path, "countiesNonZero.csv", List("Country","Count"), this.mapCountNonZero.toMap.seq)
    FileBrowser.saveCsv[String,BigInt](path, "countiesAll.csv", List("Country","Count"), this.mapCount.toMap.seq)
    FileBrowser.saveCsv[String,BigDecimal](path, "meanCosts.csv", List("Country","MeanEuro"), mapMeanCountiesOrders.toMap.seq)
    FileBrowser.saveCsv[String,BigDecimal](path, "additional.csv", List("Name","Value"), mapAdditional)
  }
}
