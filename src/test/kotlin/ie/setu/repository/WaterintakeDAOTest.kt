package ie.setu.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ie.setu.domain.Waterintake
import ie.setu.domain.db.Waterintakes
import ie.setu.domain.repository.WaterintakeDAO
import ie.setu.helpers.waterintakes
import ie.setu.helpers.populateWaterintakeTable
import ie.setu.helpers.populateUserTable
import kotlin.test.assertEquals

//retrieving some test data from Fixtures
private val waterintake1 = waterintakes.get(0)
private val waterintake2 = waterintakes.get(1)
private val waterintake3 = waterintakes.get(2)


class WaterintakeDAOTest {
    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }
    @Nested
    inner class CreateWaterintakes {

        @Test
        fun `multiple waterintakes added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val waterintakeDAO = populateWaterintakeTable()
                //Act & Assert
                assertEquals(3, waterintakeDAO.getAll().size)
                assertEquals(waterintake1, waterintakeDAO.findByWaterintakeId(waterintake1.id))

            }
        }
    }

    @Nested
    inner class ReadWaterintakes {

        @Test
        fun `getting all waterintakes from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three waterintakes
                val userDAO = populateUserTable()
                val waterintakeDAO = populateWaterintakeTable()
                //Act & Assert
                assertEquals(3, waterintakeDAO.getAll().size)
            }
        }

        @Test
        fun `get waterintake by user id that has no waterintakes, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three waterintakes
                val userDAO = populateUserTable()
                val waterintakeDAO = populateWaterintakeTable()
                //Act & Assert
                assertEquals(0, waterintakeDAO.findByUserId(6).size)
            }
        }

        @Test
        fun `get waterintake by user id that exists, results in a correct waterintake(s) returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three waterintakes
                val userDAO = populateUserTable()
                val waterintakeDAO = populateWaterintakeTable()
                //Act & Assert
                assertEquals(waterintake1, waterintakeDAO.findByUserId(1).get(0))
            }
        }

        @Test
        fun `get all waterintakes over empty table returns none`() {
            transaction {

                //Arrange - create and setup activityDAO object
                SchemaUtils.create(Waterintakes)
                val waterintakeDAO = WaterintakeDAO()

                //Act & Assert
                assertEquals(0, waterintakeDAO.getAll().size)
            }
        }

        @Test
        fun `get waterintake by waterintake id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three waterintakes
                val userDAO = populateUserTable()
                val waterintakeDAO = populateWaterintakeTable()
                //Act & Assert
                assertEquals(null, waterintakeDAO.findByWaterintakeId(4))
            }
        }

        @Test
        fun `get waterintake by waterintake id that exists, results in a correct waterintake returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three waterintakes
                val userDAO = populateUserTable()
                val waterintakeDAO = populateWaterintakeTable()
                //Act & Assert
                assertEquals(waterintake1, waterintakeDAO.findByWaterintakeId(1))
            }
        }
    }
}