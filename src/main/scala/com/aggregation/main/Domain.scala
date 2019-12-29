package com.aggregation.main

object Domain {

  case class Event(client_id: Int,
                   user_id: String,
                   account_id: Int,
                   activity: String,
                   module: String

                  )

  case class Customer(client_id: Int
                     )

}



