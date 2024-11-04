package composable.app.tables.strings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.ui.UiButton
import data.DateTransformation
import data.StringFromTable
import data.viewModels.TablesStringsViewModel

@Composable
fun AppTablesStringsList(tabVM: TablesStringsViewModel) {
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
    tabVM: TablesStringsViewModel,
    it: StringFromTable,
) {
    Row {
        val newId = mutableStateOf(it.id.toString())
        val newDate = mutableStateOf(it.date)
        val newDeviceId = mutableStateOf(it.deviceId.toString())
        val newEmployeeId = mutableStateOf(it.employeeId.toString())
        UiButton(
            if (!it.canUpdate.value) Icons.Default.EditOff else if (it.editing.value) Icons.Default.EditNote else Icons.Default.ModeEdit,
            modifier = Modifier.size(55.dp)
        ) {
            if (it.editing.value) {
                it.canUpdate.value = tabVM.update(
                    it.id,
                    newId.value.toInt(),
                    newDate.value,
                    newDeviceId.value.toInt(),
                    newEmployeeId.value.toInt()
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
            RowUpdate(it, newId, newDate, newDeviceId, newEmployeeId)
        } else {
            Card(elevation = 10.dp, modifier = Modifier.padding(start = 10.dp)) {
                Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                    Text(
                        it.id.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    )
                    Text(
                        it.date,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.deviceId.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.employeeId.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun RowUpdate(
    it: StringFromTable,
    newId: MutableState<String>,
    newDate: MutableState<String>,
    newDeviceId: MutableState<String>,
    newEmployeeId: MutableState<String>,
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
                singleLine = true,
                value = newDate.value,
                onValueChange = { if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) newDate.value = it },
                label = { Text(if (newDate.value == "") it.date else "Новая дата") },
                visualTransformation = DateTransformation(),
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                newDeviceId.value,
                { if (it.matches(regex = Regex("^\\d*\$"))) newDeviceId.value = it },
                label = { Text(if (newDeviceId.value == "") it.deviceId.toString() else "Новый ID устройства") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                newEmployeeId.value,
                { if (it.matches(regex = Regex("^\\d*\$"))) newEmployeeId.value = it },
                label = { Text(if (newEmployeeId.value == "") it.employeeId.toString() else "Новый ID сотрудника") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
        }
    }
}