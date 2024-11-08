package composable.app.tables.devices

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
import data.DateTransformation
import data.DeviceFromTable
import data.viewModels.TablesDevicesViewModel
import icons.ExportNotes
import icons.IconWindow

@Composable
fun AppTablesDevicesList(tabVM: TablesDevicesViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tabVM.listGet()) {
            Row(tabVM, it)
        }
    }
}

@Composable
private fun Row(
    tabVM: TablesDevicesViewModel,
    it: DeviceFromTable,
) {
    Row {
        val newId = mutableStateOf(it.id.toString())
        val newName = mutableStateOf(it.name)
        val newDate = mutableStateOf(it.date)
        val newPrice = mutableStateOf(it.price.toString())
        val newTypeId = mutableStateOf(it.typeId.toString())
        UiButton(
            if (!it.canUpdate.value) Icons.Default.EditOff else if (it.editing.value) Icons.Default.EditNote else Icons.Default.ModeEdit,
            modifier = Modifier.size(55.dp)
        ) {
            if (it.editing.value) {
                it.canUpdate.value = tabVM.update(
                    it.id,
                    newId.value.toInt(),
                    newName.value,
                    newDate.value,
                    newPrice.value.toInt(),
                    newTypeId.value.toInt()
                )
                if (it.canUpdate.value) {
                    it.editing.value = false
                }
            } else {
                it.editing.value = true
            }
        }
        UiButton(
            if (it.canDelete.value) Icons.Default.Delete else Icons.Default.DeleteForever,
            modifier = Modifier
                .padding(start = 10.dp).size(55.dp)
        ) {
            it.canDelete.value = tabVM.delete(it.id)
        }
        if (it.editing.value) {
            RowUpdate(it, newId, newName, newDate, newPrice, newTypeId)
        } else {
            Card(elevation = 10.dp, modifier = Modifier.padding(start = 10.dp)) {
                Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                    Text(
                        it.id.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    )
                    Text(
                        it.name,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.date,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.price.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                        Text(
                            it.typeId.toString(),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            if (tabVM.report == it.id) IconWindow else ExportNotes,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 10.dp).size(35.dp)
                                .clickable {
                                    if (tabVM.report != 0) {
                                        tabVM.report = 0
                                    } else {
                                        tabVM.report = it.id
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
private fun RowUpdate(
    it: DeviceFromTable,
    newId: MutableState<String>,
    newName: MutableState<String>,
    newDate: MutableState<String>,
    newPrice: MutableState<String>,
    newTypeId: MutableState<String>,
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
            TextField(
                singleLine = true,
                value = newDate.value,
                onValueChange = { if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) newDate.value = it },
                label = { Text(if (newDate.value == "") it.date else "Новая дата") },
                visualTransformation = DateTransformation(),
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                newPrice.value,
                { if (it.matches(regex = Regex("^\\d*\$"))) newPrice.value = it },
                label = { Text(if (newPrice.value == "") it.price.toString() else "Новая цена") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                newTypeId.value,
                { if (it.matches(regex = Regex("^\\d*\$"))) newTypeId.value = it },
                label = { Text(if (newTypeId.value == "") it.typeId.toString() else "Новый ID типа") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
        }
    }
}