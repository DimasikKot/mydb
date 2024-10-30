package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.report.*
import data.*
import data.viewModels.MainViewModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import theme.MainTheme

@Composable
fun Report(mainVM: MainViewModel,
           modifier: Modifier = Modifier) {
    var reportDeviceFromDB = ReportDeviceFromDB(0, "", 0, "", "", 0)
    val allReportStrings = remember { mutableStateListOf<ReportStringFromDB>() }
    var isCreate by remember { mutableStateOf(false) }
    if (!isCreate) {
        isCreate = true

        allReportStrings.clear()
        var stringNumber by remember { mutableIntStateOf(0) }
        transaction {
            try {
                DevicesTable
                    .select(DevicesTable.date, DevicesTable.name, DevicesTable.typeId, DevicesTable.price)
                    .where(DevicesTable.id.eq(mainVM.winVM.reportCurrentDevice))
                    .forEach { device ->
                        val typeId = device[DevicesTable.typeId]
                        var typeName = ""
                        TypesTable
                            .select(TypesTable.name)
                            .where(TypesTable.id.eq(typeId))
                            .forEach {
                                typeName = it[TypesTable.name]
                            }
                        reportDeviceFromDB = ReportDeviceFromDB(
                            mainVM.winVM.reportCurrentDevice,
                            device[DevicesTable.name],
                            typeId,
                            typeName,
                            device[DevicesTable.date],
                            device[DevicesTable.price]
                        )
                    }


                StringsTable
                    .select(StringsTable.date, StringsTable.employeeId)
                    .where(StringsTable.deviceId.eq(mainVM.winVM.reportCurrentDevice))
                    .orderBy(StringsTable.date)
                    .forEach { string ->
                        stringNumber += 1
                        val employeeID = string[StringsTable.employeeId]
                        var employeeName = ""
                        var groupId = 0
                        EmployeesTable
                            .select(EmployeesTable.name, EmployeesTable.groupId)
                            .where(EmployeesTable.id.eq(employeeID))
                            .forEach {
                                employeeName = it[EmployeesTable.name]
                                groupId = it[EmployeesTable.groupId]
                            }
                        var groupName = ""
                        GroupsTable
                            .select(GroupsTable.name)
                            .where(GroupsTable.id.eq(groupId))
                            .forEach {
                                groupName = it[GroupsTable.name]
                            }
                        allReportStrings.add(
                            ReportStringFromDB(
                                stringNumber,
                                string[StringsTable.date],
                                employeeID,
                                employeeName,
                                groupId,
                                groupName
                            )
                        )
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    MainTheme(mainVM.setVM.theme) {
        Scaffold {
            Card(elevation = 10.dp, modifier = modifier) {
                Column(modifier = Modifier.padding(10.dp)) {
                    ReportDevice(reportDeviceFromDB)
                    ReportTab(allReportStrings, Modifier.padding(top = 10.dp))
                    ReportList(allReportStrings, Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}

data class ReportDeviceFromDB(
    val deviceId: Int,
    val deviceName: String,
    val typeId: Int,
    val typeName: String,
    val date: String,
    val price: Int,
)

data class ReportStringFromDB(
    val stringNumber: Int,
    val date: String,
    val employeeID: Int,
    val employeeName: String,
    var groupId: Int,
    var groupName: String,
)