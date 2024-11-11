package data.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import data.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TablesEmployeesViewModel : ViewModel() {
    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    private var request by mutableStateOf("SELECT id, name, group_id FROM employees ORDER BY id")
    var order1 by mutableStateOf("id")
    private var order2 by mutableStateOf("")
    private var order3 by mutableStateOf("")
    var whereId by mutableStateOf("")
    var whereName by mutableStateOf("")
    var whereGroupId by mutableStateOf("")

    fun listUpdate() {
        var requestNew = "SELECT id, name, group_id FROM employees"
        val conditions = mutableListOf<String>()
        if (whereId.isNotEmpty()) { conditions.add("id >= $whereId") }
        if (whereName.isNotEmpty()) { conditions.add("name LIKE '%$whereName%'") }
        if (whereGroupId.isNotEmpty()) { conditions.add("group_id >= $whereGroupId") }
        if (conditions.isNotEmpty()) { requestNew += " WHERE " + conditions.joinToString(" and ") }
        requestNew += " ORDER BY $order1"
        requestNew += if (order2.isNotEmpty()) ", $order2" else ""
        requestNew += if (order3.isNotEmpty()) ", $order3" else ""
        request = "SELECT id FROM employees"
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
            order3 = order2
            order2 = order1
            order1 = order
        }
        listUpdate()
        return descending
    }

    fun listGet(): List<EmployeeFromTable> {
        try {
            return transaction {
                val result = exec(request) { row ->
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