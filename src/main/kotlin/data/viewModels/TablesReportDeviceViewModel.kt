package data.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.StringsTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TablesReportDeviceViewModel : ViewModel() {
    var report by mutableIntStateOf(0)

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    private var request by mutableStateOf("")
    var order1 by mutableStateOf("date")
    private var order2 by mutableStateOf("")
    private var order3 by mutableStateOf("")
    private var order4 by mutableStateOf("")
    private var order5 by mutableStateOf("")
    private var order6 by mutableStateOf("")
    var whereNumber by mutableStateOf("")
    var whereId by mutableStateOf("")
    var whereDate by mutableStateOf("")
    var whereEmployeeID by mutableStateOf("")
    var whereEmployeeName by mutableStateOf("")
    var whereGroupId by mutableStateOf("")
    var whereGroupName by mutableStateOf("")

    fun headGet(): ReportDeviceFromTables {
        try {
            val requestDevice =
                "SELECT devices.id, devices.name, devices.date, devices.price, devices.type_id, types.name AS type_name " +
                        "FROM devices JOIN types ON devices.type_id = types.id " +
                        "WHERE devices.id = $report " +
                        "LIMIT 1"
            return transaction {
                exec(requestDevice) { row ->
                    if (row.next()) {
                        ReportDeviceFromTables(
                            id = row.getInt("id"),
                            name = row.getString("name"),
                            date = row.getString("date"),
                            price = row.getInt("price"),
                            typeId = row.getInt("type_id"),
                            typeName = row.getString("type_name")
                        )
                    } else {
                        ReportDeviceFromTables(-1, "REQUEST", "REQUEST", -1, -1, "$row")
                    }
                }!!
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ReportDeviceFromTables(-1, "DB", "DB", -1, -1, "$e")
        }
    }

    fun listGet(): List<ReportStringFromTables> {
        try {
            return transaction {
                val result = exec(request) { row ->
                    generateSequence {
                        if (row.next()) {
                            ReportStringFromTables(
                                number = row.getInt("number"),
                                id = row.getInt("id"),
                                date = row.getString("date"),
                                employeeID = row.getInt("employee_id"),
                                employeeName = row.getString("employee_name"),
                                groupId = row.getInt("group_id"),
                                groupName = row.getString("group_name"),
                            )
                        } else {
                            null
                        }
                    }.toList()
                }
                println()
                result ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    fun listUpdate() {
        var requestNew =
            "SELECT ROW_NUMBER() OVER(ORDER BY strings.date NULLS LAST) AS number, strings.id, strings.date, strings.employee_id, employees.name AS employee_name, employees.group_id AS group_id, groups.name AS group_name " +
                    "FROM strings " +
                    "JOIN employees ON strings.employee_id = employees.id " +
                    "JOIN groups ON employees.group_id = groups.id " +
                    "WHERE strings.device_id = $report"
        val conditions = mutableListOf<String>()
        if (whereId.isNotEmpty()) {
            conditions.add("strings.id >= $whereId")
        }
        if (whereDate.isNotEmpty()) {
            conditions.add("strings.date LIKE '%$whereDate%'")
        }
        if (whereEmployeeID.isNotEmpty()) {
            conditions.add("strings.employee_id >= $whereEmployeeID")
        }
        if (whereEmployeeName.isNotEmpty()) {
            conditions.add("employees.name LIKE '%$whereEmployeeName%'")
        }
        if (whereGroupId.isNotEmpty()) {
            conditions.add("employees.group_id >= $whereGroupId")
        }
        if (whereGroupName.isNotEmpty()) {
            conditions.add("groups.name LIKE '%$whereGroupName%'")
        }
        if (conditions.isNotEmpty()) {
            requestNew += " and " + conditions.joinToString(" and ")
        }
        requestNew += " ORDER BY $order1"
        requestNew += if (order2.isNotEmpty()) ", $order2" else ""
        requestNew += if (order3.isNotEmpty()) ", $order3" else ""
        requestNew += if (order4.isNotEmpty()) ", $order4" else ""
        requestNew += if (order5.isNotEmpty()) ", $order5" else ""
        requestNew += if (order6.isNotEmpty()) ", $order5" else ""
        request = ""
        request = requestNew
    }

    fun listOrderBy(order: String): Boolean {
        var descending = false
        if (order1.isEmpty()) {
            order1 = order
        } else if (order1 == order) {
            order1 = "$order DESC"
            descending = true
        } else if (order1 == "$order DESC") {
            order1 = order
        } else {
            order6 = order5
            order5 = order4
            order4 = order3
            order3 = order2
            order2 = order1
            order1 = order
        }
        listUpdate()
        return descending
    }

    fun delete(itId: Int): Boolean {
        try {
            transaction {
                StringsTable.deleteWhere { id.eq(itId) }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun update(
        itId: Int,
        newId: Int,
        newDate: String,
        newEmployeeId: Int,
    ): Boolean {
        try {
            transaction {
                StringsTable.update({ StringsTable.id.eq(itId) and StringsTable.deviceId.eq(report) }) {
                    it[id] = newId
                    it[date] = newDate
                    it[employeeId] = newEmployeeId
                }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun insert(
        newId: String,
        newDate: String,
        newEmployeeId: Int,
    ): Boolean {
        try {
            transaction {
                if (newId == "") {
                    StringsTable.insert {
                        it[date] = newDate
                        it[employeeId] = newEmployeeId
                        it[deviceId] = report
                    }
                } else {
                    StringsTable.insert {
                        it[id] = newId.toInt()
                        it[date] = newDate
                        it[employeeId] = newEmployeeId
                        it[deviceId] = report
                    }
                }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}

data class ReportDeviceFromTables(
    val id: Int,
    val name: String,
    val date: String,
    val price: Int,
    val typeId: Int,
    val typeName: String,
)

data class ReportStringFromTables(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val number: Int,
    val id: Int,
    val date: String,
    val employeeID: Int,
    val employeeName: String,
    var groupId: Int,
    var groupName: String,
)