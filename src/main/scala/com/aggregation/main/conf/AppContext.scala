package com.aggregation.main.conf

import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object AppContext {

  val log: Logger = LogManager.getLogger(this.getClass)

  def initSpark(appName: String): SparkSession = {

    val sparkConf = new SparkConf().setAppName(appName).setMaster("local[*]")

    val sparkSession = buildSparkSession(sparkConf)
    sparkSession
  }


  private def buildSparkSession(sparkConf: SparkConf): SparkSession = {
    SparkSession
      .builder()
      .config(sparkConf)
      .getOrCreate()
  }



}
