package composable.db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object DbTypes : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Types_ID") // name is optional here
}

data class DbType(val id: Int, val name: String)

object DbDevices : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val date = varchar("date", 16)
    val price = integer("price")
    val typeId = integer("type_id") references DbTypes.id

    override val primaryKey = PrimaryKey(id, name = "PK_Devices_ID") // name is optional here
}

data class DbDevice(val id: Int, val name: String, val date: String, val price: Int, val typeId: Int) {
}

object DbEmployees : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val groupId: Column<Int?> = (integer("type_id") references DbGroups.id).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Employees_ID") // name is optional here
}

data class DbEmployee(val id: Int, val name: String, val groupId: Int)

object DbGroups : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Groups_ID") // name is optional here
}

data class DbGroup(val id: Int, val name: String)

//object DbStrings : Table() {
//    val id = integer("id").autoIncrement()
//    val deviceId: Column<Int?> = (integer("device_id") references DbDevices.id).nullable()
//    val date = varchar("date", 16)
//    val employeeId: Column<Int?> = (integer("employee_id") references DbEmployees.id).nullable()
//
//    override val primaryKey = PrimaryKey(id, deviceId, name = "PK_User_ID") // name is optional here
//}