package composable.app.tables.types

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
import data.viewModels.MainViewModel
import data.viewModels.TypeFromTable
import data.viewModels.TablesTypesViewModel
import icons.DatabaseOff
import kotlinx.coroutines.delay

@Composable
fun appTablesTypesList(
    mainVM: MainViewModel,
    tabVM: TablesTypesViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tabVM.list) {
            row(mainVM, tabVM, it)
        }
        item {
            if (tabVM.list.isEmpty()) {
                Box(Modifier.fillMaxWidth().height(200.dp)) {
                    Icon(
                        imageVector = DatabaseOff,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp).align(Alignment.Center).fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun row(
    mainVM: MainViewModel,
    tabVM: TablesTypesViewModel,
    it: TypeFromTable,
) {
    var isView by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(it.number.toLong() * 100)
        isView = true
    }
    if (isView) {
        Row(Modifier) {
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
                rowUpdate(mainVM, it, newId, newName)
            } else {
                Card(elevation = 10.dp, modifier = Modifier.padding(start = 10.dp)) {
                    Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                        if (mainVM.setVM.isVision) {
                            Text(
                                it.id.toString(),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                            )
                        }
                        Text(
                            it.name,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                                .padding(start = if (mainVM.setVM.isVision) 10.dp else 0.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun rowUpdate(
    mainVM: MainViewModel,
    it: TypeFromTable,
    newId: MutableState<String>,
    newName: MutableState<String>,
) {
    Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
        Row(Modifier.padding(10.dp)) {
            if (mainVM.setVM.isVision) {
                TextField(
                    newId.value,
                    { if (it.matches(regex = Regex("^\\d*\$"))) newId.value = it },
                    label = { Text(if (newId.value == "") it.id.toString() else "Новый ID") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
            }
            TextField(
                newName.value,
                { newName.value = it },
                label = { Text(if (newName.value == "") it.name else "Новое название") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = if (mainVM.setVM.isVision) 10.dp else 0.dp)
            )
        }
    }
}