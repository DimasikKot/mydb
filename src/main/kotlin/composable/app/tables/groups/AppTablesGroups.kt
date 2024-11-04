package composable.app.tables.groups

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.TablesGroupsViewModel

@Composable
fun AppTablesGroups(tabVM: TablesGroupsViewModel) {
    Column {
        AppTablesGroupsBar(tabVM)
        AppTablesGroupsList(tabVM)
    }
}