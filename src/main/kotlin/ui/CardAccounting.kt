package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun CardAccounting(
    deviceId: Int,
    stringsIds: List<Int>,
    modifier: Modifier = Modifier
) {
    transaction {
        SchemaUtils.create(DbTypes, DbDevices, DbStrings, DbEmployees, DbGroups)
        val dbStrings = DbStrings.selectAll()
        dbStrings.forEach {
            if (it[DbStrings.deviceId] == deviceId && it[DbStrings.id] in stringsIds)
                println("")
        }
    }
}

//        Usersik.insert {
//            it[username] = "john_doe"
//            it[email] = "john@example.com"
//        }
//        Usersik.insert {
//            it[username] = "222"
//            it[email] = "john3@23232323.com"
//        }


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