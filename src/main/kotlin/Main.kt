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

fun main() = application {

    val mainVM = remember { MainViewModel() }
    dbConnect(mainVM)

    if (mainVM.winVM.app) {
        val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
        Window(
            onCloseRequest = ::exitApplication,
            title = "My database app",
            state = windowState
        ) {
            App(mainVM)
        }
    }

    if (mainVM.winVM.report) {
        val windowState = rememberWindowState()
        Window(
            onCloseRequest = {
                mainVM.winVM.report = false
                mainVM.winVM.reportCurrentDevice = 0
            },
            title = "Report Device",
            state = windowState
        ) {
            Report(mainVM, Modifier.padding(10.dp))
        }
    }
}