package composable.pages.app

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
import androidx.compose.ui.unit.dp
import composable.db.DbString
import composable.db.DbStrings
import composable.ui.UiButton
import icons.DatabaseOff
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun UiStrings() {
    Column {
        val allStrings = remember { mutableStateListOf<DbString>() }
        var isCreate by remember { mutableStateOf(false) }
        if (!isCreate) {
            isCreate = true
            allStrings.clear()
            transaction {
                DbStrings.selectAll().forEach {
                    allStrings.add(
                        DbString(
                            it[DbStrings.id],
                            it[DbStrings.deviceId],
                            it[DbStrings.date],
                            it[DbStrings.employeeId]
                        )
                    )
                }
            }
            allStrings.sortBy { it.id }
        }
        Row {
            UiButton(
                if (allStrings.size == 0) DatabaseOff else Icons.Default.Update,
                modifier = Modifier.size(120.dp)
            ) {
                allStrings.clear()
                transaction {
                    DbStrings.selectAll().forEach {
                        allStrings.add(
                            DbString(
                                it[DbStrings.id],
                                it[DbStrings.deviceId],
                                it[DbStrings.date],
                                it[DbStrings.employeeId]
                            )
                        )
                    }
                }
                allStrings.sortBy { it.id }
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Card(
                    elevation = 25.dp,
                    modifier = Modifier.heightIn(min = 55.dp)
                ) {
                    Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
                        Icon(
                            Icons.Default.Newspaper, contentDescription = null,
                            modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        )
                        Text(
                            "Строки",
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
                                            allStrings.sortByDescending { it.id }
                                        } else {
                                            descending = true
                                            allStrings.sortBy { it.id }
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
                                            allStrings.sortByDescending { it.deviceId }
                                        } else {
                                            descending = true
                                            allStrings.sortBy { it.deviceId }
                                        }
                                    }
                            )
                            Text(
                                "DeviceID",
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
                                            allStrings.sortByDescending { it.date }
                                        } else {
                                            descending = true
                                            allStrings.sortBy { it.date }
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
                                            allStrings.sortByDescending { it.employeeId }
                                        } else {
                                            descending = true
                                            allStrings.sortBy { it.employeeId }
                                        }
                                    }
                            )
                            Text(
                                "EmployeeID",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
        ListGroups(allStrings)
    }
}

@Composable
private fun ListGroups(allStrings: MutableList<DbString>) {
    LazyColumn(modifier = Modifier.padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(allStrings) {
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
                        deleteString(it.id, it.deviceId)
                        allStrings.remove(it)
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
                            "${it.deviceId} ",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        Text(
                            "${it.date} ",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        Text(
                            "${it.employeeId} ",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                    }
                }
            }
        }
        item {
            Row {
                var newId by remember { mutableStateOf("") }
                var newDeviceId by remember { mutableStateOf("") }
                var newDate by remember { mutableStateOf("") }
                var newEmployeeID by remember { mutableStateOf("") }
                UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
                    if (newId == "") {
                        try {
                            insertString(newDeviceId.toInt(), newDate, newEmployeeID.toInt())
                            allStrings.add(DbString(0, newDeviceId.toInt(), newDate, newEmployeeID.toInt()))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            insertString(newId.toInt(), newDeviceId.toInt(), newDate, newEmployeeID.toInt())
                            allStrings.add(DbString(newId.toInt(), newDeviceId.toInt(), newDate, newEmployeeID.toInt()))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                Card(elevation = 25.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
                    Row(Modifier.padding(10.dp)) {
                        TextField(
                            newId,
                            { newId = it },
                            label = { Text(if (newId == "") "AUTO" else "New ID") },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                        )
                        TextField(
                            newDeviceId,
                            { newDeviceId = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        TextField(
                            newDate,
                            { newDate = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        TextField(
                            newEmployeeID,
                            { newEmployeeID = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun insertString(newId: Int, newDeviceId: Int, newDate: String, newEmployeeId: Int) {
    transaction {
        DbStrings.insert {
            it[id] = newId
            it[deviceId] = newDeviceId
            it[date] = newDate
            it[employeeId] = newEmployeeId
        }
    }
}

private fun insertString(newDeviceId: Int, newDate: String, newEmployeeId: Int) {
    transaction {
        DbStrings.insert {
            it[deviceId] = newDeviceId
            it[date] = newDate
            it[employeeId] = newEmployeeId
        }
    }
}

private fun deleteString(id: Int, deviceId: Int) {
    transaction {
        DbStrings.deleteWhere { DbStrings.id.eq(id) and DbStrings.deviceId.eq(deviceId) }
    }
}