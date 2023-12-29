package ie.setu.controllers

import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.repository.UserDAO
import ie.setu.domain.repository.GoalDAO
import ie.setu.domain.Goal
import ie.setu.utils.jsonToObject
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import ie.setu.domain.Waterintake
import ie.setu.domain.repository.WaterintakeDAO
import org.joda.time.LocalDateTime

object WaterintakeController {

    private val waterintakeDao = WaterintakeDAO()
    private val userDao = UserDAO()
    private val goalDao = GoalDAO()

    fun calculateWaterintake(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val waterintake = mapper.readValue<Waterintake>(ctx.body())
        val wid = waterintakeDao.save(waterintake)
        var descr = ""
        if(wid != null) {
            var waterintakedata = WaterintakeController.waterintakeDao.findByWaterintakeId(wid)
            if (waterintakedata != null) {
                if(waterintakedata.waterconsumed < waterintakedata.dailyrequired)
                    descr = "You have to consume more than ${ waterintakedata.dailyrequired - waterintakedata.waterconsumed } ounces of water to maintain a healthy habit."
                else
                    descr = "Your water consumption is going good.Keep going."
            }
            if (waterintakedata != null) {
                val goal: Goal = jsonToObject(
                    """{"goal_category": "water intake", "description": "${ descr }", "createdat": "${LocalDateTime.now()}", "user_id": ${ waterintakedata.user_id }}"""
                )
                goalDao.save(goal)
            }
        }

        ctx.json(waterintake)
    }
    fun getwaterintakes(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( waterintakeDao.findByUserId(ctx.pathParam("user-id").toInt()) ))
    }
    fun getWaterintakesByUserId(ctx: Context) {
        if (WaterintakeController.userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val waterintakes = WaterintakeController.waterintakeDao.findByUserId(ctx.pathParam("user-id").toInt())
            if (waterintakes.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(waterintakes))
            }
        }
    }
    fun deleteWaterintakeById(ctx: Context) {
        WaterintakeController.waterintakeDao.delete(ctx.pathParam("waterintake-id").toInt())
    }
}