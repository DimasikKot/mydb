package data.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TablesViewModel : ViewModel() {
    private var typesRequest by mutableStateOf("SELECT id, name FROM types")
    private var typesOrder1 by mutableStateOf("id")
    private var typesOrder2 by mutableStateOf("")
    var typesWhereId by mutableStateOf("")
    var typesWhereName by mutableStateOf("")

    fun typesUpdate() {
        typesRequest = "SELECT * FROM types"
        var newRequest = "SELECT id, name FROM types"
        newRequest += if (typesWhereId.isNotEmpty() or typesWhereName.isNotEmpty()) " WHERE" else ""
        newRequest += if (typesWhereId.isNotEmpty()) " id = $typesWhereId" else ""
        newRequest += if (typesWhereName.isNotEmpty()) " name LIKE '%$typesWhereName%'" else ""
        newRequest += " ORDER BY $typesOrder1"
        newRequest += if (typesOrder2.isNotEmpty()) ", $typesOrder2" else ""
        typesRequest = newRequest
    }

    fun typesOrderBy(order: String): Boolean {
        var descending = false
        if (typesOrder1.isEmpty()) {
            typesOrder1 = order
        } else if (typesOrder1 == order) {
            typesOrder1 = "$order DESC"
            descending = true
        } else if (typesOrder1 == "$order DESC") {
            typesOrder1 = order
        } else {
            typesOrder2 = typesOrder1
            typesOrder1 = order
        }
        typesUpdate()
        return descending
    }

    fun typesGetList(): List<TypeFromTable> {
        try {
            return transaction {
                val result = exec(typesRequest) { row ->
                    generateSequence {
                        if (row.next()) {
                            TypeFromTable(
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
            return emptyList()
        }
    }

    fun typeDelete(itId: Int): Boolean {
        try {
            transaction {
                TypesTable.deleteWhere { id.eq(itId) }
            }
            typesUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun typeUpdate(itId: Int, newId: Int, newName: String): Boolean {
        try {
            transaction {
                TypesTable.update({ TypesTable.id eq itId }) {
                    it[id] = newId
                    it[name] = newName
                }
            }
            typesUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun typeInsert(newId: Int, newName: String): Boolean {
        try {
            transaction {
                TypesTable.insert {
                    it[id] = newId
                    it[name] = newName
                }
            }
            typesUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun typeInsert(newName: String): Boolean {
        try {
            transaction {
                TypesTable.insert {
                    it[name] = newName
                }
            }
            typesUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    private var devicesRequest by mutableStateOf("SELECT id, name, date, price, type_id FROM devices")
    private var devicesOrder1 by mutableStateOf("id")
    private var devicesOrder2 by mutableStateOf("")
    private var devicesOrder3 by mutableStateOf("")
    private var devicesOrder4 by mutableStateOf("")
    private var devicesOrder5 by mutableStateOf("")
    var devicesWhereId by mutableStateOf("")
    var devicesWhereName by mutableStateOf("")
    var devicesWhereDate by mutableStateOf("")
    var devicesWherePrice by mutableStateOf("")
    var devicesWhereTypeId by mutableStateOf("")

    fun devicesUpdate() {
        devicesRequest = "SELECT * FROM devices"
        var newRequest = "SELECT id, name, date, price, type_id FROM devices"
        newRequest += if (devicesWhereId.isNotEmpty() || devicesWhereName.isNotEmpty() || devicesWhereDate.isNotEmpty() || devicesWherePrice.isNotEmpty() || devicesWhereTypeId.isNotEmpty()) " WHERE" else ""
        newRequest += if (devicesWhereId.isNotEmpty()) " id = $devicesWhereId" else ""
        newRequest += if (devicesWhereName.isNotEmpty()) " name LIKE '%$devicesWhereName%'" else ""
        newRequest += if (devicesWhereDate.isNotEmpty()) " date LIKE '%$devicesWhereDate%'" else ""
        newRequest += if (devicesWherePrice.isNotEmpty()) " price = $devicesWherePrice" else ""
        newRequest += if (devicesWhereTypeId.isNotEmpty()) " type_id = $devicesWhereTypeId" else ""
        newRequest += " ORDER BY $devicesOrder1"
        newRequest += if (devicesOrder2.isNotEmpty()) ", $devicesOrder2" else ""
        newRequest += if (devicesOrder3.isNotEmpty()) ", $devicesOrder3" else ""
        newRequest += if (devicesOrder4.isNotEmpty()) ", $devicesOrder4" else ""
        newRequest += if (devicesOrder5.isNotEmpty()) ", $devicesOrder5" else ""
        devicesRequest = newRequest
    }

    fun devicesOrderBy(order: String): Boolean {
        var descending = false
        if (devicesOrder1.isEmpty()) {
            devicesOrder1 = order
        } else if (devicesOrder1 == order) {
            devicesOrder1 = "$order DESC"
            descending = true
        } else if (devicesOrder1 == "$order DESC") {
            devicesOrder1 = order
        } else {
            devicesOrder5 = devicesOrder4
            devicesOrder4 = devicesOrder3
            devicesOrder3 = devicesOrder2
            devicesOrder2 = devicesOrder1
            devicesOrder1 = order
        }
        devicesUpdate()
        return descending
    }

    fun devicesGetList(): List<DeviceFromTable> {
        try {
            return transaction {
                val result = exec(devicesRequest) { row ->
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

    fun deviceDelete(itId: Int): Boolean {
        try {
            transaction {
                DevicesTable.deleteWhere { id.eq(itId) }
            }
            devicesUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun deviceUpdate(itId: Int, newId: Int, newName: String, newDate: String, newPrice: Int, newTypeId: Int): Boolean {
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
            devicesUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun deviceInsert(newId: Int, newName: String, newDate: String, newPrice: Int, newTypeId: Int): Boolean {
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
            devicesUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun deviceInsert(newName: String, newDate: String, newPrice: Int, newTypeId: Int): Boolean {
        try {
            transaction {
                DevicesTable.insert {
                    it[name] = newName
                    it[date] = newDate
                    it[price] = newPrice
                    it[typeId] = newTypeId
                }
            }
            devicesUpdate()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}