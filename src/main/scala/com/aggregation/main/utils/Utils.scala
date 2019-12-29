package com.aggregation.main.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.ChronoUnit.DAYS

import com.aggregation.main.App.log
import com.aggregation.main.Domain
import com.aggregation.main.conf.AppConstants
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType

object Utils extends Serializable {

  val DATE_TIME_FORMAT: String = "yyyy-MM-dd"
  val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)


  def generateDailyDatesRange(startDate: String, endDate: String): Seq[String] = {
    val fmt = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
    val start = LocalDate.parse(startDate, fmt)
    val end = LocalDate.parse(endDate, fmt)

    val dateDiff = DAYS.between(start, end).toInt
    (0 until dateDiff).map(i => {
      fmt.format(start.plusDays(i))
    })
  }

  def generateStartDate(date: String, daysBack: Int): String = {
    val dateObj = LocalDate.parse(date)
    dateObj.minus(daysBack, ChronoUnit.DAYS).format(dateFormat)

  }


  def readRawData(spark: SparkSession, datesToQuery: Seq[String], basePath: String): DataFrame = {

    val eventSchema = ScalaReflection.schemaFor[Domain.Event].dataType.asInstanceOf[StructType]
    val paths = datesToQuery map (date => s"${basePath}/${AppConstants.DATE}=$date")
    log.info(s"paths are ${paths}")
    val segmentsDevicesDS = spark.read.schema(eventSchema).json(paths : _*)
    segmentsDevicesDS
  }

  def readAggreagedData(spark: SparkSession, datesToQuery: Seq[String], basePath: String): DataFrame = {

    val paths = datesToQuery map (date => s"${basePath}/${AppConstants.DATE}=$date")
    log.info(s"paths are ${paths}")
    val segmentsDevicesDS = spark.read.option("basePath", basePath).parquet(paths : _*)
    segmentsDevicesDS
  }


}
