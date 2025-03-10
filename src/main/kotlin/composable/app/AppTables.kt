package composable.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.app.tables.*
import composable.app.tables.devices.appTablesDevices
import composable.app.tables.employees.appTablesEmployees
import composable.app.tables.groups.appTablesGroups
import composable.app.tables.strings.appTablesStrings
import composable.app.tables.types.appTablesTypes
import data.viewModels.MainViewModel

@Composable
fun appTables(mainVM: MainViewModel, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        appTablesBar(mainVM.navVM, modifier = Modifier.width(200.dp), debug = mainVM.setVM.debug)
        currentTable(mainVM, Modifier.padding(start = 10.dp).fillMaxSize())
    }
}

@Composable
private fun currentTable(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier
) {
    Card(elevation = 10.dp, modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(10.dp)) {
            Crossfade(mainVM.navVM.appTablesBarCurrentPage) { currentPage ->
                when (currentPage) {
                    2 -> appTablesDevices(mainVM, mainVM.setVM.debug)
                    3 -> appTablesStrings()
                    4 -> appTablesEmployees(mainVM, mainVM.setVM.debug)
                    5 -> appTablesGroups(mainVM, mainVM.setVM.debug)
                    else -> appTablesTypes(mainVM.setVM.debug)
                }
            }
        }
    }
}