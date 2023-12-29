package ie.setu.domain.repository

import ie.setu.domain.Goal
import ie.setu.domain.db.Goals
import ie.setu.utils.mapToGoal
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


class GoalDAO {
    //Get all the goals in the database regardless of user id
    fun getAll(): ArrayList<Goal> {
        val goalsList: ArrayList<Goal> = arrayListOf()
        transaction {
            Goals.selectAll().map {
                goalsList.add(mapToGoal(it)) }
        }
        return goalsList
    }

    //Find a specific goal by goal id
    fun findByGoalId(id: Int): Goal?{
        return transaction {
            Goals
                .select() { Goals.id eq id}
                .map{ mapToGoal(it) }
                .firstOrNull()
        }
    }

    //Save a Bmi to the database
    fun save(goal: Goal): Int {
        transaction { Goals.deleteWhere { (Goals.userId eq goal.user_id) and (Goals.goal_category eq goal.goal_category) } }


        return transaction {
            Goals.insert {
                it[goal_category] = goal.goal_category
                it[description] = goal.description
                it[userId] = goal.user_id
                it[createdat] = goal.createdat
            }
        } get Goals.id
    }

    //Find all Bmi calculations for a specific user id
    fun findByUserId(userId: Int): List<Goal>{
        return transaction {
            Goals
                .select { Goals.userId eq userId}
                .map { mapToGoal(it) }
        }
    }
    fun delete(id: Int){
        return transaction { Goals.deleteWhere { Goals.id eq id } }
    }
}
