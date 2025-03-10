package data.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.EmployeesTable
import data.StringsTable
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

@OptIn(DelicateCoroutinesApi::class)
class TablesReportGroupViewModel : ViewModel() {
    var report by mutableIntStateOf(0)

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    private var _list by mutableStateOf<List<ReportGroupStringFromTables>>(emptyList())
    val list: List<ReportGroupStringFromTables>
        get() = _list

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
                """
                SELECT groups.id, groups.name, 
                    (SELECT COUNT(1) 
                    FROM employees AS e2 
                    WHERE e2.group_id = groups.id) AS employees_count, 
                    (SELECT SUM(
                        (SELECT SUM(devices.price) 
                        FROM strings 
                        JOIN devices ON devices.id = strings.device_id 
                        WHERE strings.employee_id = employees.id AND strings.date = 
                            (SELECT MAX(s2.date) 
                            FROM strings AS s2 
                            WHERE strings.device_id = s2.device_id 
                            GROUP BY s2.device_id))) 
                    FROM employees 
                    WHERE employees.group_id = groups.id) AS total_price_group 
                FROM groups 
                WHERE groups.id = $report 
                LIMIT 1
                """.trimIndent()
            return transaction {
                exec(requestEmployee) { row ->
                    if (row.next()) {
                        ReportGroupFromTables(
                            id = row.getInt("id"),
                            name = row.getString("name"),
                            employeesCount = row.getInt("employees_count"),
                            totalPrice = row.getInt("total_price_group")
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

    private suspend fun listGet(): List<ReportGroupStringFromTables> {
        return withContext(Dispatchers.IO) {
            try {
                transaction {
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
                emptyList()
            }
        }
    }

    fun listUpdate() {
        var requestNew =
            """
                SELECT ROW_NUMBER() OVER(ORDER BY employees.name NULLS LAST) AS number, employees.id, employees.name, 
                    (SELECT SUM(devices.price) 
                    FROM strings 
                    JOIN devices ON devices.id = strings.device_id 
                    WHERE strings.employee_id = employees.id AND strings.date = 
                        (SELECT MAX(s2.date) 
                        FROM strings AS s2 
                        WHERE devices.id = s2.device_id 
                        GROUP BY s2.device_id)) AS total_price 
                FROM groups 
                JOIN employees ON employees.group_id = groups.id 
                WHERE groups.id = $report 
            """.trimIndent()
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
            requestNew += " AND " + conditions.joinToString(" AND ")
        }
        requestNew += " ORDER BY $order1"
        requestNew += if (order2.isNotEmpty()) ", $order2" else ""
        requestNew += if (order3.isNotEmpty()) ", $order3" else ""
        requestNew += if (order4.isNotEmpty()) ", $order4" else ""
        requestNew += if (order5.isNotEmpty()) ", $order5" else ""
        requestNew += if (order6.isNotEmpty()) ", $order5" else ""
        request = ""
        request = requestNew
        GlobalScope.launch {
            _list = listGet()
        }
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
    val employeesCount: Int = -1,
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