package composable.windows

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.db.DbString
import composable.db.DbStrings
import data.viewModels.MainViewModel
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import theme.MainTheme

@Composable
fun Report(mainVM: MainViewModel) {
    val reportDevice = ReportDevice(11, "Lenovo...", 1, "Laptop", "22.08.2023", 44000)
    val allReportStrings = remember { mutableStateListOf<ReportString>() }
//    var isCreate by remember { mutableStateOf(false) }
//    if (!isCreate) {
//        isCreate = true
//        allReportStrings.clear()
//        transaction {
//            DbStrings.selectAll().forEach {
//                allReportStrings.add(
//                    ReportString(
//                        it[DbStrings.id],
//                        it[DbStrings.deviceId],
//                        it[DbStrings.date],
//                        it[DbStrings.employeeId]
//                    )
//                )
//            }
//        }
//        allStrings.sortBy { it.id }
//    }
    MainTheme(mainVM.setVM.currentTheme) {
        Scaffold {
            Card(elevation = 5.dp, modifier = Modifier.padding(10.dp)) {
                Column(modifier = Modifier.padding(10.dp)) {
                    UiReportDevice(reportDevice)
                    UiTabListReportStrings(allReportStrings)
                    UiListReportStrings(allReportStrings)
                }
            }
        }
    }
}

@Composable
private fun UiTabListReportStrings(allReportStrings: MutableList<ReportString>) {}

@Composable
private fun UiListReportStrings(allReportStrings: MutableList<ReportString>) {
    Card(
        elevation = 25.dp
    ) {
        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
            items(allReportStrings) {
                Row {
                    Text(
                        it.stringNumber.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                    )
                    Text(
                        it.date,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.employeeID.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.employeeName,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.groupId.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.groupName,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                }
                Spacer(
                    Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
                )
            }
            item {

            }
        }
    }
}

@Composable
private fun UiReportDevice(reportDevice: ReportDevice) {
    Card(
        elevation = 25.dp
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row {
                Text(
                    "Код устройства",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.deviceId.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Название устройства",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.deviceName,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Код типа",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.typeId.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Название типа",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.typeName,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Дата приобретения",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.date,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Цена приобретения",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.price.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}

private data class ReportDevice(
    val deviceId: Int,
    val deviceName: String,
    val typeId: Int,
    val typeName: String,
    val date: String,
    val price: Int,
)

private data class ReportString(
    val stringNumber: Int,
    val date: String,
    val employeeID: Int,
    val employeeName: String,
    val groupId: Int,
    val groupName: String,
)