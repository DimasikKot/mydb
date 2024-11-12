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
import windows.App
import windows.Report
import windows.ReportEmployee

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
            App(mainVM)
        }
    }

    if (mainVM.tabDevicesVM.report != 0) {
        mainVM.tabReportVM.report = mainVM.tabDevicesVM.report
        val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
        Window(
            onCloseRequest = {
                mainVM.tabDevicesVM.report = 0
                mainVM.tabReportVM.report = 0
            },
            title = "Учёт устройства",
            state = windowState
        ) {
            Report(mainVM, Modifier.padding(10.dp))
        }
    }

    if (mainVM.tabEmployeesVM.report != 0) {
        mainVM.tabReportEmployeeVM.report = mainVM.tabEmployeesVM.report
        val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
        Window(
            onCloseRequest = {
                mainVM.tabEmployeesVM.report = 0
                mainVM.tabReportEmployeeVM.report = 0
            },
            title = "Учёт устройств сотрудника",
            state = windowState
        ) {
            ReportEmployee(mainVM, Modifier.padding(10.dp))
        }
    }
}