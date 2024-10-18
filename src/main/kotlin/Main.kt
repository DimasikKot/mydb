import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.viewModels.MainViewModel
import data.db.dbConnect
import composable.windows.App
import composable.windows.Report

fun main() = application {

    dbConnect()
    val mainVM = remember { MainViewModel() }

    if (mainVM.winVM.app) {
        val windowState = rememberWindowState(placement = WindowPlacement.Maximized )
        Window(
            onCloseRequest = { mainVM.winVM.app = false },
            title = "My database app",
            state = windowState
        ) {
            App(mainVM)
        }
    }

    if (mainVM.winVM.report) {
        val windowState = rememberWindowState()
        Window(
            onCloseRequest = { mainVM.winVM.report = false },
            title = "Report Device",
            state = windowState
        ) {
            Report(mainVM)
        }
    }

//    if (!mainVM.windowVM.main and !mainVM.windowVM.two) {
//        ::exitApplication
//    }

}