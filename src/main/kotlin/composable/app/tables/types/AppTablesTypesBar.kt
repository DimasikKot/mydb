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
import composable.ui.uiButton
import data.viewModels.TablesTypesViewModel

@Composable
fun appTablesTypesBar(
    tabVM: TablesTypesViewModel,
    debug: Boolean
) {
    Column(Modifier.animateContentSize()) {
        Row {
            uiButton(
                Icons.Default.Update,
                modifier = Modifier.size(120.dp)
            ) {
                tabVM.listUpdateView()
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                rowInfo(tabVM)
                rowBar(tabVM, debug)
            }
        }
        if (tabVM.searching) {
            rowSearch(tabVM, Modifier.padding(top = 10.dp), debug)
        }
        if (tabVM.creating) {
            rowCreate(tabVM, Modifier.padding(top = 10.dp), debug)
        }
    }
}

@Composable
private fun rowInfo(
    tabVM: TablesTypesViewModel
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.heightIn(min = 55.dp)
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
                }
            )
            Icon(
                Icons.Default.NewLabel,
                contentDescription = null,
                Modifier.align(Alignment.CenterVertically).padding(start = 10.dp).size(35.dp).clickable {
                    tabVM.creating = !tabVM.creating
                }
            )
        }
    }
}

@Composable
private fun rowBar(
    tabVM: TablesTypesViewModel,
    debug: Boolean
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.padding(top = 10.dp).heightIn(min = 55.dp)
    ) {
        Row(
            Modifier
                .background(MaterialTheme.colors.secondaryVariant).padding(10.dp)
        ) {
            if (debug) {
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
            }
            Row(Modifier.weight(1f).padding(start = if (debug) 10.dp else 0.dp)) {
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
        }
    }
}

@Composable
private fun rowSearch(
    tabVM: TablesTypesViewModel,
    modifier: Modifier = Modifier,
    debug: Boolean
) {
    Row(modifier = modifier) {
        uiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
            tabVM.listUpdateView()
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                if (debug) {
                    TextField(
                        tabVM.whereId,
                        {
                            if (it.matches(regex = Regex("^\\d*\$"))) {
                                tabVM.whereId = it
                            }
                        },
                        label = { Text(if (tabVM.whereId == "") "Искать по ID" else "Ищем по ID") },
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    )
                }
                TextField(
                    tabVM.whereName,
                    {
                        tabVM.whereName = it
                    },
                    label = { Text(if (tabVM.whereName == "") "Искать по названию" else "Ищем по названию") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                        .padding(start = if (debug) 10.dp else 0.dp)
                )
            }
        }
    }
}

@Composable
private fun rowCreate(
    tabVM: TablesTypesViewModel,
    modifier: Modifier = Modifier,
    debug: Boolean
) {
    Row(modifier = modifier) {
        var newId by remember { mutableStateOf("") }
        var newName by remember { mutableStateOf("") }
        uiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            tabVM.insert(newId, newName)
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                if (debug) {
                    TextField(
                        newId,
                        { if (it.matches(regex = Regex("^\\d*\$"))) newId = it },
                        label = { Text(if (newId == "") "Автоматический ID" else "Новый ID") },
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    )
                }
                TextField(
                    newName,
                    { newName = it },
                    label = { Text("Новое название") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                        .padding(start = if (debug) 10.dp else 0.dp)
                )
            }
        }
    }
}