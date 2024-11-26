package composable.app.tables.types

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.MainViewModel
import data.viewModels.TablesTypesViewModel

@Composable
fun appTablesTypes(
    mainVM: MainViewModel,
    tabVM: TablesTypesViewModel
) {
    Column {
        appTablesTypesBar(mainVM, tabVM)
        appTablesTypesList(mainVM, tabVM)
    }
}