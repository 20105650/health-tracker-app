package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one BMI.

object Goals : Table("goals") {
    val id = integer("id").autoIncrement().primaryKey()
    val goal_category = varchar("goal_category",1000)
    val description = varchar("description",1000)
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val createdat =datetime("createdat")
}