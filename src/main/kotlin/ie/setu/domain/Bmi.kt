package ie.setu.domain

import org.joda.time.DateTime

data class Bmi (var id: Int,
                     var weight:Double,
                     var height: Double,
                     var age: Int,
                     var community: String,
                     var bmival: Double,
                     var bmiresult: String? = null,
                     var userId: Int,
                     var createdat: DateTime? = null)