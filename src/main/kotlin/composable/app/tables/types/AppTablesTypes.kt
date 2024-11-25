package composable.app.tables.types

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.TablesTypesViewModel

@Composable
fun appTablesTypes(tabVM: TablesTypesViewModel) {
    Column {
        appTablesTypesBar(tabVM)
        appTablesTypesList(tabVM)
    }
}