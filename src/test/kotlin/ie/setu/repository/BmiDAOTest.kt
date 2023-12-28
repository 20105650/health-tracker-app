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
    inner class CreateBmis {

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

    @Nested
    inner class ReadBmis {

        @Test
        fun `getting all bmis from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and one bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()
                //Act & Assert
                assertEquals(1, bmiDAO.getAll().size)
            }
        }

        @Test
        fun `get bmi by user id that has no bmis, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and one bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()
                //Act & Assert
                assertEquals(0, bmiDAO.findByUserId(2).size)
            }
        }

        @Test
        fun `get bmi by user id that exists, results in a correct bmi(s) returned`() {
            transaction {
                //Arrange - create and populate tables with three users and one bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()
                //Act & Assert
                assertEquals(bmi1, bmiDAO.findByUserId(1).get(0))
            }
        }

        @Test
        fun `get all bmis over empty table returns none`() {
            transaction {

                //Arrange - create and setup activityDAO object
                SchemaUtils.create(Bmis)
                val bmiDAO = BmiDAO()

                //Act & Assert
                assertEquals(0, bmiDAO.getAll().size)
            }
        }

        @Test
        fun `get bmi by bmi id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and one bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()
                //Act & Assert
                assertEquals(null, bmiDAO.findByBmiId(4))
            }
        }

        @Test
        fun `get bmi by bmi id that exists, results in a correct bmi returned`() {
            transaction {
                //Arrange - create and populate tables with three users and one bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()
                //Act & Assert
                assertEquals(bmi1, bmiDAO.findByBmiId(1))
            }
        }
    }

/*
    @Nested
    inner class UpdateBmis {

        @Test
        fun `updating existing bmi in table results in successful update`() {
            transaction {

                //Arrange - create and populate tables with three users and one bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()

                //Act & Assert
                val bmi3updated = Bmi(id = 1, weight =99.50 , height =165.50, age = 25, community="Asian", bmival=33.88,bmiresult="obese",createdat = DateTime.now(), user_id = 1)
                bmiDAO.updateByBmiId(bmi3updated.id, bmi3updated)
                assertEquals(bmi3updated, bmiDAO.findByBmiId(3))
            }
        }

        @Test
        fun `updating non-existant bmi in table results in no updates`() {
            transaction {

                //Arrange - create and populate tables with three users and one bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()

                //Act & Assert
                val bmi4updated = Activity(id = 4, description = "Cardio", duration = 42.0,
                    calories = 220, started = DateTime.now(), userId = 2)
                bmiDAO.updateByBmiId(4, bmi4updated)
                assertEquals(null, bmiDAO.findByBmiId(4))
                assertEquals(3, bmiDAO.getAll().size)
            }
        }
    }
*/
    @Nested
    inner class DeleteBmis {
/*
        @Test
        fun `deleting a non-existant bmi (by id) in table results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and one  bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()

                //Act & Assert
                assertEquals(1, bmiDAO.getAll().size)
                bmiDAO.deleteByBmiId(4)
                assertEquals(1, bmiDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing activity (by id) in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteByActivityId(activity3.id)
                assertEquals(2, activityDAO.getAll().size)
            }
        }


        @Test
        fun `deleting activities when none exist for user id results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteByUserId(3)
                assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `deleting activities when 1 or more exist for user id results in deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteByUserId(1)
                assertEquals(1, activityDAO.getAll().size)
            }
        }  */
    }


}