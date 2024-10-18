package composable.pages.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.viewModels.MainViewModel
import data.viewModels.NavigationViewModel

@Composable
fun AppTables(mainVM: MainViewModel) {
    val navVM = remember { NavigationViewModel() }
    Row {
        TablesBar(navVM)
        CurrentTable(navVM, mainVM)
    }
}

@Composable
private fun CurrentTable(
    navVM: NavigationViewModel,
    mainVM: MainViewModel
) {
    Card(elevation = 5.dp, modifier = Modifier.fillMaxSize().padding(top = 10.dp, bottom = 10.dp, end = 10.dp)) {
        Box(modifier = Modifier.padding(10.dp)) {
            Crossfade(navVM.currentPage) { currentPage ->
                when (currentPage) {
                    2 -> UiDevices(mainVM)
                    3 -> UiStrings()
                    4 -> UiEmployees()
                    5 -> UiGroups()
                    else -> UiTypes()
                }
            }
        }
    }
}