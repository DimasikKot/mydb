package data

import data.viewModels.MainViewModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

//import java.sql.DriverManager

fun dbConnect(mainVM: MainViewModel) {
    try {
        Database.connect(
            url = mainVM.url,
            driver = mainVM.driver,
            user = mainVM.user,
            password = mainVM.password
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

//    transaction {
//        SchemaUtils.create(TypesTable, DevicesTable, StringsTable, EmployeesTable, GroupsTable)
//    }

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