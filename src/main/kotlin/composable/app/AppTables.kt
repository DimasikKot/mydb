package composable.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.app.tables.*
import data.viewModels.MainViewModel
import data.viewModels.NavigationViewModel

@Composable
fun AppTables(mainVM: MainViewModel) {
    val navVM = remember { NavigationViewModel() }
    Row {
        AppTablesBar(navVM)
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
                    2 -> AppTablesDevices(mainVM)
                    3 -> AppTablesStrings()
                    4 -> AppTablesEmployees()
                    5 -> AppTablesGroups()
                    else -> AppTablesTypes()
                }
            }
        }
    }
}