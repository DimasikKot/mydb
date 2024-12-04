package data.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.StringsTable
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

@OptIn(DelicateCoroutinesApi::class)
class TablesReportDeviceViewModel(private val _report: Int) : ViewModel() {
    private var _head by mutableStateOf(ReportDeviceFromTables())
    private var _list by mutableStateOf<List<ReportDeviceStringFromTables>>(emptyList())
    private var _request by mutableStateOf("")

    private var _order1 by mutableStateOf("date DESC")
    private var _order2 by mutableStateOf("")
    private var _order3 by mutableStateOf("")
    private var _order4 by mutableStateOf("")
    private var _order5 by mutableStateOf("")
    private var _order6 by mutableStateOf("")

    private var _whereNumber by mutableStateOf("")
    private var _whereId by mutableStateOf("")
    private var _whereDate by mutableStateOf("")
    private var _whereEmployeeID by mutableStateOf("")
    private var _whereEmployeeName by mutableStateOf("")
    private var _whereGroupId by mutableStateOf("")
    private var _whereGroupName by mutableStateOf("")

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    val head: ReportDeviceFromTables
        get() = _head
    val list: List<ReportDeviceStringFromTables>
        get() {
            if (_head.id == -1 && _list.isEmpty()) {
                _headUpdate()
                _requestUpdate()
            }
            return _list
        }

    var order1: String
        get() {
            return _order1
        }
        set(value) {
            _listOrderBy(value)
            _requestUpdate()
        }

    var whereNumber: String
        get() {
            return _whereNumber
        }
        set(value) {
            _whereNumber = value
            _requestUpdate()
        }
    var whereId: String
        get() {
            return _whereId
        }
        set(value) {
            _whereId = value
            _requestUpdate()
        }
    var whereDate: String
        get() {
            return _whereDate
        }
        set(value) {
            _whereDate = value
            _requestUpdate()
        }
    var whereEmployeeID: String
        get() {
            return _whereEmployeeID
        }
        set(value) {
            _whereEmployeeID = value
            _requestUpdate()
        }
    var whereEmployeeName: String
        get() {
            return _whereEmployeeName
        }
        set(value) {
            _whereEmployeeName = value
            _requestUpdate()
        }
    var whereGroupId: String
        get() {
            return _whereGroupId
        }
        set(value) {
            _whereGroupId = value
            _requestUpdate()
        }
    var whereGroupName: String
        get() {
            return _whereGroupName
        }
        set(value) {
            _whereGroupName = value
            _requestUpdate()
        }

    private fun _headUpdate() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                _head = try {
                    val requestDevice =
                        "SELECT devices.id, devices.name, devices.date, devices.price, devices.type_id, types.name AS type_name " +
                                "FROM devices JOIN types ON devices.type_id = types.id " +
                                "WHERE devices.id = $_report " +
                                "LIMIT 1"
                    transaction {
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
                                ReportDeviceFromTables(_report, "$row")
                            }
                        }!!
                    }
                } catch (e: Exception) {
                    ReportDeviceFromTables(_report, "$e")
                }
            }
        }
    }

    private fun _listUpdate() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                _list = try {
                    transaction {
                        val result = exec(_request) { row ->
                            generateSequence {
                                if (row.next()) {
                                    ReportDeviceStringFromTables(
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
                        result ?: emptyList()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    emptyList()
                }
            }
        }
    }

    private fun _requestUpdate() {
        var requestNew =
            "SELECT ROW_NUMBER() OVER(ORDER BY strings.date DESC NULLS LAST) AS number, strings.id, strings.date, strings.employee_id, employees.name AS employee_name, employees.group_id AS group_id, groups.name AS group_name " +
                    "FROM strings " +
                    "JOIN employees ON strings.employee_id = employees.id " +
                    "JOIN groups ON employees.group_id = groups.id " +
                    "WHERE strings.device_id = $_report"
        val conditions = mutableListOf<String>()
        if (_whereId.isNotEmpty()) {
            conditions.add("strings.id >= $_whereId")
        }
        if (_whereDate.isNotEmpty()) {
            conditions.add("strings.date LIKE '%$_whereDate%'")
        }
        if (_whereEmployeeID.isNotEmpty()) {
            conditions.add("strings.employee_id >= $_whereEmployeeID")
        }
        if (_whereEmployeeName.isNotEmpty()) {
            conditions.add("employees.name LIKE '%$_whereEmployeeName%'")
        }
        if (_whereGroupId.isNotEmpty()) {
            conditions.add("employees.group_id >= $_whereGroupId")
        }
        if (_whereGroupName.isNotEmpty()) {
            conditions.add("groups.name LIKE '%$_whereGroupName%'")
        }
        if (conditions.isNotEmpty()) {
            requestNew += " AND " + conditions.joinToString(" AND ")
        }
        requestNew += " ORDER BY $_order1"
        requestNew += if (_order2.isNotEmpty()) ", $_order2" else ""
        requestNew += if (_order3.isNotEmpty()) ", $_order3" else ""
        requestNew += if (_order4.isNotEmpty()) ", $_order4" else ""
        requestNew += if (_order5.isNotEmpty()) ", $_order5" else ""
        requestNew += if (_order6.isNotEmpty()) ", $_order5" else ""
        _request = requestNew
        _listUpdate()
    }

    fun viewUpdate() {
        _head = ReportDeviceFromTables()
        _list = emptyList()
    }

    private fun _listOrderBy(order: String) {
        if (_order1.isEmpty()) {
            _order1 = order
        } else if (_order1 == order) {
            _order1 = "$order DESC"
        } else if (_order1 == "$order DESC") {
            _order1 = order
        } else {
            _order6 = _order5
            _order5 = _order4
            _order4 = _order3
            _order3 = _order2
            _order2 = _order1
            _order1 = order
        }
    }

    fun delete(itId: Int): Boolean {
        return try {
            transaction {
                StringsTable.deleteWhere { id.eq(itId) }
            }
            _requestUpdate()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun update(
        itId: Int,
        newId: String,
        newDate: String,
        newEmployeeId: String,
    ): Boolean {
        return try {
            transaction {
                StringsTable.update({ StringsTable.id.eq(itId) and StringsTable.deviceId.eq(_report) }) {
                    it[id] = newId.toInt()
                    it[date] = newDate
                    it[employeeId] = newEmployeeId.toInt()
                }
            }
            _requestUpdate()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun insert(
        newId: String,
        newDate: String,
        newEmployeeId: String,
    ): Boolean {
        return try {
            transaction {
                if (newId == "") {
                    StringsTable.insert {
                        it[date] = newDate
                        it[employeeId] = newEmployeeId.toInt()
                        it[deviceId] = _report
                    }
                } else {
                    StringsTable.insert {
                        it[id] = newId.toInt()
                        it[date] = newDate
                        it[employeeId] = newEmployeeId.toInt()
                        it[deviceId] = _report
                    }
                }
            }
            _requestUpdate()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

data class ReportDeviceFromTables(
    val id: Int = -1,
    val name: String = "null",
    val date: String = "null",
    val price: Int = -1,
    val typeId: Int = -1,
    val typeName: String = "null",
)

data class ReportDeviceStringFromTables(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val number: Int = -1,
    val id: Int = -1,
    val date: String = "null",
    val employeeID: Int = -1,
    val employeeName: String = "null",
    var groupId: Int = -1,
    var groupName: String = "null",
)