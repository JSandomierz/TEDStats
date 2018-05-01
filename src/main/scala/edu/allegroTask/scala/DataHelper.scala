package edu.allegroTask.scala

import scala.collection.parallel.mutable.ParHashMap
import scala.xml.NodeSeq

object DataHelper {
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
