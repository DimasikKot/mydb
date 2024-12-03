package composable.app.tables.types

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.viewModels.MainViewModel
import data.viewModels.TablesTypesViewModel

@Composable
fun appTablesTypes(
    mainVM: MainViewModel,
    tabVM: TablesTypesViewModel
) {
    Column(Modifier) {
        appTablesTypesBar(mainVM, tabVM, Modifier.animateContentSize())
        appTablesTypesList(mainVM, tabVM, Modifier.animateContentSize())
    }
}