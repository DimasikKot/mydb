package data

import org.jetbrains.exposed.sql.Table

object TypesTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Types_ID")
}

data class TypeFromTable(val id: Int, val name: String)


object DevicesTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val date = varchar("date", 16)
    val price = integer("price")
    val typeId = integer("type_id") references TypesTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_Devices_ID")
}

data class DeviceFromTable(val id: Int, val name: String, val date: String, val price: Int, val typeId: Int)


object StringsTable : Table() {
    val id = integer("id").autoIncrement()
    val deviceId = integer("device_id") references DevicesTable.id
    val date = varchar("date", 16)
    val employeeId = integer("employee_id") references EmployeesTable.id

    override val primaryKey = PrimaryKey(id, deviceId, name = "PK_Strings_ID")
}

data class StringFromTable(val id: Int, val deviceId: Int, val date: String, val employeeId: Int)


object EmployeesTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val groupId = integer("group_id") references GroupsTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_Employees_ID")
}

data class EmployeeFromTable(val id: Int, val name: String, val groupId: Int)


object GroupsTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Groups_ID")
}

data class GroupFromTable(val id: Int, val name: String)