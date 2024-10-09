import ui.theme.MainTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import mainWindow.Page1
import mainWindow.Page2
import mainWindow.Page3
import mainWindow.TopBar
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import viewModels.MainViewModel
import viewModels.NavigationViewModel
import java.sql.Connection
import java.sql.DriverManager

@Composable
fun App(mainVM: MainViewModel) {
    MainTheme(mainVM.settingsVM.currentTheme) {
        val navVM = remember { NavigationViewModel() }
        Scaffold(
            topBar = {
                TopBar(navVM)
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                when (navVM.currentPage) {
                    3 -> Page3(mainVM)
                    2 -> Page2(mainVM)
                    else -> Page1(mainVM)
                }
            }
        }
    }
}