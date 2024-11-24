package data.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import data.StringFromTable
import data.StringsTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TablesStringsViewModel : ViewModel() {
    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    private var request by mutableStateOf("SELECT id, date, device_id, employee_id FROM strings ORDER BY device_id, id")
    var order1 by mutableStateOf("device_id")
    private var order2 by mutableStateOf("id")
    private var order3 by mutableStateOf("")
    private var order4 by mutableStateOf("")
    var whereId by mutableStateOf("")
    var whereDate by mutableStateOf("")
    var whereDeviceId by mutableStateOf("")
    var whereEmployeeId by mutableStateOf("")

    fun listUpdate() {
        var requestNew = "SELECT id, date, device_id, employee_id FROM strings"
        val conditions = mutableListOf<String>()
        if (whereId.isNotEmpty()) { conditions.add("id >= $whereId") }
        if (whereDate.isNotEmpty()) { conditions.add("date LIKE '%$whereDate%'") }
        if (whereDeviceId.isNotEmpty()) { conditions.add("device_id >= $whereDeviceId") }
        if (whereEmployeeId.isNotEmpty()) { conditions.add("employee_id >= $whereEmployeeId") }
        if (conditions.isNotEmpty()) { requestNew += " WHERE " + conditions.joinToString(" and ") }
        requestNew += " ORDER BY $order1"
        requestNew += if (order2.isNotEmpty()) ", $order2" else ""
        requestNew += if (order3.isNotEmpty()) ", $order3" else ""
        requestNew += if (order4.isNotEmpty()) ", $order4" else ""
        request = "SELECT id FROM strings"
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
            order4 = order3
            order3 = order2
            order2 = order1
            order1 = order
        }
        listUpdate()
        return descending
    }

    fun listGet(): List<StringFromTable> {
        try {
            return transaction {
                val result = exec(request) { row ->
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
            return emptyList()
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