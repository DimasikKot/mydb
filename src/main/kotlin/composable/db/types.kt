package composable.db

import DatabaseOff
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
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun Types() {
    Column {
        val allTypes = remember { mutableStateListOf<DbType>() }
        var isCreate by remember { mutableStateOf(false) }
        if (!isCreate) {
            isCreate = true
            allTypes.clear()
            transaction {
                DbTypes.selectAll().forEach { allTypes.add(DbType(it[DbTypes.id], it[DbTypes.name])) }
            }
            allTypes.sortBy { it.id }
        }
        Row {
            UiButton(
                if (allTypes.size == 0) DatabaseOff else Icons.Default.Update,
                modifier = Modifier.size(120.dp)
            ) {
                allTypes.clear()
                transaction {
                    DbTypes.selectAll().forEach { allTypes.add(DbType(it[DbTypes.id], it[DbTypes.name])) }
                }
                allTypes.sortBy { it.id }
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Card(
                    elevation = 25.dp,
                    modifier = Modifier.heightIn(min = 55.dp)
                ) {
                    Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
                        Icon(
                            Icons.Default.Style, contentDescription = null,
                            modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        )
                        Text(
                            "Типы устройств",
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
                                            allTypes.sortByDescending { it.id }
                                        } else {
                                            descending = true
                                            allTypes.sortBy { it.id }
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
                                            allTypes.sortByDescending { it.name }
                                        } else {
                                            descending = true
                                            allTypes.sortBy { it.name }
                                        }
                                    }
                            )
                            Text(
                                "Name",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
        ListTypes(allTypes)
    }
}

@Composable
private fun ListTypes(allTypes: MutableList<DbType>) {
    LazyColumn(modifier = Modifier.padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(allTypes) {
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
                        deleteType(it.id)
                        allTypes.remove(it)
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
                    }
                }
            }
        }
        item {
            Row {
                var newId by remember { mutableStateOf("AUTO") }
                var newName by remember { mutableStateOf("") }
                UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
                    if (newId == "AUTO") {
                        try {
                            insertType(newName)
                            allTypes.add(DbType(0, newName))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            insertType(newId.toInt(), newName)
                            allTypes.add(DbType(newId.toInt(), newName))
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
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                        )
                        TextField(
                            newName,
                            { newName = it },
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun insertType(newId: Int, newName: String) {
    transaction {
        DbTypes.insert {
            it[id] = newId
            it[name] = newName
        }
    }
}

private fun insertType(newName: String) {
    transaction {
        DbTypes.insert {
            it[name] = newName
        }
    }
}

private fun deleteType(id: Int) {
    transaction {
        DbTypes.deleteWhere { DbTypes.id eq id }
    }
}