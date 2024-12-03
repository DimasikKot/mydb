package data.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.DevicesTable
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

@OptIn(DelicateCoroutinesApi::class)
class TablesDevicesViewModel : ViewModel() {
    private var _loading by mutableStateOf(true)
    private var _list by mutableStateOf<List<DeviceFromTable>>(emptyList())
    private var _request by mutableStateOf("")

    private var _order1 by mutableStateOf("name")
    private var _order2 by mutableStateOf("")
    private var _order3 by mutableStateOf("")
    private var _order4 by mutableStateOf("")
    private var _order5 by mutableStateOf("")

    private var _whereId by mutableStateOf("")
    private var _whereName by mutableStateOf("")
    private var _whereDate by mutableStateOf("")
    private var _wherePrice by mutableStateOf("")
    private var _whereTypeId by mutableStateOf("")
    private var _whereTypeName by mutableStateOf("")

    var searching by mutableStateOf(false)
    var creating by mutableStateOf(false)

    val list: List<DeviceFromTable>
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
    var whereDate: String
        get() {
            return _whereDate
        }
        set(value) {
            _whereDate = value
            listUpdate()
        }
    var wherePrice: String
        get() {
            return _wherePrice
        }
        set(value) {
            _wherePrice = value
            listUpdate()
        }
    var whereTypeId: String
        get() {
            return _whereTypeId
        }
        set(value) {
            _whereTypeId = value
            listUpdate()
        }
    var whereTypeName: String
        get() {
            return _whereTypeName
        }
        set(value) {
            _whereTypeName = value
            listUpdate()
        }

    fun listUpdate() {
        var requestNew = "SELECT ROW_NUMBER() OVER(ORDER BY devices.name) AS number, devices.id, devices.name, devices.date, devices.price, devices.type_id, types.name AS type_name FROM devices JOIN types ON devices.type_id = types.id"
        val conditions = mutableListOf<String>()
        if (_whereId.isNotEmpty()) { conditions.add("id >= $_whereId") }
        if (_whereName.isNotEmpty()) { conditions.add("name LIKE '%$_whereName%'") }
        if (_whereDate.isNotEmpty()) { conditions.add("date LIKE '%$_whereDate%'") }
        if (_wherePrice.isNotEmpty()) { conditions.add("price >= $_wherePrice") }
        if (_whereTypeId.isNotEmpty()) { conditions.add("type_id >= $_whereTypeId") }
        if (conditions.isNotEmpty()) { requestNew += " WHERE " + conditions.joinToString(" AND ") }
        requestNew += " ORDER BY $_order1"
        requestNew += if (_order2.isNotEmpty()) ", $_order2" else ""
        requestNew += if (_order3.isNotEmpty()) ", $_order3" else ""
        requestNew += if (_order4.isNotEmpty()) ", $_order4" else ""
        requestNew += if (_order5.isNotEmpty()) ", $_order5" else ""
        _request = requestNew
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
            _order5 = _order4
            _order4 = _order3
            _order3 = _order2
            _order2 = _order1
            _order1 = order
        }
        listUpdate()
        return descending
    }

    private suspend fun listGet(): List<DeviceFromTable> {
        return withContext(Dispatchers.IO) {
            transaction {
                val result = exec(_request) { row ->
                    generateSequence {
                        if (row.next()) {
                            DeviceFromTable(
                                number = row.getInt("number"),
                                id = row.getInt("id"),
                                name = row.getString("name"),
                                date = row.getString("date"),
                                price = row.getInt("price"),
                                typeId = row.getInt("type_id"),
                                typeName = row.getString("type_name")
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
                DevicesTable.deleteWhere { id.eq(itId) }
            }
            listUpdate()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun update(itId: Int, newId: String, newName: String, newDate: String, newPrice: String, newTypeId: String): Boolean {
        return try {
            transaction {
                DevicesTable.update({ DevicesTable.id eq itId }) {
                    it[id] = newId.toInt()
                    it[name] = newName
                    it[date] = newDate
                    it[price] = newPrice.toInt()
                    it[typeId] = newTypeId.toInt()
                }
            }
            listUpdate()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun insert(newId: String, newName: String, newDate: String, newPrice: String, newTypeId: String): Boolean {
        return try {
            transaction {
                if (newId == "") {
                    DevicesTable.insert {
                        it[name] = newName
                        it[date] = newDate
                        it[price] = newPrice.toInt()
                        it[typeId] = newTypeId.toInt()
                    }
                } else {
                    DevicesTable.insert {
                        it[id] = newId.toInt()
                        it[name] = newName
                        it[date] = newDate
                        it[price] = newPrice.toInt()
                        it[typeId] = newTypeId.toInt()
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

data class DeviceFromTable(
    var editing: MutableState<Boolean> = mutableStateOf(false),
    var canUpdate: MutableState<Boolean> = mutableStateOf(true),
    var canDelete: MutableState<Boolean> = mutableStateOf(true),
    val number: Int,
    val id: Int,
    val name: String,
    val date: String,
    val price: Int,
    val typeId: Int,
    val typeName: String,
)