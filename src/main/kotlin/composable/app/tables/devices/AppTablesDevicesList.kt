package composable.app.tables.devices

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import composable.ui.uiButton
import data.DateTransformation
import data.viewModels.DeviceFromTable
import data.formatDate
import data.viewModels.MainViewModel
import data.viewModels.TablesDevicesViewModel
import data.viewModels.TablesTypesViewModel
import icons.DatabaseOff
import icons.ExportNotes
import icons.IconWindow
import icons.RefreshNotes
import kotlinx.coroutines.delay

@Composable
fun appTablesDevicesList(
    tabVM: TablesDevicesViewModel,
    mainVM: MainViewModel, debug: Boolean
) {
    if (tabVM.list.isEmpty()) {
        Box(Modifier.fillMaxWidth().height(200.dp)) {
            Icon(
                imageVector = DatabaseOff,
                contentDescription = null,
                modifier = Modifier.size(200.dp).align(Alignment.Center).fillMaxSize()
            )
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp).animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tabVM.list) {
            row(tabVM, it, mainVM, debug)
        }
    }
}

@Composable
private fun row(
    tabVM: TablesDevicesViewModel,
    it: DeviceFromTable,
    mainVM: MainViewModel, debug: Boolean
) {
    var isView by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(50)
        isView = true
    }
    if (isView) {
        Row {
            val newId = mutableStateOf(it.id.toString())
            val newName = mutableStateOf(it.name)
            val newDate = mutableStateOf(it.date)
            val newPrice = mutableStateOf(it.price.toString())
            val newTypeId = mutableStateOf(it.typeId.toString())
            val newTypeName = mutableStateOf(it.typeName)
            uiButton(
                if (!it.canUpdate.value) Icons.Default.EditOff else if (it.editing.value) Icons.Default.EditNote else Icons.Default.ModeEdit,
                modifier = Modifier.size(55.dp)
            ) {
                if (it.editing.value) {
                    it.canUpdate.value = tabVM.update(
                        it.id, newId.value, newName.value, newDate.value, newPrice.value, newTypeId.value
                    )
                    if (it.canUpdate.value) {
                        it.editing.value = false
                    }
                } else {
                    it.editing.value = true
                }
            }
            uiButton(
                if (it.canDelete.value) Icons.Default.Delete else Icons.Default.DeleteForever,
                modifier = Modifier.padding(start = 10.dp).size(55.dp)
            ) {
                it.canDelete.value = tabVM.delete(it.id)
            }
            if (it.editing.value) {
                rowUpdate(it, newId, newName, newDate, newPrice, newTypeId, newTypeName, debug)
            } else {
                Card(elevation = 10.dp, modifier = Modifier.padding(start = 10.dp)) {
                    Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                        if (debug) {
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
                                .padding(start = if (debug) 10.dp else 0.dp)
                        )
                        Text(
                            formatDate(it.date),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        Text(
                            it.price.toString(),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        if (debug) {
                            Text(
                                it.typeId.toString(),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                            )
                        }
                        Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                            Text(
                                it.typeName,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                if (mainVM.winVM.reportDevice == it.id) IconWindow else if (mainVM.winVM.reportDevice != 0) RefreshNotes else ExportNotes,
                                contentDescription = null,
                                modifier = Modifier.padding(start = 10.dp).size(35.dp).clickable {
                                    if (mainVM.winVM.reportDevice != 0) {
                                        mainVM.winVM.reportDevice = 0
                                    } else {
                                        mainVM.winVM.reportDevice = it.id
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun rowUpdate(
    it: DeviceFromTable,
    newId: MutableState<String>,
    newName: MutableState<String>,
    newDate: MutableState<String>,
    newPrice: MutableState<String>,
    newTypeId: MutableState<String>,
    newTypeName: MutableState<String>,
    debug: Boolean
) {
    var newTypeIdMenu by mutableStateOf(false)
    var newTypeNameMenu by mutableStateOf(false)
    Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
        Row(Modifier.padding(10.dp)) {
            if (debug) {
                TextField(
                    value = newId.value,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newId.value = it },
                    label = { Text(if (newId.value == "") it.id.toString() else "Новый ID") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
            }
            TextField(
                value = newName.value,
                onValueChange = { newName.value = it },
                label = { Text(if (newName.value == "") it.name else "Новое название") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    .padding(start = if (debug) 10.dp else 0.dp)
            )
            TextField(
                value = newDate.value,
                onValueChange = { if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) newDate.value = it },
                label = { Text(if (newDate.value == "") it.date else "Новая дата") },
                visualTransformation = DateTransformation(),
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                value = newPrice.value,
                onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newPrice.value = it },
                label = { Text(if (newPrice.value == "") it.price.toString() else "Новая цена") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            if (debug) {
                TextField(
                    value = newTypeId.value, onValueChange = {
                        if (it.matches(regex = Regex("^\\d*\$"))) newTypeId.value = it
                        if (newTypeId.value.length == 1) newTypeIdMenu = true
                    }, label = {
                        Text(if (newTypeId.value == "") it.typeId.toString() else "Новый ID типа")
                        DropdownMenu(
                            expanded = newTypeIdMenu,
                            onDismissRequest = { newTypeIdMenu = false },
                            offset = DpOffset(0.dp, 30.dp)
                        ) {
                            val typesVM = remember { TablesTypesViewModel() }
                            DropdownMenuItem({}) {
                                TextField(
                                    value = typesVM.whereId,
                                    onValueChange = {
                                        if (it.matches(regex = Regex("^\\d*\$"))) {
                                            typesVM.whereId = it
                                        }
                                    },
                                    label = { Text(if (typesVM.whereId == "") "Искать по ID типа" else "Ищем по ID типа") })
                            }
                            for (item in typesVM.list) {
                                DropdownMenuItem({
                                    newTypeId.value = item.id.toString()
                                    newTypeName.value = item.name
                                    newTypeIdMenu = false
                                }) {
                                    Text("${item.id}: ${item.name}")
                                }
                            }
                        }
                    }, modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            TextField(
                value = newTypeName.value, onValueChange = {
                    newTypeName.value = it
                    if (newTypeName.value.length == 1) newTypeNameMenu = true
                }, label = {
                    Text(if (newTypeName.value == "") it.typeName else "Новый тип")
                    DropdownMenu(
                        expanded = newTypeNameMenu,
                        onDismissRequest = { newTypeNameMenu = false },
                        offset = DpOffset(0.dp, 30.dp)
                    ) {
                        val typesVM = remember { TablesTypesViewModel() }
                        DropdownMenuItem({}) {
                            TextField(
                                value = typesVM.whereName,
                                onValueChange = {
                                    typesVM.whereName = it
                                },
                                label = { Text(if (typesVM.whereName == "") "Искать по названию типа" else "Ищем по названию типа") })
                        }
                        for (item in typesVM.list) {
                            DropdownMenuItem({
                                newTypeId.value = item.id.toString()
                                newTypeName.value = item.name
                                newTypeNameMenu = false
                            }) {
                                Text(item.name)
                            }
                        }
                    }
                }, modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
        }
    }
}