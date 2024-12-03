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
class TablesTypesViewModel : ViewModel() {
    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    private var _list by mutableStateOf<List<TypeFromTable>>(emptyList())
    val list: List<TypeFromTable>
        get() = _list

    private var request by mutableStateOf("SELECT ROW_NUMBER() OVER(ORDER BY id) AS number, id, name FROM types ORDER BY id")
    var order1 by mutableStateOf("id")
    private var order2 by mutableStateOf("")
    var whereId by mutableStateOf("")
    var whereName by mutableStateOf("")

    fun listUpdate() {
        var requestNew = "SELECT ROW_NUMBER() OVER(ORDER BY id) AS number, id, name FROM types"
        val conditions = mutableListOf<String>()
        if (whereId.isNotEmpty()) {
            conditions.add("id >= $whereId")
        }
        if (whereName.isNotEmpty()) {
            conditions.add("name LIKE '%$whereName%'")
        }
        if (conditions.isNotEmpty()) {
            requestNew += " WHERE " + conditions.joinToString(" and ")
        }
        requestNew += " ORDER BY $order1"
        requestNew += if (order2.isNotEmpty()) ", $order2" else ""
        request = "SELECT id FROM types"
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
            order2 = order1
            order1 = order
        }
        listUpdate()
        return descending
    }

    private suspend fun listGet(): List<TypeFromTable> {
        return withContext(Dispatchers.IO) {
            try {
                transaction {
                    val result = exec(request) { row ->
                        generateSequence {
                            if (row.next()) {
                                TypeFromTable(
                                    number = row.getInt("number"),
                                    id = row.getInt("id"),
                                    name = row.getString("name")
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
                TypesTable.deleteWhere { id.eq(itId) }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun update(itId: Int, newId: Int, newName: String): Boolean {
        try {
            transaction {
                TypesTable.update({ TypesTable.id eq itId }) {
                    it[id] = newId
                    it[name] = newName
                }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun insert(newId: Int, newName: String): Boolean {
        try {
            transaction {
                TypesTable.insert {
                    it[id] = newId
                    it[name] = newName
                }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun insert(newName: String): Boolean {
        try {
            transaction {
                TypesTable.insert {
                    it[name] = newName
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

data class TypeFromTable(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val number: Int = -1,
    val id: Int = -1,
    val name: String = "null",
)