package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one BMI.

object Waterintakes : Table("waterintakes") {
    val id = integer("id").autoIncrement().primaryKey()
    val weight = double("weight")
    val waterconsumed = double("waterconsumed")
    val dailyrequired = double("dailyrequired")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val createdat =datetime("createdat")
}