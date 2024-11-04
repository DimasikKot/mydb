package composable.app.tables.strings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.TablesStringsViewModel

@Composable
fun AppTablesStrings(tabVM: TablesStringsViewModel) {
    Column {
        AppTablesStringsBar(tabVM)
        AppTablesStringsList(tabVM)
    }
}