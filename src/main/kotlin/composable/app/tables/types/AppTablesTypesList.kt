package composable.app.tables.types

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.ui.UiButton
import data.TypeFromTable
import data.viewModels.MainViewModel

@Composable
fun AppTablesTypesList(mainVM: MainViewModel) {
    TypeRowSearch(mainVM, Modifier.padding(top = 10.dp))
    LazyColumn(modifier = Modifier.padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            TypeRowCreate(mainVM)
        }
        items(mainVM.tabVM.typesGetList()) {
            TypeRow(mainVM, it)
        }
    }
}

@Composable
private fun TypeRowSearch(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        UiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
            mainVM.tabVM.typesUpdate()
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                TextField(
                    mainVM.tabVM.typesWhereId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) mainVM.tabVM.typesWhereId = it },
                    label = { Text(if (mainVM.tabVM.typesWhereId == "") "Искать по ID" else "Ищем по ID") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                TextField(
                    mainVM.tabVM.typesWhereName,
                    { mainVM.tabVM.typesWhereName = it },
                    label = { Text(if (mainVM.tabVM.typesWhereName == "") "Искать по названию" else "Ищем по названию") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun TypeRowCreate(mainVM: MainViewModel) {
    Row {
        var newId by remember { mutableStateOf("") }
        var newName by remember { mutableStateOf("") }
        UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            if (newId == "") {
                mainVM.tabVM.typeInsert(newName)
            } else {
                mainVM.tabVM.typeInsert(newId.toInt(), newName)
            }
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                TextField(
                    newId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) newId = it },
                    label = { Text(if (newId == "") "Автоматический ID" else "Новый ID") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                TextField(
                    newName,
                    { newName = it },
                    label = { Text("Новое название") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun TypeRow(
    mainVM: MainViewModel,
    type: TypeFromTable,
) {
    Row {
        val newId = mutableStateOf(type.id.toString())
        val newName = mutableStateOf(type.name)
        UiButton(
            if (!type.canUpdate.value) Icons.Default.EditOff else if (type.editing.value) Icons.Default.EditNote else Icons.Default.ModeEdit,
            modifier = Modifier.size(55.dp)
        ) {
            if (type.editing.value) {
                type.canUpdate.value = mainVM.tabVM.typeUpdate(type.id, newId.value.toInt(), newName.value)
                if (type.canUpdate.value) {
                    type.editing.value = false
                }
            } else {
                type.editing.value = true
            }
        }
        UiButton(
            if (type.canDelete.value) Icons.Default.Delete else Icons.Default.DeleteForever,
            modifier = Modifier
                .padding(start = 10.dp).size(55.dp)
        ) {
            type.canDelete.value = mainVM.tabVM.typeDelete(type.id)
        }
        if (type.editing.value) {
            TypeRowUpdate(type, newId, newName)
        } else {
            Card(elevation = 10.dp, modifier = Modifier.padding(start = 10.dp)) {
                Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                    Text(
                        type.id.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    )
                    Text(
                        type.name,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TypeRowUpdate(
    it: TypeFromTable,
    newId: MutableState<String>,
    newName: MutableState<String>,
) {
    Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
        Row(Modifier.padding(10.dp)) {
            TextField(
                newId.value,
                { if (it.matches(regex = Regex("^\\d*\$"))) newId.value = it },
                label = { Text(if (newId.value == "") it.id.toString() else "Новый ID") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
            )
            TextField(
                newName.value,
                { newName.value = it },
                label = { Text(if (newName.value == "") it.name else "Новое название") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
        }
    }
}