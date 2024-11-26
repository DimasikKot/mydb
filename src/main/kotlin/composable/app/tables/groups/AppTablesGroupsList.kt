package composable.app.tables.groups

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
import composable.ui.uiButton
import data.GroupFromTable
import data.viewModels.TablesGroupsViewModel
import icons.ExportNotes
import icons.IconWindow
import icons.RefreshNotes

@Composable
fun appTablesGroupsList(tabVM: TablesGroupsViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tabVM.listGet()) {
            row(tabVM, it)
        }
    }
}

@Composable
private fun row(
    tabVM: TablesGroupsViewModel,
    it: GroupFromTable,
) {
    Row {
        val newId = mutableStateOf(it.id.toString())
        val newName = mutableStateOf(it.name)
        uiButton(
            if (!it.canUpdate.value) Icons.Default.EditOff else if (it.editing.value) Icons.Default.EditNote else Icons.Default.ModeEdit,
            modifier = Modifier.size(55.dp)
        ) {
            if (it.editing.value) {
                it.canUpdate.value = tabVM.update(it.id, newId.value.toInt(), newName.value)
                if (it.canUpdate.value) {
                    it.editing.value = false
                }
            } else {
                it.editing.value = true
            }
        }
        uiButton(
            if (it.canDelete.value) Icons.Default.Delete else Icons.Default.DeleteForever,
            modifier = Modifier
                .padding(start = 10.dp).size(55.dp)
        ) {
            it.canDelete.value = tabVM.delete(it.id)
        }
        if (it.editing.value) {
            rowUpdate(it, newId, newName)
        } else {
            Card(elevation = 10.dp, modifier = Modifier.padding(start = 10.dp)) {
                Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                    Text(
                        it.id.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    )
                    Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                        Text(
                            it.name,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            if (tabVM.report.value == it.id) IconWindow else if (tabVM.report.value != 0) RefreshNotes else ExportNotes,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 10.dp).size(35.dp)
                                .clickable {
                                    if (tabVM.report.value != 0) {
                                        tabVM.report.value = 0
                                    } else {
                                        tabVM.report.value = it.id
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun rowUpdate(
    it: GroupFromTable,
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