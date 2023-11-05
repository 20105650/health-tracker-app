package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one BMI.

object Bmis : Table("bmis") {
    val id = integer("id").autoIncrement().primaryKey()
    val weight = double("weight")
    val height = double("height")
    val age = integer("age")
    val community = varchar("community", 100)
    val bmival = double("bmival")
    val bmiresult = varchar("bmiresult", 100)
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val createdat =datetime("createdat")
}