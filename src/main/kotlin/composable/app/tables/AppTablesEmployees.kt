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
import androidx.compose.ui.unit.dp
import composable.ui.UiButton
import composable.icons.DatabaseOff
import data.EmployeeFromTable
import data.EmployeesTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun AppTablesEmployees() {
    Column {
        val listEmployees = remember { mutableStateListOf<EmployeeFromTable>() }
        var isCreate by remember { mutableStateOf(false) }
        if (!isCreate) {
            isCreate = true
            listEmployees.clear()
            transaction {
                EmployeesTable.selectAll().forEach {
                    listEmployees.add(
                        EmployeeFromTable(
                            it[EmployeesTable.id],
                            it[EmployeesTable.name],
                            it[EmployeesTable.groupId]
                        )
                    )
                }
            }
            listEmployees.sortBy { it.id }
        }
        Row {
            UiButton(
                if (listEmployees.size == 0) DatabaseOff else Icons.Default.Update,
                modifier = Modifier.size(120.dp)
            ) {
                listEmployees.clear()
                transaction {
                    EmployeesTable.selectAll().forEach {
                        listEmployees.add(
                            EmployeeFromTable(
                                it[EmployeesTable.id],
                                it[EmployeesTable.name],
                                it[EmployeesTable.groupId]
                            )
                        )
                    }
                }
                listEmployees.sortBy { it.id }
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Card(
                    elevation = 25.dp,
                    modifier = Modifier.heightIn(min = 55.dp)
                ) {
                    Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
                        Icon(
                            Icons.Default.People, contentDescription = null,
                            modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        )
                        Text(
                            "Сотрудники",
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
                                            listEmployees.sortByDescending { it.id }
                                        } else {
                                            descending = true
                                            listEmployees.sortBy { it.id }
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
                                            listEmployees.sortByDescending { it.name }
                                        } else {
                                            descending = true
                                            listEmployees.sortBy { it.name }
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
                                            listEmployees.sortByDescending { it.groupId }
                                        } else {
                                            descending = true
                                            listEmployees.sortBy { it.groupId }
                                        }
                                    }
                            )
                            Text(
                                "GroupID",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
        ListDevices(listEmployees)
    }
}

@Composable
private fun ListDevices(listEmployees: MutableList<EmployeeFromTable>) {
    LazyColumn(modifier = Modifier.padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(listEmployees) {
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
                        deleteEmployee(it.id)
                        listEmployees.remove(it)
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
                            "${it.groupId} ",
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
                var newName by remember { mutableStateOf("") }
                var newGroupId by remember { mutableStateOf("") }
                UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
                    if (newId == "") {
                        try {
                            insertEmployee(newName, newGroupId.toInt())
                            listEmployees.add(EmployeeFromTable(0, newName, newGroupId.toInt()))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            insertEmployee(newId.toInt(), newName, newGroupId.toInt())
                            listEmployees.add(
                                EmployeeFromTable(
                                    newId.toInt(),
                                    newName,
                                    newGroupId.toInt()
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
                            newGroupId,
                            { newGroupId = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun insertEmployee(newId: Int, newName: String, newGroupId: Int) {
    transaction {
        EmployeesTable.insert {
            it[id] = newId
            it[name] = newName
            it[groupId] = newGroupId
        }
    }
}

private fun insertEmployee(newName: String, newGroupId: Int) {
    transaction {
        EmployeesTable.insert {
            it[name] = newName
            it[groupId] = newGroupId
        }
    }
}

private fun deleteEmployee(id: Int) {
    transaction {
        EmployeesTable.deleteWhere { EmployeesTable.id.eq(id) }
    }
}