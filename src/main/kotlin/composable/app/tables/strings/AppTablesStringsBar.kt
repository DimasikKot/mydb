package composable.app.tables.strings

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import data.viewModels.TablesDevicesViewModel
import data.viewModels.TablesEmployeesViewModel
import data.viewModels.TablesStringsViewModel

@Composable
fun appTablesStringsBar(tabVM: TablesStringsViewModel) {
    Column(Modifier.animateContentSize()) {
        Row {
            uiButton(
                Icons.Default.Update,
                modifier = Modifier.size(120.dp)
            ) {
                tabVM.listUpdate()
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                rowInfo(tabVM, Modifier.heightIn(min = 55.dp))
                rowBar(tabVM, Modifier.padding(top = 10.dp).heightIn(min = 55.dp))
            }
        }
        if (tabVM.searching) {
            rowSearch(tabVM, Modifier.padding(top = 10.dp))
        }
        if (tabVM.creating) {
            rowCreate(tabVM, Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
private fun rowInfo(
    tabVM: TablesStringsViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
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
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                Modifier.align(Alignment.CenterVertically).padding(start = 10.dp).size(35.dp).clickable {
                    tabVM.searching = !tabVM.searching
                })
            Icon(
                Icons.Default.NewLabel,
                contentDescription = null,
                Modifier.align(Alignment.CenterVertically).padding(start = 10.dp).size(35.dp).clickable {
                    tabVM.creating = !tabVM.creating
                })
        }
    }
}

@Composable
private fun rowBar(
    tabVM: TablesStringsViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Row(
            Modifier
                .background(MaterialTheme.colors.secondaryVariant).padding(10.dp)
        ) {
            Row(Modifier.weight(1f)) {
                var descending by remember { mutableStateOf(false) }
                Icon(
                    if (descending) {
                        if (tabVM.order1 == "id DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                    } else {
                        if (tabVM.order1 == "id") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        .clickable {
                            descending = tabVM.listOrderBy("id")
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
                    if (descending) {
                        if (tabVM.order1 == "date DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                    } else {
                        if (tabVM.order1 == "date") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        .clickable {
                            descending = tabVM.listOrderBy("date")
                        }
                )
                Text(
                    "Даты",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row(Modifier.weight(1f).padding(start = 10.dp)) {
                var descending by remember { mutableStateOf(false) }
                Icon(
                    if (descending) {
                        if (tabVM.order1 == "device_id DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                    } else {
                        if (tabVM.order1 == "device_id") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        .clickable {
                            descending = tabVM.listOrderBy("device_id")
                        }
                )
                Text(
                    "ID устройств",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row(Modifier.weight(1f).padding(start = 10.dp)) {
                var descending by remember { mutableStateOf(false) }
                Icon(
                    if (descending) {
                        if (tabVM.order1 == "employee_id DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                    } else {
                        if (tabVM.order1 == "employee_id") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        .clickable {
                            descending = tabVM.listOrderBy("employee_id")
                        }
                )
                Text(
                    "ID сотрудников",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
private fun rowSearch(
    tabVM: TablesStringsViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        uiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
            tabVM.listUpdate()
        }
        Card(elevation = 25.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                TextField(
                    value = tabVM.whereId,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereId = it },
                    label = { Text(if (tabVM.whereId == "") "Искать по ID" else "Ищем по ID") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                TextField(
                    value = tabVM.whereDate,
                    onValueChange = {
                        if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) tabVM.whereDate = it
                    },
                    label = { Text(if (tabVM.whereDate == "") "Искать по дате" else "Ищем по дате") },
                    visualTransformation = DateTransformation(),
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = tabVM.whereDeviceId,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereDeviceId = it },
                    label = { Text(if (tabVM.whereDeviceId == "") "Искать по ID устройства" else "Ищем по ID устройства") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = tabVM.whereEmployeeId,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereEmployeeId = it },
                    label = { Text(if (tabVM.whereEmployeeId == "") "Искать по ID сотрудника" else "Ищем по ID сотрудника") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun rowCreate(
    tabVM: TablesStringsViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        var newId by remember { mutableStateOf("") }
        var newDate by remember { mutableStateOf("") }
        var newDeviceId by remember { mutableStateOf("") }
        var newDeviceIdMenu by remember { mutableStateOf(false) }
        var newEmployeeId by remember { mutableStateOf("") }
        var newEmployeeIdMenu by remember { mutableStateOf(false) }
        uiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            if (newId == "") {
                tabVM.insert(newDate, newDeviceId.toInt(), newEmployeeId.toInt())
            } else {
                tabVM.insert(newId.toInt(), newDate, newDeviceId.toInt(), newEmployeeId.toInt())
            }
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                TextField(
                    value = newId,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newId = it },
                    label = { Text(if (newId == "") "Автоматический ID" else "Новый ID") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                TextField(
                    value = newDate,
                    onValueChange = {
                        if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) newDate = it
                    },
                    label = { Text("Новая дата") },
                    visualTransformation = DateTransformation(),
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = newDeviceId,
                    onValueChange = {
                        if (it.matches(regex = Regex("^\\d*\$"))) newDeviceId = it
                        if (newDeviceId.length == 1) newDeviceIdMenu = true
                    },
                    label = {
                        Text("Новый ID устройства")
                        DropdownMenu(
                            expanded = newDeviceIdMenu,
                            onDismissRequest = { newDeviceIdMenu = false },
                            offset = DpOffset(0.dp, 30.dp)
                        ) {
                            val devicesVM = remember { TablesDevicesViewModel() }
                            DropdownMenuItem({}) {
                                TextField(
                                    value = devicesVM.whereId,
                                    onValueChange = {
                                        if (it.matches(regex = Regex("^\\d*\$"))) {
                                            devicesVM.whereId = it
                                            devicesVM.listUpdate()
                                        }
                                    },
                                    label = { Text(if (devicesVM.whereId == "") "Искать по ID устройства" else "Ищем по ID устройства") }
                                )
                            }
                            for (item in devicesVM.list) {
                                DropdownMenuItem({
                                    newDeviceId = item.id.toString()
                                    newDeviceIdMenu = false
                                }) {
                                    Text("${item.id}: ${item.name}")
                                }
                            }
                            devicesVM.listUpdate()
                        }
                    },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = newEmployeeId,
                    onValueChange = {
                        if (it.matches(regex = Regex("^\\d*\$"))) newEmployeeId = it
                        if (newEmployeeId.length == 1) newEmployeeIdMenu = true
                    },
                    label = {
                        Text("Новый ID сотрудника")
                        DropdownMenu(
                            expanded = newEmployeeIdMenu,
                            onDismissRequest = { newEmployeeIdMenu = false },
                            offset = DpOffset(0.dp, 30.dp)
                        ) {
                            val employeesVM = remember { TablesEmployeesViewModel() }
                            DropdownMenuItem({}) {
                                TextField(
                                    value = employeesVM.whereId,
                                    onValueChange = {
                                        if (it.matches(regex = Regex("^\\d*\$"))) {
                                            employeesVM.whereId = it
                                            employeesVM.listUpdate()
                                        }
                                    },
                                    label = { Text(if (employeesVM.whereId == "") "Искать по ID сотрудника" else "Ищем по ID сотрудника") }
                                )
                            }
                            for (item in employeesVM.list) {
                                DropdownMenuItem({
                                    newEmployeeId = item.id.toString()
                                    newEmployeeIdMenu = false
                                }) {
                                    Text("${item.id}: ${item.name}")
                                }
                            }
                            employeesVM.listUpdate()
                        }
                    },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}