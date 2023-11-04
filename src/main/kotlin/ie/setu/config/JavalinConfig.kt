package ie.setu.config

import ie.setu.controllers.HealthTrackerController
import ie.setu.controllers.UserActivityController
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.JavalinJackson

class JavalinConfig {

    fun startJavalinService(): Javalin {
        val app = Javalin.create {
            //add this jsonMapper to serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
        }
            .apply{
                exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
                error(404) { ctx -> ctx.json("404 - Not Found") }
            }
            .start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }

    private fun registerRoutes(app: Javalin) {
        app.routes {
            path("/api/users") {
                get(HealthTrackerController::getAllUsers)
                post(HealthTrackerController::addUser)
                path("{user-id}") {
                    get(HealthTrackerController::getUserByUserId)
                    delete(HealthTrackerController::deleteUserByUserId)
                    patch(HealthTrackerController::updateUser)
                    path("activities"){
                        get(UserActivityController::getActivitiesByUserId)
                    }
                }
                path("email/{user-email}") {
                    get(HealthTrackerController::getUserByUserEmail)
                }
                path("/api/activities") {
                    get(UserActivityController::getAllActivities)
                    post(UserActivityController::addActivity)
                }
            }
        }
    }
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7000
    }
}