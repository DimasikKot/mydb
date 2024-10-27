package data.db

import org.jetbrains.exposed.sql.Database

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

//    try {
//        Class.forName(driver)
//        val connection: Connection = DriverManager.getConnection(url, user, password)
//
//        val resultSet = connection.createStatement().executeQuery(
//            """
//                SELECT device_id, date, employee_id
//                FROM dbstrings
//                WHERE dbstrings.device_id = 11
//                ORDER BY date
//            """
//        )
//
//        while (resultSet.next()) {
//            //Что делать с элементами
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
}