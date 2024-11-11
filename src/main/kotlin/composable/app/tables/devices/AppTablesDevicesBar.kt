package composable.app.tables.devices

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
import composable.ui.UiButton
import data.DateTransformation
import data.viewModels.TablesDevicesViewModel
import data.viewModels.TablesTypesViewModel

@Composable
fun AppTablesDevicesBar(tabVM: TablesDevicesViewModel) {
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
private fun RowInfo(tabVM: TablesDevicesViewModel) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.heightIn(min = 55.dp)
    ) {
        Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
            Icon(
                Icons.Default.Devices, contentDescription = null,
                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
            )
            Text(
                "Устройства",
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
private fun RowBar(tabVM: TablesDevicesViewModel) {
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
                        if (tabVM.order1 == "name DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                    } else {
                        if (tabVM.order1 == "name") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        .clickable {
                            descending = tabVM.listOrderBy("name")
                        }
                )
                Text(
                    "Названия",
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
                        if (tabVM.order1 == "price DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                    } else {
                        if (tabVM.order1 == "price") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        .clickable {
                            descending = tabVM.listOrderBy("price")
                        }
                )
                Text(
                    "Цены",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row(Modifier.weight(1f).padding(start = 10.dp)) {
                var descending by remember { mutableStateOf(false) }
                Icon(
                    if (descending) {
                        if (tabVM.order1 == "type_id DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                    } else {
                        if (tabVM.order1 == "type_id") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        .clickable {
                            descending = tabVM.listOrderBy("type_id")
                        }
                )
                Text(
                    "ID типов",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
private fun RowSearch(
    tabVM: TablesDevicesViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        UiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
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
                    value = tabVM.whereName,
                    onValueChange = { tabVM.whereName = it },
                    label = { Text(if (tabVM.whereName == "") "Искать по названию" else "Ищем по названию") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
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
                    value = tabVM.wherePrice,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.wherePrice = it },
                    label = { Text(if (tabVM.wherePrice == "") "Искать по цене" else "Ищем по цене") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = tabVM.whereTypeId,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereTypeId = it },
                    label = { Text(if (tabVM.whereTypeId == "") "Искать по ID типа" else "Ищем по ID типа") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun RowCreate(
    tabVM: TablesDevicesViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        var newId by remember { mutableStateOf("") }
        var newName by remember { mutableStateOf("") }
        var newDate by remember { mutableStateOf("") }
        var newPrice by remember { mutableStateOf("") }
        var newTypeId by remember { mutableStateOf("") }
        var newTypeIdMenu by remember { mutableStateOf(false) }
        UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            if (newId == "") {
                tabVM.insert(newName, newDate, newPrice.toInt(), newTypeId.toInt())
            } else {
                tabVM.insert(newId.toInt(), newName, newDate, newPrice.toInt(), newTypeId.toInt())
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
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Новое название") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
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
                    value = newPrice,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newPrice = it },
                    label = { Text("Новая цена") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    value = newTypeId,
                    onValueChange = {
                        if (it.matches(regex = Regex("^\\d*\$"))) newTypeId = it
                        if (newTypeId.length == 1) newTypeIdMenu = true
                    },
                    label = {
                        Text("Новый ID типа")
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
                                            typesVM.listUpdate()
                                        }
                                    },
                                    label = { Text(if (typesVM.whereId == "") "Искать по ID типа" else "Ищем по ID типа") }
                                )
                            }
                            for (item in typesVM.listGet()) {
                                DropdownMenuItem({
                                    newTypeId = item.id.toString()
                                    newTypeIdMenu = false
                                }) {
                                    Text("${item.id}: ${item.name}")
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}