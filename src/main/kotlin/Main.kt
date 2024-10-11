import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.viewModels.MainViewModel
import data.db.dbConnect
import composable.windows.App

fun main() = application {

    dbConnect()
    val mainVM = remember { MainViewModel() }

    if (mainVM.winVM.app) {
        val windowState = rememberWindowState()
        Window(
            onCloseRequest = { mainVM.winVM.app = false },
            title = "My database app",
            state = windowState
        ) {
            App(mainVM)
        }
    }

//    if (mainVM.windowVM.two) {
//        val windowState = rememberWindowState()
//        Window(
//            onCloseRequest = { mainVM.windowVM.two = false }, title = "Two", state = windowState
//        ) {
//            App(mainVM)
//        }
//    }

//    if (!mainVM.windowVM.main and !mainVM.windowVM.two) {
//        ::exitApplication
//    }

}