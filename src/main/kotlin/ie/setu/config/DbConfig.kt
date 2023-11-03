package ie.setu.config

import org.jetbrains.exposed.sql.Database

class DbConfig {
    fun getDbConnection() :Database{

        val PGHOST = "trumpet.db.elephantsql.com"
        val PGPORT = "5432"
        val PGUSER = "brvgacan"
        val PGPASSWORD = "4Cgg0RRk_FRDalXKQ7HZP4mpn4V0boc_"
        val PGDATABASE = "brvgacan"

        //url format should be jdbc:postgresql://host:port/database
        val dbUrl = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        val dbConfig = Database.connect(
            url = dbUrl,
            driver="org.postgresql.Driver",
            user = PGUSER,
            password = PGPASSWORD
        )

        return dbConfig
    }
}