package composable.app.tables.devices

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import composable.ui.UiButton
import data.DeviceFromTable
import data.viewModels.MainViewModel
import icons.ExportNotes
import icons.IconWindow

@Composable
fun AppTablesDevicesList(mainVM: MainViewModel) {
    DeviceRowSearch(mainVM, Modifier.padding(top = 10.dp))
    LazyColumn(modifier = Modifier.padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            DeviceRowCreate(mainVM)
        }
        items(mainVM.tabVM.devicesGetList()) {
            DeviceRow(mainVM, it)
        }
    }
}

@Composable
private fun DeviceRowSearch(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        UiButton(Icons.Default.Search, modifier = Modifier.height(80.dp).width(120.dp)) {
            mainVM.tabVM.devicesUpdate()
        }
        Card(elevation = 25.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
            Row(Modifier.padding(10.dp)) {
                TextField(
                    mainVM.tabVM.devicesWhereId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) mainVM.tabVM.devicesWhereId = it },
                    label = { Text(if (mainVM.tabVM.devicesWhereId == "") "Искать по ID" else "Ищем по ID") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                TextField(
                    mainVM.tabVM.devicesWhereName,
                    { mainVM.tabVM.devicesWhereName = it },
                    label = { Text(if (mainVM.tabVM.devicesWhereName == "") "Искать по названию" else "Ищем по названию") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    singleLine = true,
                    value = mainVM.tabVM.devicesWhereDate,
                    onValueChange = {
                        if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) mainVM.tabVM.devicesWhereDate = it
                    },
                    label = { Text(if (mainVM.tabVM.devicesWhereDate == "") "Искать по дате" else "Ищем по дате") },
                    visualTransformation = DateTransformation(),
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    mainVM.tabVM.devicesWherePrice,
                    { if (it.matches(regex = Regex("^\\d*\$"))) mainVM.tabVM.devicesWherePrice = it },
                    label = { Text(if (mainVM.tabVM.devicesWherePrice == "") "Искать по цене" else "Ищем по цене") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    mainVM.tabVM.devicesWhereTypeId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) mainVM.tabVM.devicesWhereTypeId = it },
                    label = { Text(if (mainVM.tabVM.devicesWhereTypeId == "") "Искать по ID типа" else "Ищем по ID типа") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun DeviceRowCreate(mainVM: MainViewModel) {
    Row {
        var newId by remember { mutableStateOf("") }
        var newName by remember { mutableStateOf("") }
        var newDate by remember { mutableStateOf("") }
        var newPrice by remember { mutableStateOf("") }
        var newTypeId by remember { mutableStateOf("") }
        UiButton(Icons.Default.NewLabel, modifier = Modifier.height(80.dp).width(120.dp)) {
            if (newId == "") {
                mainVM.tabVM.deviceInsert(newName, newDate, newPrice.toInt(), newTypeId.toInt())
            } else {
                mainVM.tabVM.deviceInsert(newId.toInt(), newName, newDate, newPrice.toInt(), newTypeId.toInt())
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
                    newPrice,
                    { if (it.matches(regex = Regex("^\\d*\$"))) newPrice = it },
                    label = { Text("Новая цена") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
                TextField(
                    newTypeId,
                    { if (it.matches(regex = Regex("^\\d*\$"))) newTypeId = it },
                    label = { Text("Новый ID типа") },
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun DeviceRow(
    mainVM: MainViewModel,
    device: DeviceFromTable,
) {
    Row {
        val newId = mutableStateOf(device.id.toString())
        val newName = mutableStateOf(device.name)
        val newDate = mutableStateOf(device.date)
        val newPrice = mutableStateOf(device.price.toString())
        val newTypeId = mutableStateOf(device.typeId.toString())
        UiButton(
            if (!device.canUpdate.value) Icons.Default.EditOff else if (device.editing.value) Icons.Default.EditNote else Icons.Default.ModeEdit,
            modifier = Modifier.size(55.dp)
        ) {
            if (device.editing.value) {
                device.canUpdate.value = mainVM.tabVM.deviceUpdate(
                    device.id,
                    newId.value.toInt(),
                    newName.value,
                    newDate.value,
                    newPrice.value.toInt(),
                    newTypeId.value.toInt()
                )
                if (device.canUpdate.value) {
                    device.editing.value = false
                }
            } else {
                device.editing.value = true
            }
        }
        UiButton(
            if (device.canDelete.value) Icons.Default.Delete else Icons.Default.DeleteForever,
            modifier = Modifier
                .padding(start = 10.dp).size(55.dp)
        ) {
            device.canDelete.value = mainVM.tabVM.deviceDelete(device.id)
        }
        if (device.editing.value) {
            DeviceRowUpdate(device, newId, newName, newDate, newPrice, newTypeId)
        } else {
            Card(elevation = 10.dp, modifier = Modifier.padding(start = 10.dp)) {
                Row(Modifier.heightIn(min = 55.dp).padding(10.dp)) {
                    Text(
                        device.id.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    )
                    Text(
                        device.name,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        device.date,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        device.price.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Row(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                        Text(
                            device.typeId.toString(),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            if (mainVM.winVM.reportCurrentDevice == device.id && mainVM.winVM.report) IconWindow else ExportNotes,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 10.dp).size(35.dp)
                                .clickable {
                                    if (mainVM.winVM.report) {
                                        mainVM.winVM.report = false
                                        mainVM.winVM.reportCurrentDevice = 0
                                    } else {
                                        mainVM.winVM.report = true
                                        mainVM.winVM.reportCurrentDevice = device.id
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
private fun DeviceRowUpdate(
    device: DeviceFromTable,
    newId: MutableState<String>,
    newName: MutableState<String>,
    newDate: MutableState<String>,
    newPrice: MutableState<String>,
    newTypeId: MutableState<String>,
) {
    Card(elevation = 10.dp, modifier = Modifier.heightIn(min = 80.dp).padding(start = 10.dp)) {
        Row(Modifier.padding(10.dp)) {
            TextField(
                newId.value,
                { if (it.matches(regex = Regex("^\\d*\$"))) newId.value = it },
                label = { Text(if (newId.value == "") device.id.toString() else "Новый ID") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
            )
            TextField(
                newName.value,
                { newName.value = it },
                label = { Text(if (newName.value == "") device.name else "Новое название") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                singleLine = true,
                value = newDate.value,
                onValueChange = { if (it.length <= 8 && it.matches(regex = Regex("^\\d*\$"))) newDate.value = it },
                label = { Text(if (newDate.value == "") device.date else "Новая дата") },
                visualTransformation = DateTransformation(),
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                newPrice.value,
                { if (it.matches(regex = Regex("^\\d*\$"))) newPrice.value = it },
                label = { Text(if (newPrice.value == "") device.price.toString() else "Новая цена") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
            TextField(
                newTypeId.value,
                { if (it.matches(regex = Regex("^\\d*\$"))) newTypeId.value = it },
                label = { Text(if (newTypeId.value == "") device.typeId.toString() else "Новый ID типа") },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
            )
        }
    }
}

class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

fun dateFilter(text: AnnotatedString): TransformedText {

    val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 2 == 1 && i < 4) out += "/"
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset + 1
            if (offset <= 8) return offset + 2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}