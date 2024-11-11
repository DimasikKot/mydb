package composable.report

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import composable.ui.UiButton
import data.DateTransformation
import data.formatDate
import data.viewModels.*

@Composable
fun ReportBar(
    tabVM: TablesReportViewModel,
    modifier: Modifier = Modifier,
) {
    tabVM.listUpdate()
    Column(modifier.animateContentSize()) {
        Row {
            UiButton(
                Icons.Default.Update,
                modifier = Modifier.height(55.dp).width(120.dp)
            ) {
                tabVM.listUpdate()
            }
            RowInfo(tabVM, Modifier.padding(start = 10.dp))
        }
        RowDevice(tabVM.deviceGet(), Modifier.padding(top = 10.dp))
        RowBar(tabVM, Modifier.padding(top = 10.dp))
        if (tabVM.searching) {
            RowSearch(tabVM, Modifier.padding(top = 10.dp))
        }
        if (tabVM.creating) {
            RowCreate(tabVM, Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
private fun RowInfo(
    tabVM: TablesReportViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
            Icon(
                Icons.Default.Devices, contentDescription = null,
                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
            )
            Text(
                "Учёт устройств",
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
private fun RowDevice(
    reportDevice: ReportDeviceFromTables,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row {
                Text(
                    "Код устройства",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.id.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Название устройства",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.name,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Дата приобретения",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    formatDate(reportDevice.date),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Цена приобретения",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.price.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }

            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Код типа",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.typeId.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Название типа",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.typeName,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun RowBar(
    tabVM: TablesReportViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Card(
            elevation = 10.dp,
            modifier = Modifier.size(55.dp).clickable {
                tabVM.searching = !tabVM.searching
            }) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                Modifier.size(35.dp).align(Alignment.CenterVertically).background(MaterialTheme.colors.secondaryVariant)
            )
        }
        Card(
            elevation = 10.dp,
            modifier = Modifier.padding(start = 10.dp).size(55.dp)
                .clickable {
                    tabVM.creating = !tabVM.creating
                }) {
            Icon(
                Icons.Default.NewLabel,
                contentDescription = null,
                Modifier.size(35.dp).align(Alignment.CenterVertically).background(MaterialTheme.colors.secondaryVariant)
            )
        }
        Card(
            elevation = 10.dp,
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Row(
                Modifier
                    .background(MaterialTheme.colors.secondaryVariant).padding(10.dp)
            ) {
                Row(Modifier.weight(1f)) {
                    var descending by remember { mutableStateOf(false) }
                    Icon(
                        if (descending) {
                            if (tabVM.order1 == "strings.id DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                        } else {
                            if (tabVM.order1 == "strings.id") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                            .clickable {
                                descending = tabVM.listOrderBy("strings.id")
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
                            if (tabVM.order1 == "strings.date DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                        } else {
                            if (tabVM.order1 == "strings.date") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                            .clickable {
                                descending = tabVM.listOrderBy("strings.date")
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
                            if (tabVM.order1 == "employees.id DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                        } else {
                            if (tabVM.order1 == "employees.id") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                            .clickable {
                                descending = tabVM.listOrderBy("employees.id")
                            }
                    )
                    Text(
                        "ID сотрудника",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row(Modifier.weight(1f).padding(start = 10.dp)) {
                    var descending by remember { mutableStateOf(false) }
                    Icon(
                        if (descending) {
                            if (tabVM.order1 == "employees.name DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                        } else {
                            if (tabVM.order1 == "employees.name") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                            .clickable {
                                descending = tabVM.listOrderBy("employees.name")
                            }
                    )
                    Text(
                        "Название сотрудника",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row(Modifier.weight(1f).padding(start = 10.dp)) {
                    var descending by remember { mutableStateOf(false) }
                    Icon(
                        if (descending) {
                            if (tabVM.order1 == "groups.id DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                        } else {
                            if (tabVM.order1 == "groups.id") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                            .clickable {
                                descending = tabVM.listOrderBy("groups.id")
                            }
                    )
                    Text(
                        "ID групп",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row(Modifier.weight(1f).padding(start = 10.dp)) {
                    var descending by remember { mutableStateOf(false) }
                    Icon(
                        if (descending) {
                            if (tabVM.order1 == "groups.name DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                        } else {
                            if (tabVM.order1 == "groups.name") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                            .clickable {
                                descending = tabVM.listOrderBy("groups.name")
                            }
                    )
                    Text(
                        "Название группы",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Composable
private fun RowSearch(
    tabVM: TablesReportViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        UiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
            tabVM.listUpdate()
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
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
                    value = tabVM.whereEmployeeID,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereEmployeeID = it },
                    label = { Text(if (tabVM.whereEmployeeID == "") "Искать по ID сотрудника" else "Ищем по ID сотрудника") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = tabVM.whereEmployeeName,
                    onValueChange = { tabVM.whereEmployeeName = it },
                    label = { Text(if (tabVM.whereEmployeeName == "") "Искать по названию сотрудника" else "Ищем по названию сотрудника") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = tabVM.whereGroupId,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereGroupId = it },
                    label = { Text(if (tabVM.whereGroupId == "") "Искать по ID группы" else "Ищем по ID группы") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = tabVM.whereGroupName,
                    onValueChange = { tabVM.whereGroupName = it },
                    label = { Text(if (tabVM.whereGroupName == "") "Искать по названию группы" else "Ищем по названию группы") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun RowCreate(
    tabVM: TablesReportViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        var newId by remember { mutableStateOf("") }
        var newDate by remember { mutableStateOf("") }
        var newEmployeeId by remember { mutableStateOf("") }
        var newEmployeeIdMenu by remember { mutableStateOf(false) }
        var newEmployeeName by remember { mutableStateOf("") }
        var newGroupId by remember { mutableStateOf("") }
        var newGroupIdMenu by remember { mutableStateOf(false) }
        var newGroupName by remember { mutableStateOf("") }
        UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            if (newId == "") {
                TODO()
            } else {
                TODO()
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
                    onValueChange = { if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) newDate = it },
                    label = { Text("Новая дата") },
                    visualTransformation = DateTransformation(),
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
                            for (item in employeesVM.listGet()) {
                                DropdownMenuItem({
                                    newEmployeeId = item.id.toString()
                                    newEmployeeName = item.name
                                    newGroupId = item.groupId.toString()
                                    val groupVM by mutableStateOf(TablesGroupsViewModel())
                                    for (itemGroup in groupVM.listGet()) {
                                        if (item.groupId == itemGroup.id) {
                                            newGroupName = itemGroup.name
                                        }
                                    }
                                    newEmployeeIdMenu = false
                                }) {
                                    Text("${item.id}: ${item.name}")
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = newEmployeeName,
                    onValueChange = { newEmployeeName = it },
                    label = { Text("Новое название сотрудника") },
                    readOnly = true,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = newGroupId,
                    onValueChange = {
                        if (it.matches(regex = Regex("^\\d*\$"))) newGroupId = it
                        if (newGroupId.length == 1) newGroupIdMenu = true
                    },
                    label = {
                        Text("Новый ID группы")
                        DropdownMenu(
                            expanded = newGroupIdMenu,
                            onDismissRequest = { newGroupIdMenu = false },
                            offset = DpOffset(0.dp, 30.dp)
                        ) {
                            val groupVM = remember { TablesGroupsViewModel() }
                            DropdownMenuItem({}) {
                                TextField(
                                    value = groupVM.whereId,
                                    onValueChange = {
                                        if (it.matches(regex = Regex("^\\d*\$"))) {
                                            groupVM.whereId = it
                                            groupVM.listUpdate()
                                        }
                                    },
                                    label = { Text(if (groupVM.whereId == "") "Искать по ID группы" else "Ищем по ID группы") }
                                )
                            }
                            for (item in groupVM.listGet()) {
                                DropdownMenuItem({
                                    newGroupId = item.id.toString()
                                    newGroupName = item.name
                                    newGroupIdMenu = false
                                }) {
                                    Text("${item.id}: ${item.name}")
                                }
                            }
                        }
                    },
                    readOnly = true,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = newGroupName,
                    onValueChange = { newGroupName = it },
                    label = { Text("Новое названию группы") },
                    readOnly = true,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}