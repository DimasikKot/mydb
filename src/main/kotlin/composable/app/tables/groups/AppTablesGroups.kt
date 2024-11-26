package composable.app.tables.groups

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.MainViewModel
import data.viewModels.TablesGroupsViewModel

@Composable
fun appTablesGroups(
    mainVM: MainViewModel,
    tabVM: TablesGroupsViewModel
) {
    Column {
        appTablesGroupsBar(tabVM)
        appTablesGroupsList(tabVM)
    }
}