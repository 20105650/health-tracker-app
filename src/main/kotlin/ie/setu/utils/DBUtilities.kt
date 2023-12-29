package ie.setu.utils

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.domain.Bmi
import ie.setu.domain.Waterintake
import ie.setu.domain.db.Bmis
import ie.setu.domain.db.Waterintakes
import ie.setu.domain.Goal
import ie.setu.domain.db.Goals
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email]
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    weight = it[Activities.weight],
    activity = it[Activities.activity],
    duration = it[Activities.duration],
    type =it[Activities.type],
    createdat = it[Activities.createdat],
    calories = it[Activities.calories],
    user_id = it[Activities.user_id]
)


fun mapToBmi(it: ResultRow) = Bmi(
    id = it[Bmis.id],
    weight = it[Bmis.weight],
    height = it[Bmis.height],
    bmival = it[Bmis.bmival],
    bmiresult = it[Bmis.bmiresult],
    user_id = it[Bmis.userId],
    createdat = it[Bmis.createdat]
)

fun mapToWaterintake(it: ResultRow) = Waterintake(
    id = it[Waterintakes.id],
    weight = it[Waterintakes.weight],
    waterconsumed = it[Waterintakes.waterconsumed],
    dailyrequired = it[Waterintakes.dailyrequired],
    user_id = it[Waterintakes.userId],
    createdat = it[Waterintakes.createdat]
)
fun mapToGoal(it: ResultRow) = Goal(
    id = it[Goals.id],
    goal_category = it[Goals.goal_category],
    description = it[Goals.description],
    user_id = it[Goals.userId],
    createdat = it[Goals.createdat]
)