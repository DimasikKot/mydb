import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import viewModels.MainViewModel

fun main() = application {

    val mainVM = remember { MainViewModel() }

    if (mainVM.windowVM.main) {
        val windowState = rememberWindowState()
        Window(onCloseRequest = { mainVM.windowVM.main = false }, title = "Main", state = windowState) {
            App(mainVM)
        }
    }

    if (mainVM.windowVM.two) {
        val windowState = rememberWindowState()
        Window(
            onCloseRequest = { mainVM.windowVM.two = false }, title = "Two", state = windowState
        ) {
            App(mainVM)
        }
    }

//    if (!mainVM.windowVM.main and !mainVM.windowVM.two) {
//        ::exitApplication
//    }

}