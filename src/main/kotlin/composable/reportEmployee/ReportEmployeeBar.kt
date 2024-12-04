package composable.reportEmployee

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
import composable.ui.uiButton
import data.DateTransformation
import data.viewModels.*
import icons.ExportNotes
import icons.IconWindow
import icons.RefreshNotes

@Composable
fun reportEmployeeBar(
    tabVM: TablesReportEmployeeViewModel,
    modifier: Modifier = Modifier,
    mainVM: MainViewModel
) {
    tabVM.listUpdate()
    Column(modifier.animateContentSize()) {
        Row {
            uiButton(
                Icons.Default.Update,
                modifier = Modifier.height(55.dp).width(120.dp)
            ) {
                tabVM.listUpdate()
            }
            rowInfo(Modifier.padding(start = 10.dp))
        }
        rowHead(tabVM.headGet(), Modifier.padding(top = 10.dp), mainVM)
        rowBar(tabVM, Modifier.padding(top = 10.dp))
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
//    tabVM: TablesReportEmployeeViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
            Icon(
                Icons.Default.People, contentDescription = null,
                modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
            )
            Text(
                "Учёт устройств сотрудника",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
//            Spacer(Modifier.weight(1f))
//            Icon(
//                Icons.Default.Search,
//                contentDescription = null,
//                Modifier.align(Alignment.CenterVertically).padding(start = 10.dp).size(35.dp).clickable {
//                    tabVM.searching = !tabVM.searching
//                }
//            )
//            Icon(
//                Icons.Default.NewLabel,
//                contentDescription = null,
//                Modifier.align(Alignment.CenterVertically).padding(start = 10.dp).size(35.dp).clickable {
//                    tabVM.creating = !tabVM.creating
//                }
//            )
        }
    }
}

@Composable
private fun rowHead(
    it: ReportEmployeeFromTables,
    modifier: Modifier = Modifier,
    mainVM: MainViewModel
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row {
                Text(
                    "Код сотрудника",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    it.id.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Название сотрудника",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    it.name,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Код группы",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    it.groupId.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Название группы",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                    Text(
                        it.groupName,
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = if (mainVM.winVM.reportGroup == it.groupId) IconWindow else if (mainVM.winVM.reportGroup != 0) RefreshNotes else ExportNotes,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 10.dp).size(35.dp).clickable {
                            if (mainVM.winVM.reportGroup != 0) {
                                mainVM.winVM.reportGroup = 0
                            } else {
                                mainVM.winVM.reportGroup = it.groupId
                            }
                        }
                    )
                }
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Цена всего имущества",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    it.totalPrice.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun rowBar(
    tabVM: TablesReportEmployeeViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
//        Card(
//            elevation = 10.dp,
//            modifier = Modifier.size(55.dp).clickable {
//                tabVM.searching = !tabVM.searching
//            }) {
//            Icon(
//                Icons.Default.Search,
//                contentDescription = null,
//                Modifier.size(35.dp).align(Alignment.CenterVertically).background(MaterialTheme.colors.secondaryVariant)
//            )
//        }
//        Card(
//            elevation = 10.dp,
//            modifier = Modifier.padding(start = 10.dp).size(55.dp)
//                .clickable {
//                    tabVM.creating = !tabVM.creating
//                }) {
//            Icon(
//                Icons.Default.NewLabel,
//                contentDescription = null,
//                Modifier.size(35.dp).align(Alignment.CenterVertically).background(MaterialTheme.colors.secondaryVariant)
//            )
//        }
        Card(
            elevation = 10.dp,
            modifier = Modifier
        ) {
            Row(
                Modifier
                    .background(MaterialTheme.colors.secondaryVariant).padding(10.dp)
            ) {
                Text(
                    "№",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(start = 10.dp).width(55.dp).align(Alignment.CenterVertically)
                )
                Row(Modifier.weight(1f).padding(start = 10.dp)) {
                    var descending by remember { mutableStateOf(false) }
                    Icon(
                        if (descending) {
                            if (tabVM.order1 == "date_give DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                        } else {
                            if (tabVM.order1 == "date_give") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                            .clickable {
                                descending = tabVM.listOrderBy("date_give")
                            }
                    )
                    Text(
                        "Дата получения",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row(Modifier.weight(0.5f).padding(start = 10.dp)) {
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
                Row(Modifier.weight(1.5f).padding(start = 10.dp)) {
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
                        "Название",
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
                        "Дата",
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
                        "Стоимость",
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
                        "ID типа",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row(Modifier.weight(1f).padding(start = 10.dp)) {
                    var descending by remember { mutableStateOf(false) }
                    Icon(
                        if (descending) {
                            if (tabVM.order1 == "type_name DESC") Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardArrowUp
                        } else {
                            if (tabVM.order1 == "type_name") Icons.Default.KeyboardDoubleArrowDown else Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                            .clickable {
                                descending = tabVM.listOrderBy("type_name")
                            }
                    )
                    Text(
                        "Название типа",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Composable
private fun rowSearch(
    tabVM: TablesReportEmployeeViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        uiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
            tabVM.listUpdate()
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                TextField(
                    value = tabVM.whereNumber,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereNumber = it },
                    label = { Text("№") },
                    modifier = Modifier.width(55.dp).align(Alignment.CenterVertically)
                )
                TextField(
                    value = tabVM.whereId,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) tabVM.whereId = it },
                    label = { Text(if (tabVM.whereId == "") "Искать по ID" else "Ищем по ID") },
                    modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
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
                    modifier = Modifier.weight(0.7f).align(Alignment.CenterVertically).padding(start = 10.dp)
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
                    label = { Text(if (tabVM.whereGroupId == "") "Искать ID группы" else "Ищем по ID группы") },
                    modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
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
private fun rowCreate(
    tabVM: TablesReportEmployeeViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        var newNumber by remember { mutableStateOf("") }
        var newId by remember { mutableStateOf("") }
        var newDate by remember { mutableStateOf("") }
        var newEmployeeId by remember { mutableStateOf("") }
        var newEmployeeIdMenu by remember { mutableStateOf(false) }
        var newEmployeeName by remember { mutableStateOf("") }
        var newGroupId by remember { mutableStateOf("") }
        var newGroupIdMenu by remember { mutableStateOf(false) }
        var newGroupName by remember { mutableStateOf("") }
        uiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            tabVM.insert(newId, newDate, newEmployeeId.toInt())
        }
        Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                TextField(
                    value = newNumber,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newNumber = it },
                    label = { Text("№") },
                    readOnly = true,
                    modifier = Modifier.width(55.dp).align(Alignment.CenterVertically)
                )
                TextField(
                    value = newId,
                    onValueChange = { if (it.matches(regex = Regex("^\\d*\$"))) newId = it },
                    label = { Text(if (newId == "") "Авто ID" else "Новый ID") },
                    modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
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
                                        }
                                    },
                                    label = { Text(if (employeesVM.whereId == "") "Искать по ID сотрудника" else "Ищем по ID сотрудника") }
                                )
                            }
                            for (item in employeesVM.list) {
                                DropdownMenuItem({
                                    newEmployeeId = item.id.toString()
                                    newEmployeeName = item.name
                                    newGroupId = item.groupId.toString()
                                    val groupVM by mutableStateOf(TablesGroupsViewModel())
                                    for (itemGroup in groupVM.list) {
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
                    modifier = Modifier.weight(0.7f).align(Alignment.CenterVertically).padding(start = 10.dp)
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
                                        }
                                    },
                                    label = { Text(if (groupVM.whereId == "") "Искать по ID группы" else "Ищем по ID группы") }
                                )
                            }
                            for (item in groupVM.list) {
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
                    modifier = Modifier.weight(0.5f).align(Alignment.CenterVertically).padding(start = 10.dp)
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