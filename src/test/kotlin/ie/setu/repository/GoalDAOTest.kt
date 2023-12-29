package ie.setu.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ie.setu.domain.Goal
import ie.setu.domain.db.Goals
import ie.setu.domain.repository.GoalDAO
import ie.setu.helpers.goals
import ie.setu.helpers.populateGoalTable
import ie.setu.helpers.populateUserTable
import kotlin.test.assertEquals

//retrieving some test data from Fixtures
private val goal1 = goals.get(0)
private val goal2 = goals.get(1)
private val goal3 = goals.get(2)


class GoalDAOTest {
    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }
    @Nested
    inner class CreateGoals {

        @Test
        fun `multiple goals added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three users and three goals
                val userDAO = populateUserTable()
                val goalDAO = populateGoalTable()
                //Act & Assert
                assertEquals(3, goalDAO.getAll().size)
                assertEquals(goal1, goalDAO.findByGoalId(2))

            }
        }
    }

    @Nested
    inner class ReadGoals {

        @Test
        fun `getting all goals from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three goals
                val userDAO = populateUserTable()
                val goalDAO = populateGoalTable()
                //Act & Assert
                assertEquals(3, goalDAO.getAll().size)
            }
        }

        @Test
        fun `get goal by user id that has no goals, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three goals
                val userDAO = populateUserTable()
                val goalDAO = populateGoalTable()
                //Act & Assert
                assertEquals(0, goalDAO.findByUserId(6).size)
            }
        }

        @Test
        fun `get goal by user id that exists, results in a correct goal(s) returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three goals
                val userDAO = populateUserTable()
                val goalDAO = populateGoalTable()
                //Act & Assert
                assertEquals(goal1, goalDAO.findByUserId(1).get(0))
            }
        }

        @Test
        fun `get all goals over empty table returns none`() {
            transaction {

                //Arrange - create and setup activityDAO object
                SchemaUtils.create(Goals)
                val goalDAO = GoalDAO()

                //Act & Assert
                assertEquals(0, goalDAO.getAll().size)
            }
        }

        @Test
        fun `get goal by goal id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three goals
                val userDAO = populateUserTable()
                val goalDAO = populateGoalTable()
                //Act & Assert
                assertEquals(null, goalDAO.findByGoalId(4))
            }
        }

        @Test
        fun `get goal by goal id that exists, results in a correct goal returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three goals
                val userDAO = populateUserTable()
                val goalDAO = populateGoalTable()
                //Act & Assert
                assertEquals(goal1, goalDAO.findByGoalId(1))
            }
        }
    }
}