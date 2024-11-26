package composable.app.tables.strings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.MainViewModel
import data.viewModels.TablesStringsViewModel

@Composable
fun appTablesStrings(
    mainVM: MainViewModel,
    tabVM: TablesStringsViewModel
) {
    Column {
        appTablesStringsBar(tabVM)
        appTablesStringsList(tabVM)
    }
}