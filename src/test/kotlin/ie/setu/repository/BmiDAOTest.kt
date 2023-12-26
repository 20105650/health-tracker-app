package ie.setu.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ie.setu.domain.db.Bmis
import ie.setu.domain.Bmi
import ie.setu.domain.repository.BmiDAO
import ie.setu.helpers.bmis
import ie.setu.helpers.populateActivityTable
import ie.setu.helpers.populateBmiTable
import ie.setu.helpers.populateUserTable
import kotlin.test.assertEquals

//retrieving some test data from Fixtures
private val bmi1 = bmis.get(0)


class BmiDAOTest {
    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }
    @Nested
    inner class CreateActivities {

        @Test
        fun `multiple bmis added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()
                //Act & Assert
                assertEquals(1, bmiDAO.getAll().size)
                assertEquals(bmi1, bmiDAO.findByBmiId(bmi1.id))

            }
        }
    }

}