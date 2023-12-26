package ie.setu.config

import ie.setu.controllers.ActivityController
import ie.setu.controllers.BmiController
import ie.setu.controllers.UserController
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.JavalinJackson
import io.javalin.vue.VueComponent



class JavalinConfig {

    fun startJavalinService(): Javalin {
        val app = Javalin.create{

            //added this jsonMapper for our integration tests - serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
            it.staticFiles.enableWebjars()
            it.vue.vueAppName = "app" // only required for Vue 3, is defined in layout.html
        }.apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 : Not Found") }
        }.start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }


    private fun registerRoutes(app: Javalin) {
        app.routes {
            path("/api/users") {
                get(UserController::getAllUsers)
                post(UserController::addUser)
                path("{user-id}") {
                    get(UserController::getUserByUserId)
                    delete(UserController::deleteUserByUserId)
                    patch(UserController::updateUser)
                    path("activities") {
                        get(ActivityController::getActivitiesByUserId)
                    }
                    path("bmis") {
                        get(BmiController::getBmisByUserId)
                    }
                }
                path("email/{user-email}") {
                    get(UserController::getUserByUserEmail)
                }
            }
            path("/api/activities") {
                get(ActivityController::getAllActivities)
                post(ActivityController::addActivity)
                path("{activity-id}") {
                    get(ActivityController::getActivitiesByActivityId)
                    delete(ActivityController::deleteActivityByActivityId)
                    patch(ActivityController::updateActivity)
                }
            }
            path("/api/bmi") {
                post(BmiController::calculateBmi)
                path("{bmi-id}") {
                    delete(BmiController::deleteBmiById)
                }
                path("{user-id}") {
                    get(BmiController::getbmis)
                }
            }
            // The @routeComponent that we added in layout.html earlier will be replaced
            // by the String inside the VueComponent. This means a call to / will load
            // the layout and display our <home-page> component.
            get("/", VueComponent("<home-page></home-page>"))
            get("/users", VueComponent("<user-overview></user-overview>"))
            get("/users/{user-id}", VueComponent("<user-profile></user-profile>"))
            get("/users/{user-id}/activities", VueComponent("<user-activity-overview></user-activity-overview>"))
            get("/users/{user-id}/bmi", VueComponent("<user-bmi-overview></user-bmi-overview>"))
            get("/activities", VueComponent("<activities-overview></activities-overview>"))
            get("/users/{user-id}/water-intakes", VueComponent("<water-intake></water-intake>"))
        }
    }
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7000
    }
}