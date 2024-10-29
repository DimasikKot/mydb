import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.dbConnect
import data.viewModels.MainViewModel
import windows.App
import windows.Report

fun main() = application {

    dbConnect()
    val mainVM = remember { MainViewModel() }

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
                mainVM.winVM.currentDevice = 0
            },
            title = "Report Device",
            state = windowState
        ) {
            Report(mainVM)
        }
    }
}