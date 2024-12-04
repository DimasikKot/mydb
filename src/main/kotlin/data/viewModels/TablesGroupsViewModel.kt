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
class TablesGroupsViewModel : ViewModel() {
    private var _loading by mutableStateOf(true)
    private var _list by mutableStateOf<List<GroupFromTable>>(emptyList())
    private var _request by mutableStateOf("")

    private var _order1 by mutableStateOf("name")
    private var _order2 by mutableStateOf("")

    private var _whereId by mutableStateOf("")
    private var _whereName by mutableStateOf("")

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    val list: List<GroupFromTable>
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
        var requestNew = "SELECT id, name FROM groups"
        val conditions = mutableListOf<String>()
        if (_whereId.isNotEmpty()) {
            conditions.add("id >= $_whereId")
        }
        if (_whereName.isNotEmpty()) {
            conditions.add("name LIKE '%$_whereName%'")
        }
        if (conditions.isNotEmpty()) {
            requestNew += " WHERE " + conditions.joinToString(" and ")
        }
        requestNew += " ORDER BY $_order1"
        requestNew += if (_order2.isNotEmpty()) ", $_order2" else ""
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
            _order2 = _order1
            _order1 = order
        }
        listUpdate()
        return descending
    }

    private suspend fun listGet(): List<GroupFromTable> {
        return withContext(Dispatchers.IO) {
            transaction {
                val result = exec(_request) { row ->
                    generateSequence {
                        if (row.next()) {
                            GroupFromTable(
                                id = row.getInt("id"),
                                name = row.getString("name")
                            )
                        } else null
                    }.toList()
                }
                result ?: emptyList()
            }
        }
    }

    fun delete(itId: Int): Boolean {
        return try {
            transaction {
                GroupsTable.deleteWhere { id.eq(itId) }
            }
            listUpdate()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun update(itId: Int, newId: String, newName: String): Boolean {
        return try {
            transaction {
                GroupsTable.update({ GroupsTable.id eq itId }) {
                    it[id] = newId.toInt()
                    it[name] = newName
                }
            }
            listUpdate()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun insert(newId: String, newName: String): Boolean {
        return try {
            transaction {
                if (newId == "") {
                    GroupsTable.insert {
                        it[name] = newName
                    }
                } else {
                    GroupsTable.insert {
                        it[id] = newId.toInt()
                        it[name] = newName
                    }
                }
            }
            listUpdate()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

data class GroupFromTable(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val id: Int,
    val name: String,
)