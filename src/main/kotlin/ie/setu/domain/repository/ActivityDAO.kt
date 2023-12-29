package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.utils.mapToActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ActivityDAO {

    //Get all the activities in the database regardless of user id
    fun getAll(): ArrayList<Activity> {
        val activitiesList: ArrayList<Activity> = arrayListOf()
        transaction {
            Activities.selectAll().map {
                activitiesList.add(mapToActivity(it)) }
        }
        return activitiesList
    }

    //Find a specific activity by activity id
    fun findByActivityId(id: Int): Activity?{
        return transaction {
            Activities
                .select() { Activities.id eq id}
                .map{mapToActivity(it)}
                .firstOrNull()
        }
    }

    //Find all activities for a specific user id
    fun findByUserId(userId: Int): List<Activity>{
        return transaction {
            Activities
                .select {Activities.user_id eq userId}
                .map {mapToActivity(it)}
        }
    }

    //Save an activity to the database
    fun save(excercise: Activity): Int {
        return transaction {
            val typeMet = excercise.type.split('@')
           // if(typeMet) {
            val typ = typeMet.getOrElse(0) { "" }
            val met = typeMet.getOrNull(1)?.toDoubleOrNull() ?: 0.0
                // Convert the string to a Double or default to 0.0 if conversion fails

                val calory = ((met * excercise.weight * 3.5) / 200) * excercise.duration
           // }
            Activities.insert {

                it[activity] = excercise.activity
                it[duration] = excercise.duration
                it[weight] = excercise.weight
                it[type] = typ
                it[calories] = calory
                it[createdat] = excercise.createdat
                it[user_id] = excercise.user_id
            } get Activities.id
        }
    }

    //Update an activity with specific id
    fun updateByActivityId(activityId: Int, activityToUpdate: Activity) : Int{
        return transaction {
            Activities.update ({
                Activities.id eq activityId}) {
                it[activity] = activityToUpdate.activity
                it[duration] = activityToUpdate.duration
                it[calories] = activityToUpdate.calories
                it[createdat] = activityToUpdate.createdat
                it[user_id] = activityToUpdate.user_id
            }
        }
    }

    //Delete an activity with specific id
    fun deleteByActivityId (activityId: Int): Int{
        return transaction{
            Activities.deleteWhere { Activities.id eq activityId }
        }
    }

    //Delete activities of a specific userid
    fun deleteByUserId (userId: Int): Int{
        return transaction{
            Activities.deleteWhere { Activities.user_id eq userId }
        }
    }

}