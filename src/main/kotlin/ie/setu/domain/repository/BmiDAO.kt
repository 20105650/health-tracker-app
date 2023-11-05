package ie.setu.domain.repository

import ie.setu.domain.Bmi
import ie.setu.domain.db.Bmis
import ie.setu.utils.mapToBmi
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class BmiDAO {

    //Save a Bmi to the database
    fun save(bmi: Bmi): Int {
        val bmivalue = bmi.weight / ((bmi.height/100) * (bmi.height/100))
        var bmires :String
        if(bmivalue < 18.5) {
            bmires = "underweight"
        }
        if (bmivalue > 18.5 && bmivalue < 23){
            bmires = "Healthy"
        }
        else {
            bmires = "Overweight"
        }
        val currentDateTime: org.joda.time.DateTime = org.joda.time.DateTime.now()
        return transaction {
            Bmis.insert {
                it[weight] = bmi.weight
                it[height] = bmi.height
                it[age] = bmi.age
                it[community] = bmi.community
                it[bmival] = bmivalue
                it[bmiresult] = bmires
                it[userId] = bmi.userId
                it[createdat] = currentDateTime
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
}