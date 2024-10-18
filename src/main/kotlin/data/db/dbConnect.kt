package data.db

import composable.db.DbDevices
import composable.db.DbEmployees
import composable.db.DbGroups
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

//import java.sql.Connection
//import java.sql.DriverManager

fun dbConnect() {
    val url = "jdbc:postgresql://localhost:5432/mydb"
    val driver = "org.postgresql.Driver"
    val user = "postgres"
    val password = "Dima2004"

    try {
        Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

    transaction {
        SchemaUtils.create(DbDevices)
    }

//    try {
//        Class.forName(driver)
//        val connection: Connection = DriverManager.getConnection(url, user, password)
//        println("Connected to the database!")
//        // Ваш код работы с базой данных здесь
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
}