package composable.app.tables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import composable.ui.UiButton
import data.viewModels.MainViewModel
import composable.icons.DatabaseOff
import composable.icons.ExportNotes
import composable.icons.IconWindow
import data.DeviceFromTable
import data.DevicesTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun AppTablesDevices(mainVM: MainViewModel) {
    Column {
        val listDevices = remember { mutableStateListOf<DeviceFromTable>() }
        var isCreate by remember { mutableStateOf(false) }
        if (!isCreate) {
            isCreate = true
            listDevices.clear()
            transaction {
                DevicesTable.selectAll().forEach {
                    listDevices.add(
                        DeviceFromTable(
                            it[DevicesTable.id],
                            it[DevicesTable.name],
                            it[DevicesTable.date],
                            it[DevicesTable.price],
                            it[DevicesTable.typeId]
                        )
                    )
                }
            }
            listDevices.sortBy { it.id }
        }
        Row {
            UiButton(
                if (listDevices.size == 0) DatabaseOff else Icons.Default.Update,
                modifier = Modifier.size(120.dp)
            ) {
                listDevices.clear()
                transaction {
                    DevicesTable.selectAll().forEach {
                        listDevices.add(
                            DeviceFromTable(
                                it[DevicesTable.id],
                                it[DevicesTable.name],
                                it[DevicesTable.date],
                                it[DevicesTable.price],
                                it[DevicesTable.typeId]
                            )
                        )
                    }
                }
                listDevices.sortBy { it.id }
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Card(
                    elevation = 25.dp,
                    modifier = Modifier.heightIn(min = 55.dp)
                ) {
                    Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
                        Icon(
                            Icons.Default.Devices, contentDescription = null,
                            modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        )
                        Text(
                            "Устройства",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                    }
                }
                Card(
                    elevation = 25.dp,
                    modifier = Modifier.padding(top = 10.dp).heightIn(min = 55.dp)
                ) {
                    Row(
                        Modifier
                            .background(MaterialTheme.colors.secondaryVariant).padding(10.dp)
                    ) {
                        Row(Modifier.weight(1f)) {
                            var descending by remember { mutableStateOf(false) }
                            Icon(
                                if (descending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                                    .clickable {
                                        if (descending) {
                                            descending = false
                                            listDevices.sortByDescending { it.id }
                                        } else {
                                            descending = true
                                            listDevices.sortBy { it.id }
                                        }
                                    }
                            )
                            Text(
                                "ID",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Row(Modifier.weight(1f).padding(start = 10.dp)) {
                            var descending by remember { mutableStateOf(false) }
                            Icon(
                                if (descending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                                    .clickable {
                                        if (descending) {
                                            descending = false
                                            listDevices.sortByDescending { it.name }
                                        } else {
                                            descending = true
                                            listDevices.sortBy { it.name }
                                        }
                                    }
                            )
                            Text(
                                "Name",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Row(Modifier.weight(1f).padding(start = 10.dp)) {
                            var descending by remember { mutableStateOf(false) }
                            Icon(
                                if (descending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                                    .clickable {
                                        if (descending) {
                                            descending = false
                                            listDevices.sortByDescending { it.date }
                                        } else {
                                            descending = true
                                            listDevices.sortBy { it.date }
                                        }
                                    }
                            )
                            Text(
                                "Date",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Row(Modifier.weight(1f).padding(start = 10.dp)) {
                            var descending by remember { mutableStateOf(false) }
                            Icon(
                                if (descending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                                    .clickable {
                                        if (descending) {
                                            descending = false
                                            listDevices.sortByDescending { it.price }
                                        } else {
                                            descending = true
                                            listDevices.sortBy { it.price }
                                        }
                                    }
                            )
                            Text(
                                "Price",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Row(Modifier.weight(1f).padding(start = 10.dp)) {
                            var descending by remember { mutableStateOf(false) }
                            Icon(
                                if (descending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                                    .clickable {
                                        if (descending) {
                                            descending = false
                                            listDevices.sortByDescending { it.typeId }
                                        } else {
                                            descending = true
                                            listDevices.sortBy { it.typeId }
                                        }
                                    }
                            )
                            Text(
                                "TypeID",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
        ListDevices(listDevices, mainVM)
    }
}

@Composable
private fun ListDevices(listDevices: MutableList<DeviceFromTable>, mainVM: MainViewModel) {
    LazyColumn(modifier = Modifier.padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Row {
                var newId by remember { mutableStateOf("") }
                var newName by remember { mutableStateOf("") }
                var newDate by remember { mutableStateOf("") }
                var newPrice by remember { mutableStateOf("") }
                var newTypeId by remember { mutableStateOf("") }
                UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
                    if (newId == "") {
                        try {
                            insertDevice(newName, newDate, newPrice.toInt(), newTypeId.toInt())
                            listDevices.add(DeviceFromTable(0, newName, newDate, newPrice.toInt(), newTypeId.toInt()))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            insertDevice(newId.toInt(), newName, newDate, newPrice.toInt(), newTypeId.toInt())
                            listDevices.add(
                                DeviceFromTable(
                                    newId.toInt(),
                                    newName,
                                    newDate,
                                    newPrice.toInt(),
                                    newTypeId.toInt()
                                )
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                Card(elevation = 25.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
                    Row(Modifier.heightIn(min = 60.dp).padding(10.dp)) {
                        TextField(
                            newId,
                            { newId = it },
                            label = { Text(if (newId == "") "AUTO" else "New ID") },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                        )
                        TextField(
                            newName,
                            { newName = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        TextField(
                            newDate,
                            { newDate = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        TextField(
                            newPrice,
                            { newPrice = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        TextField(
                            newTypeId,
                            { newTypeId = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                    }
                }
            }
        }
        items(listDevices) {
            Row {
                var canUpdate by remember { mutableStateOf(true) }
                var canDelete by remember { mutableStateOf(true) }
                UiButton(
                    if (canUpdate) Icons.Default.ModeEdit else Icons.Default.EditOff,
                    modifier = Modifier.size(55.dp)
                ) {
                    try {
                        canUpdate = false
                    } catch (e: Exception) {
                        e.printStackTrace()
                        TODO()
                    }
                }
                UiButton(
                    if (canDelete) Icons.Default.Delete else Icons.Default.DeleteForever,
                    modifier = Modifier
                        .padding(start = 10.dp).size(55.dp)
                ) {
                    try {
                        deleteDevice(it.id)
                        listDevices.remove(it)
                    } catch (e: Exception) {
                        canDelete = false
                        e.printStackTrace()
                    }
                }
                Card(elevation = 25.dp, modifier = Modifier.padding(start = 10.dp)) {
                    Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                        Text(
                            "${it.id} ",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                        )
                        Text(
                            "${it.name} ",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        Text(
                            "${it.date} ",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        Text(
                            "${it.price} ",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                            Text(
                                "${it.typeId} ",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                if (mainVM.winVM.currentDevice == it.id && mainVM.winVM.report) IconWindow else ExportNotes,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 10.dp).size(35.dp)
                                    .clickable {
                                        if (mainVM.winVM.report) {
                                            mainVM.winVM.report = false
                                            mainVM.winVM.currentDevice = 0
                                        } else {
                                            mainVM.winVM.report = true
                                            mainVM.winVM.currentDevice = it.id
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun insertDevice(newId: Int, newName: String, newDate: String, newPrice: Int, newTypeId: Int) {
    transaction {
        DevicesTable.insert {
            it[id] = newId
            it[name] = newName
            it[date] = newDate
            it[price] = newPrice
            it[typeId] = newTypeId
        }
    }
}

private fun insertDevice(newName: String, newDate: String, newPrice: Int, newTypeId: Int) {
    transaction {
        DevicesTable.insert {
            it[name] = newName
            it[date] = newDate
            it[price] = newPrice
            it[typeId] = newTypeId
        }
    }
}

private fun deleteDevice(id: Int) {
    transaction {
        DevicesTable.deleteWhere { DevicesTable.id.eq(id) }
    }
}