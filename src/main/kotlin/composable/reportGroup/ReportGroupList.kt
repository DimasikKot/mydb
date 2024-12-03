package composable.reportGroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.viewModels.*
import icons.ExportNotes
import icons.IconWindow
import icons.RefreshNotes

@Composable
fun reportGroupList(
    tabVM: TablesReportGroupViewModel,
    reportEmployee: MutableIntState,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tabVM.list) {
                row(tabVM, reportEmployee, it)
            }
        }
    }
}

@Composable
private fun row(
    tabVM: TablesReportGroupViewModel,
    reportEmployee: MutableIntState,
    it: ReportGroupStringFromTables,
) {
    Row {
        val newId = mutableStateOf(it.id.toString())
        val newName = mutableStateOf(it.name)
        val newTotalPrice = mutableStateOf(it.totalPrice.toString())
//        uiButton(
//            if (!it.canUpdate.value) Icons.Default.EditOff else if (it.editing.value) Icons.Default.EditNote else Icons.Default.ModeEdit,
//            modifier = Modifier.size(55.dp)
//        ) {
//            if (it.editing.value) {
//                it.canUpdate.value = tabVM.update(
//                    it.id,
//                    newId.value.toInt(),
//                    newName.value,
//                    newTotalPrice.value.toInt(),
//                )
//                if (it.canUpdate.value) {
//                    it.editing.value = false
//                }
//            } else {
//                it.editing.value = true
//            }
//        }
//        uiButton(
//            if (it.canDelete.value) Icons.Default.Delete else Icons.Default.DeleteForever,
//            modifier = Modifier
//                .padding(start = 10.dp).size(55.dp)
//        ) {
//            it.canDelete.value = tabVM.delete(it.id)
//        }
        if (it.editing.value) {
            rowUpdate(it, newId, newName, newTotalPrice)
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
                        it.name,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                        Text(
                            it.totalPrice.toString(),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            if (reportEmployee.value == it.id) IconWindow else if (reportEmployee.value != 0) RefreshNotes else ExportNotes,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 10.dp).size(35.dp)
                                .clickable {
                                    if (reportEmployee.value != 0) {
                                        reportEmployee.value = 0
                                    } else {
                                        reportEmployee.value = it.id
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
    it: ReportGroupStringFromTables,
    newId: MutableState<String>,
    newName: MutableState<String>,
    newTotalPrice: MutableState<String>,
) {
    var number by remember { mutableStateOf(it.number.toString()) }
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
                value = newName.value,
                onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newId.value = it },
                label = { Text(if (newId.value == "") it.id.toString() else "Новый ID") },
                modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                value = newTotalPrice.value,
                onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newId.value = it },
                label = { Text(if (newId.value == "") it.id.toString() else "Новый ID") },
                readOnly = true,
                modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
        }
    }
}