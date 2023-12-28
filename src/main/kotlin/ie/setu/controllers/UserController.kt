package ie.setu.controllers

import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import ie.setu.domain.User
import ie.setu.utils.jsonToObject

object UserController {

    private val userDao = UserDAO()

    fun getAllUsers(ctx: Context) {
        val users = userDao.getAll()
        if (users.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(users)
    }
    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }
    fun getUserByUserEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("user-email"))
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun addUser(ctx: Context){
        val mapper = jacksonObjectMapper()
        var user: User = jsonToObject(ctx.body())
        val userId = userDao.save(user)
        if (userId != null) {
            user = user.copy(id = userId) // Use the copy function to create a new user with the updated id
            ctx.json(user)
            ctx.status(201)
        }
    }

    fun deleteUserByUserId(ctx: Context) {
        if(userDao.delete(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateUser(ctx: Context){
       /* val mapper = jacksonObjectMapper()
        val userData = mapper.readValue<User>(ctx.body())
        userDao.update(id = ctx.pathParam("user-id").toInt() , user = userData) */
        val foundUser : User = jsonToObject(ctx.body())
        if ((userDao.update(id = ctx.pathParam("user-id").toInt(), user=foundUser)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

}