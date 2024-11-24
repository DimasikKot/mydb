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

class TablesReportEmployeeViewModel : ViewModel() {
    var report by mutableIntStateOf(0)

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    private var request by mutableStateOf(
        "SELECT ROW_NUMBER() OVER(ORDER BY strings.date NULLS LAST) AS number, strings.date AS date_give, strings.device_id AS id, devices.name, devices.date, devices.price, devices.type_id AS type_id, devices.type_name AS type_name " +
                "FROM strings " +
                "JOIN devices ON strings.device_id = devices.id " +
                "JOIN types ON devices.type_id = types.id " +
                "WHERE strings.device_id = $report " +
                "ORDER BY strings.date"
    )
    var order1 by mutableStateOf("strings.date")
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

    fun employeeGet(): ReportEmployeeFromTables {
        try {
            val requestEmployee =
                "SELECT employees.id, employees.name, employees.group_id, groups.name AS group_name, " +
                            "(SELECT SUM(devices.price) " +
                            "FROM devices " +
                            "JOIN " +
                                "(SELECT strings.device_id, MAX(strings.date) AS latest_date " +
                                    "FROM strings " +
                                    "WHERE strings.employee_id = $report " +
                                    "GROUP BY strings.device_id) AS latest " +
                                "ON devices.id = latest.device_id " +
                            "JOIN strings ON devices.id = strings.device_id AND strings.date = latest.latest_date " +
                            "WHERE strings.employee_id = $report) AS total_price " +
                        "FROM employees " +
                        "JOIN groups " +
                            "ON employees.group_id = groups.id " +
                        "WHERE employees.id = $report " +
                        "LIMIT 1"
            return transaction {
                exec(requestEmployee) { row ->
                    if (row.next()) {
                        ReportEmployeeFromTables(
                            id = row.getInt("id"),
                            name = row.getString("name"),
                            groupId = row.getInt("group_id"),
                            groupName = row.getString("group_name"),
                            totalPrice = row.getInt("total_price")
                        )
                    } else {
                        ReportEmployeeFromTables(-1, "REQUEST", -1, "$row", -1)
                    }
                }!!
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ReportEmployeeFromTables(-1, "DB", -1, "$e", -1)
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

    fun listGet(): List<ReportEmployeeStringFromTables> {
        try {
            return transaction {
                val result = exec(request) { row ->
                    generateSequence {
                        if (row.next()) {
                            ReportEmployeeStringFromTables(
                                number = row.getInt("number"),
                                giveDate = row.getString("date_give"),
                                id = row.getInt("id"),
                                name = row.getString("name"),
                                date = row.getString("date"),
                                price = row.getInt("price"),
                                typeId = row.getInt("type_id"),
                                typeName = row.getString("type_name"),
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

data class ReportEmployeeFromTables(
    val id: Int,
    val name: String,
    val groupId: Int,
    val groupName: String,
    val totalPrice: Int,
)

data class ReportEmployeeStringFromTables(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val number: Int,
    val giveDate: String,
    val id: Int,
    val name: String,
    val date: String,
    val price: Int,
    val typeId: Int,
    val typeName: String,
)