package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one activity.
//       Database wise, this is the table object.

object Activities : Table("activities") {
    val id = integer("id").autoIncrement().primaryKey()
    val activity = varchar("activity", 100)
    val weight = double("weight")
    val type = varchar("type", 100)
    val duration = double("duration")
    val calories = double("calories")
    val createdat = datetime("createdat")
    val user_id = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}