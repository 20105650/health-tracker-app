package ie.setu.domain

import org.joda.time.DateTime

data class Goal (var id: Int,
                var goal_category:String,
                var description: String,
                var user_id: Int,
                var createdat: DateTime)