package data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.jetbrains.exposed.sql.Table

object TypesTable : Table("types") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Types_ID")
}

object DevicesTable : Table("devices") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val date = varchar("date", 16)
    val price = integer("price")
    val typeId = integer("type_id") references TypesTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_Devices_ID")
}

object StringsTable : Table("strings") {
    val id = integer("id").autoIncrement()
    val deviceId = integer("device_id") references DevicesTable.id
    val date = varchar("date", 16)
    val employeeId = integer("employee_id") references EmployeesTable.id

    override val primaryKey = PrimaryKey(id, deviceId, name = "PK_Strings_ID")
}

object EmployeesTable : Table("employees") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val groupId = integer("group_id") references GroupsTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_Employees_ID")
}

object GroupsTable : Table("groups") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Groups_ID")
}