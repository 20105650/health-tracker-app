package ie.setu.domain

import org.joda.time.DateTime

data class Waterintake (var id: Int,
                var weight:Double,
                var waterconsumed: Double,
                var dailyrequired: Double,
                var user_id: Int,
                var createdat: DateTime)