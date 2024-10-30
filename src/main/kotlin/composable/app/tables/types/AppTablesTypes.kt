package composable.app.tables.types

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import data.viewModels.MainViewModel

@Composable
fun AppTablesTypes(mainVM: MainViewModel) {
    Column {
        AppTablesTypesBar(mainVM)
        AppTablesTypesList(mainVM)
    }
}