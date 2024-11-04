package composable.app.tables.types

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.TablesTypesViewModel

@Composable
fun AppTablesTypes(tabVM: TablesTypesViewModel) {
    Column {
        AppTablesTypesBar(tabVM)
        AppTablesTypesList(tabVM)
    }
}