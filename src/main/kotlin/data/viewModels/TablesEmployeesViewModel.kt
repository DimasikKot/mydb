package data.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

@OptIn(DelicateCoroutinesApi::class)
class TablesEmployeesViewModel : ViewModel() {
    private var _loading by mutableStateOf(true)
    private var _list by mutableStateOf<List<EmployeeFromTable>>(emptyList())
    private var _request by mutableStateOf("")

    private var _order1 by mutableStateOf("name")
    private var _order2 by mutableStateOf("")
    private var _order3 by mutableStateOf("")

    private var _whereId by mutableStateOf("")
    private var _whereName by mutableStateOf("")
    private var _whereGroupId by mutableStateOf("")

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    val list: List<EmployeeFromTable>
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
    var whereName: String
        get() {
            return _whereName
        }
        set(value) {
            _whereName = value
            listUpdate()
        }
    var whereGroupId: String
        get() {
            return _whereGroupId
        }
        set(value) {
            _whereGroupId = value
            listUpdate()
        }

    fun listUpdate() {
        var requestNew = "SELECT id, name, group_id FROM employees"
        val conditions = mutableListOf<String>()
        if (_whereId.isNotEmpty()) { conditions.add("id >= $_whereId") }
        if (_whereName.isNotEmpty()) { conditions.add("name LIKE '%$_whereName%'") }
        if (_whereGroupId.isNotEmpty()) { conditions.add("group_id >= $_whereGroupId") }
        if (conditions.isNotEmpty()) { requestNew += " WHERE " + conditions.joinToString(" and ") }
        requestNew += " ORDER BY $_order1"
        requestNew += if (_order2.isNotEmpty()) ", $_order2" else ""
        requestNew += if (_order3.isNotEmpty()) ", $_order3" else ""
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
            _order3 = _order2
            _order2 = _order1
            _order1 = order
        }
        listUpdate()
        return descending
    }

    private suspend fun listGet(): List<EmployeeFromTable> {
        return withContext(Dispatchers.IO) {
            try {
                transaction {
                    val result = exec(_request) { row ->
                        generateSequence {
                            if (row.next()) {
                                EmployeeFromTable(
                                    id = row.getInt("id"),
                                    name = row.getString("name"),
                                    groupId = row.getInt("group_id")
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
                EmployeesTable.deleteWhere { id.eq(itId) }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun update(itId: Int, newId: Int, newName: String, newGroupId: Int): Boolean {
        try {
            transaction {
                EmployeesTable.update({ EmployeesTable.id eq itId }) {
                    it[id] = newId
                    it[name] = newName
                    it[groupId] = newGroupId
                }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun insert(newId: Int, newName: String, newGroupId: Int): Boolean {
        try {
            transaction {
                EmployeesTable.insert {
                    it[id] = newId
                    it[name] = newName
                    it[groupId] = newGroupId
                }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun insert(newName: String, newGroupId: Int): Boolean {
        try {
            transaction {
                EmployeesTable.insert {
                    it[name] = newName
                    it[groupId] = newGroupId
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

data class EmployeeFromTable(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val id: Int,
    val name: String,
    val groupId: Int,
)