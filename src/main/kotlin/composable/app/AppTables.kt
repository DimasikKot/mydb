package composable.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.app.tables.*
import composable.app.tables.devices.AppTablesDevices
import composable.app.tables.employees.AppTablesEmployees
import composable.app.tables.groups.AppTablesGroups
import composable.app.tables.strings.AppTablesStrings
import composable.app.tables.types.AppTablesTypes
import data.viewModels.MainViewModel

@Composable
fun AppTables(mainVM: MainViewModel, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        AppTablesBar(mainVM.navVM, modifier = Modifier.width(200.dp))
        CurrentTable(mainVM, Modifier.padding(start = 10.dp).fillMaxSize())
    }
}

@Composable
private fun CurrentTable(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier
) {
    Card(elevation = 10.dp, modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(10.dp)) {
            Crossfade(mainVM.navVM.appTablesBarCurrentPage) { currentPage ->
                when (currentPage) {
                    2 -> AppTablesDevices(mainVM.tabDevicesVM)
                    3 -> AppTablesStrings(mainVM.tabStringsVM)
                    4 -> AppTablesEmployees(mainVM.tabEmployeesVM)
                    5 -> AppTablesGroups(mainVM.tabGroupsVM)
                    else -> AppTablesTypes(mainVM.tabTypesVM)
                }
            }
        }
    }
}