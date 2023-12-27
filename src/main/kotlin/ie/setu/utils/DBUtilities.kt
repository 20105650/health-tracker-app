package ie.setu.utils

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.domain.Bmi
import ie.setu.domain.Waterintake
import ie.setu.domain.db.Bmis
import ie.setu.domain.db.Waterintakes
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email],
    dob = it[Users.dob]?.toDate()
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId]
)


fun mapToBmi(it: ResultRow) = Bmi(
    id = it[Bmis.id],
    weight = it[Bmis.weight],
    height = it[Bmis.height],
    age = it[Bmis.age],
    community = it[Bmis.community],
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