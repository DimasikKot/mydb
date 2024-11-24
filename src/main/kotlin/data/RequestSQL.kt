package data

import org.jetbrains.exposed.sql.transactions.transaction

fun requestSQL(request: String): String {
    try {
        return transaction {
            exec(request).toString()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return e.toString()
    }
}