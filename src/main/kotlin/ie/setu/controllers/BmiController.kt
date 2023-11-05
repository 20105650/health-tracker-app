package ie.setu.controllers

import ie.setu.domain.repository.BmiDAO
import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Bmi
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import ie.setu.domain.Activity

object BmiController {

    private val bmiDAO = BmiDAO()
    private val userDao = UserDAO()

    fun calculateBmi(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val bmi = mapper.readValue<Bmi>(ctx.body())
        bmiDAO.save(bmi)
        ctx.json(bmi)
    }
    fun getbmis(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( bmiDAO.findByUserId(ctx.pathParam("user-id").toInt()) ))
    }
}