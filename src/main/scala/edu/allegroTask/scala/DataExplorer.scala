package edu.allegroTask.scala

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

class DataExplorer {
    def exploreData(): Unit ={
      Logger.getLogger("org").setLevel(Level.ERROR)
      Logger.getLogger("akka").setLevel(Level.ERROR)
      val conf = new SparkConf()
        .setMaster("local[1]")
        .setAppName("Allegro task")
        /*.set("spark.logConf","true")
        .set("spark.driver.memory","200m")
        .set("spark.executor.memory","500m")
        .set("spark.num.executors","1")
        .set("spark.memory.fraction","0.3")
        .set("spark.memory.storageFraction","0.2")*/
      val spark = new SparkContext(conf)
      val fileList = FileBrowser.getDirectoryList("E:/data/2018-03")
      println(spark.uiWebUrl.get)
      //limit number of directories
      val numDirsToExplore = 14
      for( i <- 0 to math.min(numDirsToExplore,fileList.size-1) ){//explore one dir at time
        val logData = spark.wholeTextFiles(fileList(i))
        for( pair <- logData ){
          val xml = scala.xml.XML.loadString(pair._2)
          val F02Form = xml \\ "F02_2014"
          val original = F02Form.filter( p=>p.attribute("CATEGORY").getOrElse("").toString.contentEquals("ORIGINAL") )
          if(original.nonEmpty){
            val countryCode = (original \\ "COUNTRY").head.attribute("VALUE").getOrElse("Unknown").toString
            val total = original \\ "VAL_ESTIMATED_TOTAL"
            var cost = BigDecimal(0)
            if(total.nonEmpty){
              cost = DataHelper.extractCosts(total)
            }else{
              val parts = original \\ "VAL_OBJECT"
              cost = DataHelper.extractCosts(parts)
            }
            //collect data
            val country = CountryCodes.getCountryNameFromCode(countryCode)
            if(cost > 0){
              DataHelper.mapCostsInEuro.put(country, DataHelper.mapCostsInEuro.getOrElse(country, BigDecimal(0)) + cost )
              DataHelper.mapCountNonZero.put(country, DataHelper.mapCountNonZero.getOrElse(country, BigInt(0)) + 1 )
            }
            DataHelper.mapCount.put(country, DataHelper.mapCount.getOrElse(country, BigInt(0)) + 1 )
          }
        }
        logData.unpersist(true)
      }
      spark.stop()
    }
}