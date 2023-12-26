package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.Bmi
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Bmis
import ie.setu.domain.db.Users
import ie.setu.utils.mapToActivity
import ie.setu.utils.mapToBmi
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class BmiDAO {

    //Get all the bmis in the database regardless of user id
    fun getAll(): ArrayList<Bmi> {
        val bmisList: ArrayList<Bmi> = arrayListOf()
        transaction {
            Bmis.selectAll().map {
                bmisList.add(mapToBmi(it)) }
        }
        return bmisList
    }

    //Find a specific bmi by bmi id
    fun findByBmiId(id: Int): Bmi?{
        return transaction {
            Bmis
                .select() { Bmis.id eq id}
                .map{ mapToBmi(it) }
                .firstOrNull()
        }
    }

    //Save a Bmi to the database
    fun save(bmi: Bmi): Int {
        val bmivaluee = bmi.weight / ((bmi.height/100) * (bmi.height/100))
        val bmivalue = String.format("%.2f", bmivaluee).toDouble()
        var bmires :String
        if(bmivalue < 18.5) {
            bmires = "underweight"
        }
        else if (bmivalue >= 18.5 && bmivalue < 23){
            bmires = "Healthy"
        }
        else if (bmivalue >= 23 && bmivalue < 27.5){
            bmires = "Overweight"
        }
        else {
            bmires = "Obese"
        }
//val currentDateTime: org.joda.time.DateTime = org.joda.time.DateTime.now()
        return transaction {
            Bmis.insert {
                it[weight] = bmi.weight
                it[height] = bmi.height
                it[age] = bmi.age
                it[community] = bmi.community
                it[bmival] = bmivalue
                it[bmiresult] = bmires
                it[userId] = bmi.user_id
                it[createdat] = bmi.createdat
            }
        } get Bmis.id
    }

    //Find all Bmi calculations for a specific user id
    fun findByUserId(userId: Int): List<Bmi>{
        return transaction {
            Bmis
                .select { Bmis.userId eq userId}
                .map { mapToBmi(it) }
        }
    }
    fun delete(id: Int){
        return transaction { Bmis.deleteWhere { Bmis.id eq id } }
    }
}