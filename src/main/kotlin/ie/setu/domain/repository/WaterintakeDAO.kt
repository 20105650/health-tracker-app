package ie.setu.domain.repository

import ie.setu.domain.Waterintake
import ie.setu.domain.db.Waterintakes
import ie.setu.utils.mapToWaterintake
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class WaterintakeDAO {

    //Get all the Waterintakes in the database regardless of user id
    fun getAll(): ArrayList<Waterintake> {
        val waterintakesList: ArrayList<Waterintake> = arrayListOf()
        transaction {
            Waterintakes.selectAll().map {
                waterintakesList.add(mapToWaterintake(it)) }
        }
        return waterintakesList
    }

    //Find a specific waterintake by waterintake id
    fun findByWaterintakeId(id: Int): Waterintake?{
        return transaction {
            Waterintakes
                .select() { Waterintakes.id eq id}
                .map{ mapToWaterintake(it) }
                .firstOrNull()
        }
    }

    //Save a waterintake to the database
    fun save(waterintake: Waterintake): Int {
        val dailyrequired = String.format("%.2f", waterintake.dailyrequired).toDouble();

        return transaction {
            Waterintakes.insert {
                it[weight] = waterintake.weight
                it[Waterintakes.dailyrequired] = dailyrequired
                it[waterconsumed] = waterintake.waterconsumed
                it[userId] = waterintake.user_id
                it[createdat] = waterintake.createdat
            }
        } get Waterintakes.id
    }

    //Find all waterintake  for a specific user id for today only
    fun findByUserId(userId: Int): List<Waterintake>{
        return transaction {
            Waterintakes
                .select { Waterintakes.userId eq userId}
                .orderBy(Waterintakes.createdat, SortOrder.DESC)
                .limit(1)
                .map { mapToWaterintake(it) }
        }
    }
    fun delete(id: Int){
        return transaction { Waterintakes.deleteWhere { Waterintakes.id eq id } }
    }
}