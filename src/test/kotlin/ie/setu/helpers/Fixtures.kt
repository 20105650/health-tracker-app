package ie.setu.helpers

import ie.setu.domain.*
import ie.setu.domain.User
import ie.setu.domain.db.*
import ie.setu.domain.db.Users
import ie.setu.domain.repository.*
import ie.setu.domain.repository.UserDAO
import org.apache.http.client.utils.DateUtils.parseDate
import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"
val validNamee = "Test User 2"
val validEmaill = "testuser2@test.com"
val updatedName = "Updated Name"
val updatedEmail = "Updated Email"
val updatedNamee = "Updated Namee"
val updatedEmaill = "Updated Emaill"

val updatedDescription = "Updated Description"
val updatedDuration = 30.0
val updatedCalories = 945
val updatedStarted = DateTime.parse("2020-06-11T05:59:27.258Z")

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com",  id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4)
)


val activities = arrayListOf<Activity>(
    Activity(id = 1, activity = "Running",weight=55.00, duration = 45.00, type="Unicycling",calories = 216.5625, createdat = DateTime.now(), user_id = 1),
    Activity(id = 2, activity = "Hopping",weight=55.00, duration = 45.00, type="Unicycling",calories = 216.5625, createdat = DateTime.now(), user_id = 2),
    Activity(id = 3, activity = "Walking",weight=55.00, duration = 45.00, type="Unicycling",calories = 216.5625, createdat = DateTime.now(), user_id = 3)
)
val bmis = arrayListOf<Bmi>(
    Bmi(id = 1, weight =43.50 , height =165.50, bmival=15.88,bmiresult="underweight",createdat = DateTime.now(), user_id = 1)
)

val waterintakes = arrayListOf<Waterintake>(
    Waterintake(id=1,weight = 100.00, waterconsumed = 30.00 ,dailyrequired =66.67,createdat = DateTime.now(), user_id = 1),
    Waterintake(id=2,weight = 110.00, waterconsumed = 20.00 ,dailyrequired =73.30,createdat = DateTime.now(), user_id = 2),
    Waterintake(id=3,weight = 120.00, waterconsumed = 40.00 ,dailyrequired =80.67,createdat = DateTime.now(), user_id = 3),

    )

val goals = arrayListOf<Goal>(
    Goal(id=1, goal_category="bmi", description="test goal", user_id=1, createdat=DateTime.now()),
    Goal(id=2, goal_category="bmi", description="test goal", user_id=2, createdat=DateTime.now()),
    Goal(id=3, goal_category="bmi", description="test goal", user_id=3, createdat=DateTime.now())

    )

fun populateUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users[0])
    userDAO.save(users[1])
    userDAO.save(users[2])
    return userDAO
}
fun populateActivityTable(): ActivityDAO {
    SchemaUtils.create(Activities)
    val activityDAO = ActivityDAO()
    activityDAO.save(activities[0])
    activityDAO.save(activities[1])
    activityDAO.save(activities[2])
    return activityDAO
}

fun populateBmiTable(): BmiDAO {
    SchemaUtils.create(Bmis)
    val bmiDAO = BmiDAO()
    bmiDAO.save(bmis[0])
    return bmiDAO
}

fun populateWaterintakeTable(): WaterintakeDAO {
    SchemaUtils.create(Waterintakes)
    val waterintakeDAO = WaterintakeDAO()
    waterintakeDAO.save(waterintakes[0])
    waterintakeDAO.save(waterintakes[1])
    waterintakeDAO.save(waterintakes[2])
    return waterintakeDAO
}
fun populateGoalTable(): GoalDAO {
    SchemaUtils.create(Goals)
    val goalDAO = GoalDAO()
    goalDAO.save(goals[0])
    goalDAO.save(goals[1])
    goalDAO.save(goals[2])
    return goalDAO
}