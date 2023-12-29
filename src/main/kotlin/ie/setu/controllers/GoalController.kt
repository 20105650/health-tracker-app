package ie.setu.controllers


import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ie.setu.domain.repository.UserDAO
import ie.setu.domain.repository.GoalDAO
import ie.setu.domain.Goal
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule

object GoalController {

    private val userDao = UserDAO()
    private val goalDao = GoalDAO()

    fun getgoals(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( GoalController.goalDao.findByUserId(ctx.pathParam("user-id").toInt()) ))
    }
    fun getGoalsByUserId(ctx: Context) {
        if (GoalController.userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val goals = GoalController.goalDao.findByUserId(ctx.pathParam("user-id").toInt())
            if (goals.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(goals))
            }
        }
    }
    fun deleteGoalById(ctx: Context) {
        GoalController.goalDao.delete(ctx.pathParam("goal-id").toInt())
    }


}