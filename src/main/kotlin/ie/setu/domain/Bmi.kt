package ie.setu.domain

import org.joda.time.DateTime

data class Bmi (var id: Int,
                     var weight:Double,
                     var height: Double,
                     var bmival: Double,
                     var bmiresult: String? = null,
                     var user_id: Int,
                     var createdat: DateTime)