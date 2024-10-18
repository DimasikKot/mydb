package composable.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

//fun CardAccounting(
//    deviceId: Int
//): List<CardAcc> {
//    val strings: MutableList<CardAcc> = mutableListOf()
//    transaction {
//        val dbStrings = DbStrings.selectAll()
//        val deviceIsPrinted = false
//
//        dbStrings.forEach {
//            strings.addLast(
//                CardAcc(
//                    it[DbDevices.id],
//                    it[DbStrings.id],
//                    it[DbStrings.date],
//                    it[DbEmployees.id],
//                    it[DbEmployees.name],
//                    it[DbGroups.id],
//                    it[DbGroups.name],
//                )
//            )
//        }
//    }
//    return strings
//}

data class CardAcc(val a1: Int, val a2: Int, val a3: String, val a4: Int, val a5: String, val a6: Int, val a7: String)

fun insertMore() {
    transaction {
        DbGroups.insert {
            it[name] = "math"
        }
        DbGroups.insert {
            it[name] = "rus"
        }
//        DbEmployees.insert {
//            it[name] = "Dima"
//            it[groupId]
//        }
    }
}

