package com.aggregation.main

import com.aggregation.main.conf.{AppConstants, AppContext}
import com.aggregation.main.utils.Utils
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType

object App {

  @transient lazy val log: Logger = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    log.info("Starting task!")

    //val appConf = new AppConf(args)
    //val customerId= appConf.customerId
    //    val today = appConf.dateArgument


    implicit val spark: SparkSession = AppContext.initSpark("task")


    val customerId = 1234
    val today = "2019-12-30"
    val basePath = s"/Users/tal/Documents/git/task/${AppConstants.CUSTOMER}=$customerId"

    //aggregated and write today's data
    val todaysData = Utils.readRawData(spark, List(today), s"${basePath}/${AppConstants.RAW_DATA}/")
    todaysData
      .write
      .mode(SaveMode.Overwrite)
      .partitionBy(AppConstants.CLIENT_ID)
      .parquet(s"${basePath}/${AppConstants.AGGREGATED_DATA}/${AppConstants.DATE}=$today")


    //prepare reports
    val timeRangesInDays = List(1,3) // 1,3, 7, 14, 30, 90, 180, 365
    val logic = new Logic()
    timeRangesInDays.foreach(timeRange => {
      val startDate = Utils.generateStartDate(today,timeRange)
      val datesToQuery = Utils.generateDailyDatesRange(startDate,today)

      log.info(s"Last ${timeRange} days!")
      val customerDf = Utils.readAggreagedData(spark, datesToQuery, s"${basePath}/${AppConstants.AGGREGATED_DATA}/")
      customerDf.show()

      val activityAggregation = logic.getActivityAggregation(customerDf)
      activityAggregation.show()

      val moduleAggregation = logic.getModuleAggregation(customerDf)
      moduleAggregation.show()
    }
   )


    log.info("Finished task!")


  }


}
