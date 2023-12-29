package ie.setu.controllers

import ie.setu.domain.repository.BmiDAO
import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Bmi
import ie.setu.domain.repository.UserDAO
import ie.setu.domain.repository.GoalDAO
import ie.setu.domain.Goal
import ie.setu.utils.jsonToObject
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import ie.setu.domain.Activity
import org.joda.time.LocalDateTime

object BmiController {

    private val bmiDAO = BmiDAO()
    private val userDao = UserDAO()
    private val goalDao = GoalDAO()

    fun calculateBmi(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val bmi = mapper.readValue<Bmi>(ctx.body())
        val bmiId = bmiDAO.save(bmi)
        var descr = ""
        if(bmiId != null){
            var bmidata = bmiDAO.findByBmiId(bmiId)
            if (bmidata != null) {
                if(bmidata.bmival < 18.5){
                    descr = "Eating more frequently and Do more Exercise."
                } else if (bmidata.bmival >= 18.5 && bmidata.bmival < 23){
                    descr = "Maintain Healthy habit. "
                } else if (bmidata.bmival >= 23 && bmidata.bmival < 27.5){
                    descr = "Eating less and Do more Exercise."
                } else {
                    descr = "Do more Exercise and eat less"
                }
            }
            if (bmidata != null) {
                val goal: Goal = jsonToObject(
                    """{"goal_category": "bmi", "description": "${ descr }", "createdat": "${LocalDateTime.now()}", "user_id": ${ bmidata.user_id }}"""
                )
                goalDao.save(goal)
            }
        }
        ctx.json(bmi)
        ctx.status(201)
    }
    fun getbmis(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( bmiDAO.findByUserId(ctx.pathParam("user-id").toInt()) ))
    }
    fun getBmisByUserId(ctx: Context) {
        if (BmiController.userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val bmis = BmiController.bmiDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (bmis.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(bmis))
            }
            ctx.status(201)
        }
        else ctx.status(404)
    }
    fun deleteBmiById(ctx: Context) {
        BmiController.bmiDAO.delete(ctx.pathParam("bmi-id").toInt())
    }
}