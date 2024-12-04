package data.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import data.StringsTable
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

@OptIn(DelicateCoroutinesApi::class)
class TablesStringsViewModel : ViewModel() {
    private var _loading by mutableStateOf(true)
    private var _list by mutableStateOf<List<StringFromTable>>(emptyList())
    private var _request by mutableStateOf("")

    private var _order1 by mutableStateOf("device_id")
    private var _order2 by mutableStateOf("id")
    private var _order3 by mutableStateOf("")
    private var _order4 by mutableStateOf("")

    private var _whereId by mutableStateOf("")
    private var _whereDate by mutableStateOf("")
    private var _whereDeviceId by mutableStateOf("")
    private var _whereEmployeeId by mutableStateOf("")

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    val list: List<StringFromTable>
        get() {
            if (_loading) {
                listUpdate()
            }
            return _list
        }

    val order1: String
        get() {
            return _order1
        }

    var whereId: String
        get() {
            return _whereId
        }
        set(value) {
            _whereId = value
            listUpdate()
        }
    var whereDate: String
        get() {
            return _whereDate
        }
        set(value) {
            _whereDate = value
            listUpdate()
        }
    var whereDeviceId: String
        get() {
            return _whereDeviceId
        }
        set(value) {
            _whereDeviceId = value
            listUpdate()
        }
    var whereEmployeeId: String
        get() {
            return _whereEmployeeId
        }
        set(value) {
            _whereEmployeeId = value
            listUpdate()
        }

    fun listUpdateView() {
        try {
            GlobalScope.launch {
                _list = emptyList()
                _list = listGet()
                if (_list.isNotEmpty()) {
                    _loading = false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun listUpdate() {
        var requestNew = "SELECT id, date, device_id, employee_id FROM strings"
        val conditions = mutableListOf<String>()
        if (_whereId.isNotEmpty()) { conditions.add("id >= $_whereId") }
        if (_whereDate.isNotEmpty()) { conditions.add("date LIKE '%$_whereDate%'") }
        if (_whereDeviceId.isNotEmpty()) { conditions.add("device_id >= $_whereDeviceId") }
        if (_whereEmployeeId.isNotEmpty()) { conditions.add("employee_id >= $_whereEmployeeId") }
        if (conditions.isNotEmpty()) { requestNew += " WHERE " + conditions.joinToString(" and ") }
        requestNew += " ORDER BY $_order1"
        requestNew += if (_order2.isNotEmpty()) ", $_order2" else ""
        requestNew += if (_order3.isNotEmpty()) ", $_order3" else ""
        requestNew += if (_order4.isNotEmpty()) ", $_order4" else ""
        _request = requestNew
        try {
            GlobalScope.launch {
                _list = listGet()
                if (_list.isNotEmpty()) {
                    _loading = false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun listOrderBy(order: String): Boolean {
        var descending = false
        if (_order1.isEmpty()) {
            _order1 = order
        } else if (_order1 == order) {
            _order1 = "$order DESC"
            descending = true
        } else if (_order1 == "$order DESC") {
            _order1 = order
        } else {
            _order4 = _order3
            _order3 = _order2
            _order2 = _order1
            _order1 = order
        }
        listUpdate()
        return descending
    }

    private suspend fun listGet(): List<StringFromTable> {
        return withContext(Dispatchers.IO) {
            try {
                transaction {
                    val result = exec(_request) { row ->
                        generateSequence {
                            if (row.next()) {
                                StringFromTable(
                                    id = row.getInt("id"),
                                    date = row.getString("date"),
                                    deviceId = row.getInt("device_id"),
                                    employeeId = row.getInt("employee_id")
                                )
                            } else null
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

    fun update(itId: Int, newId: Int, newDate: String, newDeviceId: Int, newEmployeeId: Int): Boolean {
        try {
            transaction {
                StringsTable.update({ StringsTable.id eq itId }) {
                    it[id] = newId
                    it[date] = newDate
                    it[deviceId] = newDeviceId
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

    fun insert(newId: Int, newDate: String, newDeviceId: Int, newEmployeeId: Int): Boolean {
        try {
            transaction {
                StringsTable.insert {
                    it[id] = newId
                    it[date] = newDate
                    it[deviceId] = newDeviceId
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

    fun insert(newDate: String, newDeviceId: Int, newEmployeeId: Int): Boolean {
        try {
            transaction {
                StringsTable.insert {
                    it[date] = newDate
                    it[deviceId] = newDeviceId
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
}

data class StringFromTable(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val id: Int,
    val deviceId: Int,
    val date: String,
    val employeeId: Int,
)