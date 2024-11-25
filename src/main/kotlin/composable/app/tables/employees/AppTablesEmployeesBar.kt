package composable.app.tables.employees

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
import data.viewModels.TablesEmployeesViewModel
import data.viewModels.TablesGroupsViewModel

@Composable
fun appTablesEmployeesBar(tabVM: TablesEmployeesViewModel) {
    Column(Modifier.animateContentSize()) {
        Row {
            uiButton(
                Icons.Default.Update,
                modifier = Modifier.size(120.dp)
            ) {
                tabVM.listUpdate()
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                rowInfo(tabVM)
                rowBar(tabVM)
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
private fun rowInfo(tabVM: TablesEmployeesViewModel) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.heightIn(min = 55.dp)
    ) {
        Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
            Icon(
                Icons.Default.People, contentDescription = null,
                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
            )
            Text(
                "Сотрудники",
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
private fun rowBar(tabVM: TablesEmployeesViewModel) {
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
                        if (tabVM.order1 == "group_id DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                    } else {
                        if (tabVM.order1 == "group_id") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                        .clickable {
                            descending = tabVM.listOrderBy("group_id")
                        }
                )
                Text(
                    "ID групп",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
private fun rowSearch(
    tabVM: TablesEmployeesViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        uiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
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
                    tabVM.whereName,
                    { tabVM.whereName = it },
                    label = { Text(if (tabVM.whereName == "") "Искать по названию" else "Ищем по названию") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    tabVM.whereGroupId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereGroupId = it },
                    label = { Text(if (tabVM.whereGroupId == "") "Искать по ID группы" else "Ищем по ID группы") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun rowCreate(
    tabVM: TablesEmployeesViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        var newId by remember { mutableStateOf("") }
        var newName by remember { mutableStateOf("") }
        var newGroupId by remember { mutableStateOf("") }
        var newGroupIdMenu by remember { mutableStateOf(false) }
        uiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            if (newId == "") {
                tabVM.insert(newName, newGroupId.toInt())
            } else {
                tabVM.insert(newId.toInt(), newName, newGroupId.toInt())
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
                    newName,
                    { newName = it },
                    label = { Text("Новое название") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    newGroupId,
                    {
                        if (it.matches(regex = Regex("^\\d*\$"))) newGroupId = it
                        if (newGroupId.length == 1) newGroupIdMenu = true
                    },
                    label = {
                        Text("Новый ID группы")
                        DropdownMenu(
                            expanded = newGroupIdMenu,
                            onDismissRequest = { newGroupIdMenu = false },
                            offset = DpOffset(0.dp,30.dp)
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
                                    label = { Text(if (groupsVM.whereId == "") "Искать по ID группы" else "Ищем по ID группы") }
                                )
                            }
                            for (item in groupsVM.listGet()) {
                                DropdownMenuItem({
                                    newGroupId = item.id.toString()
                                    newGroupIdMenu = false
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