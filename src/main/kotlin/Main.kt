import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.dbConnect
import data.viewModels.MainViewModel
import windows.app
import windows.reportDevice
import windows.reportEmployee
import windows.reportGroup

fun main() = application {

    val mainVM = remember { MainViewModel() }
    dbConnect(mainVM)

    if (mainVM.winVM.app) {
        val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
        Window(
            onCloseRequest = ::exitApplication,
            title = "Учёт устройств",
            state = windowState
        ) {
            app(mainVM)
        }
    }

    if (mainVM.winVM.reportDevice != 0) {
        mainVM.tabReportDeviceVM.report = mainVM.winVM.reportDevice
        val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
        Window(
            onCloseRequest = {
                mainVM.winVM.reportDevice = 0
                mainVM.tabReportDeviceVM.report = 0
            },
            title = "Учёт устройства",
            state = windowState
        ) {
            reportDevice(mainVM, mainVM.tabEmployeesVM.report, mainVM.tabGroupsVM.report, Modifier.padding(10.dp))
        }
    }

    if (mainVM.tabEmployeesVM.report.value != 0) {
        mainVM.tabReportEmployeeVM.report = mainVM.tabEmployeesVM.report.value
        val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
        Window(
            onCloseRequest = {
                mainVM.tabEmployeesVM.report.value = 0
                mainVM.tabReportEmployeeVM.report = 0
            },
            title = "Учёт устройств сотрудника",
            state = windowState
        ) {
            reportEmployee(mainVM, mainVM.tabGroupsVM.report, Modifier.padding(10.dp))
        }
    }

    if (mainVM.tabGroupsVM.report.value != 0) {
        mainVM.tabReportGroupVM.report = mainVM.tabGroupsVM.report.value
        val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
        Window(
            onCloseRequest = {
                mainVM.tabGroupsVM.report.value = 0
                mainVM.tabReportGroupVM.report = 0
            },
            title = "Учёт сотрудников группы",
            state = windowState
        ) {
            reportGroup(mainVM, mainVM.tabEmployeesVM.report, Modifier.padding(10.dp))
        }
    }
}