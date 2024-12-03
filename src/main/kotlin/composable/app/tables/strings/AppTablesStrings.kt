package composable.app.tables.strings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.TablesStringsViewModel

@Composable
fun appTablesStrings() {
    val tabVM = TablesStringsViewModel()
    Column {
        appTablesStringsBar(tabVM)
        appTablesStringsList(tabVM)
    }
}