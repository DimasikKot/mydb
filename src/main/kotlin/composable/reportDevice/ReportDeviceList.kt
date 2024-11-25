package composable.reportDevice

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
import data.formatDate
import data.viewModels.ReportStringFromTables
import data.viewModels.TablesEmployeesViewModel
import data.viewModels.TablesGroupsViewModel
import data.viewModels.TablesReportDeviceViewModel
import icons.ExportNotes
import icons.IconWindow

@Composable
fun reportDeviceList(
    tabVM: TablesReportDeviceViewModel,
    reportEmployee: MutableIntState,
    reportGroup: MutableIntState,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp, modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tabVM.listGet()) {
                row(tabVM, reportEmployee, reportGroup, it)
            }
        }
    }
}

@Composable
private fun row(
    tabVM: TablesReportDeviceViewModel,
    reportEmployee: MutableIntState,
    reportGroup: MutableIntState,
    it: ReportStringFromTables,
) {
    Row {
        val newId = mutableStateOf(it.id.toString())
        val newDate = mutableStateOf(it.date)
        val newEmployeeId = mutableStateOf(it.employeeID.toString())
        val newEmployeeName = mutableStateOf(it.employeeName)
        val newGroupId = mutableStateOf(it.groupId.toString())
        val newGroupName = mutableStateOf(it.groupName)
        uiButton(
            if (!it.canUpdate.value) Icons.Default.EditOff else if (it.editing.value) Icons.Default.EditNote else Icons.Default.ModeEdit,
            modifier = Modifier.size(55.dp)
        ) {
            if (it.editing.value) {
                it.canUpdate.value = tabVM.update(
                    it.id,
                    newId.value.toInt(),
                    newDate.value,
                    newEmployeeId.value.toInt(),
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
            rowUpdate(it, newId, newDate, newEmployeeId, newEmployeeName, newGroupId, newGroupName)
        } else {
            Card(elevation = 10.dp, modifier = Modifier.padding(start = 10.dp)) {
                Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                    Text(
                        it.number.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.width(55.dp).align(Alignment.CenterVertically)
                    )
                    Text(
                        it.id.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        formatDate(it.date),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.employeeID.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(0.7f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                        Icon(
                            if (reportEmployee.value == it.employeeID) IconWindow else ExportNotes,
                            contentDescription = null,
                            modifier = Modifier.size(35.dp).clickable {
                                if (reportEmployee.value != 0) {
                                    reportEmployee.value = 0
                                } else {
                                    reportEmployee.value = it.employeeID
                                }
                            }
                        )
                        Text(
                            it.employeeName,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(start = 10.dp).align(Alignment.CenterVertically).weight(1f)
                        )
                    }
                    Text(
                        it.groupId.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                        Icon(
                            if (reportGroup.value == it.groupId) IconWindow else ExportNotes,
                            contentDescription = null,
                            modifier = Modifier.size(35.dp).clickable {
                                if (reportGroup.value != 0) {
                                    reportGroup.value = 0
                                } else {
                                    reportGroup.value = it.groupId
                                }
                            }
                        )
                        Text(
                            it.groupName,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 10.dp).weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun rowUpdate(
    it: ReportStringFromTables,
    newId: MutableState<String>,
    newDate: MutableState<String>,
    newEmployeeId: MutableState<String>,
    newEmployeeName: MutableState<String>,
    newGroupId: MutableState<String>,
    newGroupName: MutableState<String>,
) {
    var number by remember { mutableStateOf(it.number.toString()) }
    var newEmployeeIdMenu by remember { mutableStateOf(false) }
    var newGroupIdMenu by remember { mutableStateOf(false) }
    Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
        Row(Modifier.padding(10.dp)) {
            TextField(
                value = number,
                onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) number = it },
                label = { Text(if (newId.value == "") it.id.toString() else "№") },
                readOnly = true,
                modifier = Modifier.width(55.dp).align(Alignment.CenterVertically)
            )
            TextField(
                value = newId.value,
                onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newId.value = it },
                label = { Text(if (newId.value == "") it.id.toString() else "Новый ID") },
                modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                value = newDate.value,
                onValueChange = { if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) newDate.value = it },
                label = { Text(if (newDate.value == "") it.date else "Новая дата") },
                visualTransformation = DateTransformation(),
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                value = newEmployeeId.value, onValueChange = {
                if (it.matches(regex = Regex("^\\d*\$"))) newEmployeeId.value = it
                if (newEmployeeId.value.length == 1) newEmployeeIdMenu = true
            }, label = {
                Text(if (newEmployeeId.value == "") it.employeeID.toString() else "Новый ID сотрудника")
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
                            label = { Text(if (employeesVM.whereId == "") "Искать по ID сотрудника" else "Ищем по ID сотрудника") })
                    }
                    for (item in employeesVM.listGet()) {
                        DropdownMenuItem({
                            newEmployeeId.value = item.id.toString()
                            newEmployeeName.value = item.name
                            newGroupId.value = item.groupId.toString()
                            val groupVM by mutableStateOf(TablesGroupsViewModel())
                            for (itemGroup in groupVM.listGet()) {
                                if (item.groupId == itemGroup.id) {
                                    newGroupName.value = itemGroup.name
                                }
                            }
                            newEmployeeIdMenu = false
                        }) {
                            Text("${item.id}: ${item.name}")
                        }
                    }
                }
            }, modifier = Modifier.weight(0.7f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                value = newEmployeeName.value,
                onValueChange = { newEmployeeName.value = it },
                label = { Text(if (newEmployeeName.value == "") it.employeeName else "Новое название сотрудника") },
                readOnly = true,
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                value = newGroupId.value,
                onValueChange = {
                    if (it.matches(regex = Regex("^\\d*\$"))) newGroupId.value = it
                    if (newGroupId.value.length == 1) newGroupIdMenu = true
                },
                label = {
                    Text(if (newGroupId.value == "") it.groupId.toString() else "Новый ID группы")
                    DropdownMenu(
                        expanded = newGroupIdMenu,
                        onDismissRequest = { newGroupIdMenu = false },
                        offset = DpOffset(0.dp, 30.dp)
                    ) {
                        val groupsVM = remember { TablesGroupsViewModel() }
                        DropdownMenuItem({}) {
                            TextField(
                                value = groupsVM.whereId,
                                onValueChange = {
                                    if (it.matches(regex = Regex("^\\d*\$"))) {
                                        groupsVM.whereId = it
                                        groupsVM.listUpdate()
                                    }
                                },
                                label = { Text(if (groupsVM.whereId == "") "Искать по ID группы" else "Ищем по ID группы") })
                        }
                        for (item in groupsVM.listGet()) {
                            DropdownMenuItem({
                                newGroupId.value = item.id.toString()
                                newGroupName.value = item.name
                                newGroupIdMenu = false
                            }) {
                                Text("${item.id}: ${item.name}")
                            }
                        }
                    }
                },
                readOnly = true,
                modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                value = newGroupName.value,
                onValueChange = { newGroupName.value = it },
                label = { Text(if (newGroupName.value == "") it.groupName else "Новое название сотрудника") },
                readOnly = true,
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
        }
    }
}