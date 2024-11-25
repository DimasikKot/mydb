package data.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.EmployeesTable
import data.StringsTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TablesReportGroupViewModel : ViewModel() {
    var report by mutableIntStateOf(0)

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    private var request by mutableStateOf("")
    var order1 by mutableStateOf("name")
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

    fun headGet(): ReportGroupFromTables {
        try {
            val requestEmployee =
                "SELECT groups.id, groups.name, (" +
                            "SELECT SUM(devices.price) " +
                            "FROM strings " +
                            "JOIN devices ON devices.id = strings.device_id " +
                            "JOIN (" +
                                "SELECT MAX(date) AS date_give_latest " +
                                "FROM strings " +
                                "GROUP BY device_id" +
                            ") ON date_give_latest = strings.date " +
                            "WHERE strings.employee_id = $report" +
                        ") AS total_price " +
                        "FROM groups " +
                        "JOIN groups " +
                        "ON employees.group_id = groups.id " +
                        "WHERE employees.id = $report " +
                        "LIMIT 1"
            return transaction {
                exec(requestEmployee) { row ->
                    if (row.next()) {
                        ReportGroupFromTables(
                            id = row.getInt("id"),
                            name = row.getString("name"),
                            totalPrice = row.getInt("total_price")
                        )
                    } else {
                        ReportGroupFromTables(name = "$row")
                    }
                }!!
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ReportGroupFromTables(name = "$e")
        }
    }

    fun listGet(): List<ReportGroupStringFromTables> {
        try {
            return transaction {
                val result = exec(request) { row ->
                    generateSequence {
                        if (row.next()) {
                            ReportGroupStringFromTables(
                                number = row.getInt("number"),
                                id = row.getInt("id"),
                                name = row.getString("name"),
                                totalPrice = row.getInt("total_price"),
                            )
                        } else {
                            null
                        }
                    }.toList()
                }
                result ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    fun listUpdate() {
        var requestNew =
            "SELECT ROW_NUMBER() OVER(ORDER BY employees.name NULLS LAST) AS number, employees.id, employees.name, (" +
                        "SELECT SUM(devices.price) " +
                        "FROM strings " +
                        "JOIN devices ON devices.id = strings.device_id " +
                        "JOIN (" +
                            "SELECT MAX(date) AS date_give_latest " +
                            "FROM strings " +
                            "GROUP BY device_id" +
                        ") ON date_give_latest = strings.date " +
                        "WHERE strings.employee_id = employees.id" +
                    ") AS total_price " +
                    "FROM groups " +
                    "JOIN employees " +
                    "ON employees.group_id = groups.id " +
                    "WHERE groups.id = $report "
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
                EmployeesTable.deleteWhere { id.eq(itId) }
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

data class ReportGroupFromTables(
    val id: Int = -1,
    val name: String = "null",
    val totalPrice: Int = -1,
)

data class ReportGroupStringFromTables(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val number: Int = -1,
    val id: Int = -1,
    val name: String = "null",
    val totalPrice: Int = -1,
)