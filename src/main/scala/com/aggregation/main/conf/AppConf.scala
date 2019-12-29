package com.aggregation.main.conf

import org.rogach.scallop._
import org.apache.logging.log4j.{LogManager, Logger}

class AppConf(args: Seq[String]) extends ScallopConf(args) {
  val log: Logger = LogManager.getLogger(this.getClass)

  val dateArgument: ScallopOption[String] = opt[String]("date", required = false)
  val customerId: ScallopOption[Int] = opt[Int]("customerId", required = false)

  verify()

}
