package data.db

import org.jetbrains.exposed.sql.Database
//import java.sql.Connection
//import java.sql.DriverManager

fun dbConnect() {
    val url = "jdbc:postgresql://localhost:5432/postgres"
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

//    try {
//        Class.forName(driver)
//        val connection: Connection = DriverManager.getConnection(url, user, password)
//        println("Connected to the database!")
//        // Ваш код работы с базой данных здесь
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
}