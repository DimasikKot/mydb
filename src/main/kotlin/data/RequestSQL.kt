package data

import data.viewModels.ReportEmployeeStringFromTables
import org.jetbrains.exposed.sql.transactions.transaction

fun requestSQL(request: String): String {
    try {
        var result = ""
        transaction {
            exec(request) { row ->
                if (row.next()) {
                    result += row.next().toString()
                }
            }
        }
        return result
    } catch (e: Exception) {
        return e.toString()
    }
}