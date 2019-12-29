package com.aggregation.main

import com.aggregation.main.conf.AppConstants
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col,countDistinct}
class Logic {
  //  ● What is the number of activities used per user per account per module in the last 1, 3 ,7 ,14, 30, 90, 180, 365 days
  //    ● What is the number of modules used per user per account in the last 1, 3, 7, 14, 30, 90,180, 365 days

  def getActivityAggregation(inputDF:DataFrame):DataFrame = {
    inputDF.select(col(AppConstants.USER_ID),col(AppConstants.ACCOUNT_ID),col(AppConstants.MODULE),col(AppConstants.ACTIVITY))
      .groupBy(col(AppConstants.USER_ID),col(AppConstants.ACCOUNT_ID),col(AppConstants.MODULE))
      .agg(countDistinct(col(AppConstants.ACTIVITY)).alias(AppConstants.ACTIVITY_COUNT))
  }

  def getModuleAggregation(inputDF:DataFrame):DataFrame = {
    inputDF.select(col(AppConstants.USER_ID),col(AppConstants.ACCOUNT_ID),col(AppConstants.MODULE))
      .groupBy(col(AppConstants.USER_ID),col(AppConstants.ACCOUNT_ID))
      .agg(countDistinct(col(AppConstants.MODULE)).alias(AppConstants.MODULE_COUNT))
  }

}

