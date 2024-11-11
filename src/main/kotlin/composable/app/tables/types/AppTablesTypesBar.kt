package composable.app.tables.types

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
import data.viewModels.TablesTypesViewModel

@Composable
fun AppTablesTypesBar(tabVM: TablesTypesViewModel) {
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
private fun RowInfo(tabVM: TablesTypesViewModel) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.heightIn(min = 55.dp).animateContentSize()
    ) {
        Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
            Icon(
                Icons.Default.Style, contentDescription = null,
                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
            )
            Text(
                "Типы устройств",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterVertically).padding(start = 10.dp)
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
private fun RowBar(tabVM: TablesTypesViewModel) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.padding(top = 10.dp).heightIn(min = 55.dp).animateContentSize()
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
                            descending = tabVM.listOrderBy("name")
                        }
                )
                Text(
                    "Названия",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
private fun RowSearch(
    tabVM: TablesTypesViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.animateContentSize()) {
        UiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
            tabVM.listUpdate()
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
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
            }
        }
    }
}

@Composable
private fun RowCreate(
    tabVM: TablesTypesViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.animateContentSize()) {
        var newId by remember { mutableStateOf("") }
        var newName by remember { mutableStateOf("") }
        UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            if (newId == "") {
                tabVM.insert(newName)
            } else {
                tabVM.insert(newId.toInt(), newName)
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
            }
        }
    }
}