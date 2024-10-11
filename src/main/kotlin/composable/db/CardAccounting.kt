package composable.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun CardAccounting(
    deviceId: Int
): List<CardAcc> {
    val strings: MutableList<CardAcc> = mutableListOf()
    transaction {
        val dbStrings = DbStrings.selectAll()
        val deviceIsPrinted = false

        dbStrings.forEach {
            strings.addLast(
                CardAcc(
                    it[DbDevices.id],
                    it[DbStrings.id],
                    it[DbStrings.date],
                    it[DbEmployees.id],
                    it[DbEmployees.name],
                    it[DbGroups.id],
                    it[DbGroups.name],
                )
            )
        }
    }
    return strings
}

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

object DbTypes : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Types_ID") // name is optional here
}

object DbDevices : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val date = varchar("date", 16)
    val price = integer("price")
    val typeId: Column<Int?> = (integer("type_id") references DbTypes.id).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Devices_ID") // name is optional here
}

object DbStrings : Table() {
    val id = integer("id").autoIncrement()
    val deviceId: Column<Int?> = (integer("device_id") references DbDevices.id).nullable()
    val date = varchar("date", 16)
    val employeeId: Column<Int?> = (integer("employee_id") references DbEmployees.id).nullable()

    override val primaryKey = PrimaryKey(id, deviceId, name = "PK_User_ID") // name is optional here
}

object DbEmployees : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val groupId: Column<Int?> = (integer("type_id") references DbGroups.id).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Employees_ID") // name is optional here
}

object DbGroups : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Groups_ID") // name is optional here
}