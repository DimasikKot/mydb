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
import androidx.compose.ui.unit.dp
import composable.ui.UiButton
import data.DateTransformation
import data.viewModels.TablesStringsViewModel

@Composable
fun AppTablesStringsBar(tabVM: TablesStringsViewModel) {
    tabVM.listUpdate()
    Column(Modifier.animateContentSize()) {
        Row {
            UiButton(
                Icons.Default.Update,
                modifier = Modifier.size(120.dp)
            ) {
                tabVM.listUpdate()
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                RowInfo(tabVM)
                RowBar(tabVM)
            }
        }
        if (tabVM.searching) {
            RowSearch(tabVM, Modifier.padding(top = 10.dp))
        }
        if (tabVM.creating) {
            RowCreate(tabVM, Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
private fun RowInfo(tabVM: TablesStringsViewModel) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.heightIn(min = 55.dp)
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
private fun RowBar(tabVM: TablesStringsViewModel) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.padding(top = 10.dp).heightIn(min = 55.dp)
    ) {
        Row(
            Modifier
                .background(MaterialTheme.colors.secondaryVariant).padding(10.dp)
        ) {
            Row(Modifier.weight(1f)) {
                var descending by remember { mutableStateOf(false) }
                Icon(
                    if (descending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
                    if (descending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
                    if (descending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
                    if (descending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
private fun RowSearch(
    tabVM: TablesStringsViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        UiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
            tabVM.listUpdate()
        }
        Card(elevation = 25.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                TextField(
                    tabVM.whereId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereId = it },
                    label = { Text(if (tabVM.whereId == "") "Искать по ID" else "Ищем по ID") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                TextField(
                    singleLine = true,
                    value = tabVM.whereDate,
                    onValueChange = {
                        if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) tabVM.whereDate = it
                    },
                    label = { Text(if (tabVM.whereDate == "") "Искать по дате" else "Ищем по дате") },
                    visualTransformation = DateTransformation(),
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    tabVM.whereDeviceId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereDeviceId = it },
                    label = { Text(if (tabVM.whereDeviceId == "") "Искать по ID устройства" else "Ищем по ID устройства") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    tabVM.whereEmployeeId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereEmployeeId = it },
                    label = { Text(if (tabVM.whereEmployeeId == "") "Искать по ID сотрудника" else "Ищем по ID сотрудника") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun RowCreate(
    tabVM: TablesStringsViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        var newId by remember { mutableStateOf("") }
        var newDate by remember { mutableStateOf("") }
        var newDeviceId by remember { mutableStateOf("") }
        var newEmployeeId by remember { mutableStateOf("") }
        UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            if (newId == "") {
                tabVM.insert(newDate, newDeviceId.toInt(), newEmployeeId.toInt())
            } else {
                tabVM.insert(newId.toInt(), newDate, newDeviceId.toInt(), newEmployeeId.toInt())
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
                    singleLine = true,
                    value = newDate,
                    onValueChange = {
                        if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) newDate = it
                    },
                    label = { Text("Новая дата") },
                    visualTransformation = DateTransformation(),
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    newDeviceId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) newDeviceId = it },
                    label = { Text("Новый ID устройства") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    newEmployeeId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) newEmployeeId = it },
                    label = { Text("Новый ID сотрудника") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}