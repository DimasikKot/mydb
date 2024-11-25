package data.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import data.DeviceFromTable
import data.DevicesTable
import data.IntDB
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TablesDevicesViewModel : ViewModel() {
    var report = mutableIntStateOf(IntDB("reportDefaultDevice", 0).toInt())

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    private var request by mutableStateOf("SELECT id, name, date, price, type_id FROM devices ORDER BY id")
    var order1 by mutableStateOf("id")
    private var order2 by mutableStateOf("")
    private var order3 by mutableStateOf("")
    private var order4 by mutableStateOf("")
    private var order5 by mutableStateOf("")
    var whereId by mutableStateOf("")
    var whereName by mutableStateOf("")
    var whereDate by mutableStateOf("")
    var wherePrice by mutableStateOf("")
    var whereTypeId by mutableStateOf("")

    fun listUpdate() {
        var requestNew = "SELECT id, name, date, price, type_id FROM devices"
        val conditions = mutableListOf<String>()
        if (whereId.isNotEmpty()) { conditions.add("id >= $whereId") }
        if (whereName.isNotEmpty()) { conditions.add("name LIKE '%$whereName%'") }
        if (whereDate.isNotEmpty()) { conditions.add("date LIKE '%$whereDate%'") }
        if (wherePrice.isNotEmpty()) { conditions.add("price >= $wherePrice") }
        if (whereTypeId.isNotEmpty()) { conditions.add("type_id >= $whereTypeId") }
        if (conditions.isNotEmpty()) { requestNew += " WHERE " + conditions.joinToString(" and ") }
        requestNew += " ORDER BY $order1"
        requestNew += if (order2.isNotEmpty()) ", $order2" else ""
        requestNew += if (order3.isNotEmpty()) ", $order3" else ""
        requestNew += if (order4.isNotEmpty()) ", $order4" else ""
        requestNew += if (order5.isNotEmpty()) ", $order5" else ""
        request = "SELECT id FROM devices"
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
            order5 = order4
            order4 = order3
            order3 = order2
            order2 = order1
            order1 = order
        }
        listUpdate()
        return descending
    }

    fun listGet(): List<DeviceFromTable> {
        try {
            return transaction {
                val result = exec(request) { row ->
                    generateSequence {
                        if (row.next()) {
                            DeviceFromTable(
                                id = row.getInt("id"),
                                name = row.getString("name"),
                                date = row.getString("date"),
                                price = row.getInt("price"),
                                typeId = row.getInt("type_id")
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
                DevicesTable.deleteWhere { id.eq(itId) }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun update(itId: Int, newId: Int, newName: String, newDate: String, newPrice: Int, newTypeId: Int): Boolean {
        try {
            transaction {
                DevicesTable.update({ DevicesTable.id eq itId }) {
                    it[id] = newId
                    it[name] = newName
                    it[date] = newDate
                    it[price] = newPrice
                    it[typeId] = newTypeId
                }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun insert(newId: Int, newName: String, newDate: String, newPrice: Int, newTypeId: Int): Boolean {
        try {
            transaction {
                DevicesTable.insert {
                    it[id] = newId
                    it[name] = newName
                    it[date] = newDate
                    it[price] = newPrice
                    it[typeId] = newTypeId
                }
            }
            listUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun insert(newName: String, newDate: String, newPrice: Int, newTypeId: Int): Boolean {
        try {
            transaction {
                DevicesTable.insert {
                    it[name] = newName
                    it[date] = newDate
                    it[price] = newPrice
                    it[typeId] = newTypeId
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