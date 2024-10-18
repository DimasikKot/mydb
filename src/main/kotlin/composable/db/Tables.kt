package composable.db

import org.jetbrains.exposed.sql.Table

object DbTypes : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Types_ID")
}

data class DbType(val id: Int, val name: String)


object DbDevices : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val date = varchar("date", 16)
    val price = integer("price")
    val typeId = integer("type_id") references DbTypes.id

    override val primaryKey = PrimaryKey(id, name = "PK_Devices_ID")
}

data class DbDevice(val id: Int, val name: String, val date: String, val price: Int, val typeId: Int)


object DbStrings : Table() {
    val id = integer("id").autoIncrement()
    val deviceId = integer("device_id") references DbDevices.id
    val date = varchar("date", 16)
    val employeeId = integer("employee_id") references DbEmployees.id

    override val primaryKey = PrimaryKey(id, deviceId, name = "PK_Strings_ID")
}

data class DbString(val id: Int, val deviceId: Int, val date: String, val employeeId: Int)


object DbEmployees : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val groupId = integer("group_id") references DbGroups.id

    override val primaryKey = PrimaryKey(id, name = "PK_Employees_ID")
}

data class DbEmployee(val id: Int, val name: String, val groupId: Int)


object DbGroups : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Groups_ID")
}

data class DbGroup(val id: Int, val name: String)