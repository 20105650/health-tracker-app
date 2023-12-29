package ie.setu.domain

import org.joda.time.DateTime

data class Activity (var id: Int,
                     var activity:String,
                     var weight: Double,
                     var duration: Double,
                     var type: String,
                     var calories: Double,
                     var createdat: DateTime,
                     var user_id: Int)