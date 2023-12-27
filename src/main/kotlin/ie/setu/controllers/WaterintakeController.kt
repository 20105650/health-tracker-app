package ie.setu.controllers

import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Bmi
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import ie.setu.domain.Waterintake
import ie.setu.domain.repository.WaterintakeDAO

object WaterintakeController {

    private val waterintakeDao = WaterintakeDAO()
    private val userDao = UserDAO()

    fun calculateWaterintake(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val waterintake = mapper.readValue<Waterintake>(ctx.body())
        waterintakeDao.save(waterintake)
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