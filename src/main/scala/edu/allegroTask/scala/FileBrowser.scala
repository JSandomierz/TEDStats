package edu.allegroTask.scala

import java.io.File
import java.util

import scala.collection.mutable.ListBuffer
import java.io._

object FileBrowser {
  def getDirectoryList(root: String): List[String] ={
    val files = ListBuffer[String]()
    val d = new File(root)
    for( dir <- d.listFiles().filter( d=>d.isDirectory() ) ){
      files.append(dir.getPath())
    }
    return files.toList
  }
  def saveCsv[K,V](path: String, name: String, columnNames: List[String], data: Map[K,V]): Unit ={
    // PrintWriter
    try{
      val directory = new File(path)
      if (!directory.exists) {
        directory.mkdirs()
      }
    }catch{
      case x: Exception => {
        println("Could not create directories to " + path)
      }
    }
    try{
      val pw = new PrintWriter(new File(path+name))
      pw.println(columnNames.mkString(";"))
      for( pair <- data.toList.sortWith( (x:(K,V), y:(K,V))=>x._1.toString.compareTo(y._1.toString)>0 )){
        pw.println(pair._1+";"+pair._2.toString.replace('.',','))
      }
      pw.close
    }catch{
      case x: Exception =>{
        println("Could not open file: "+path+name)
      }
    }
  }
}
