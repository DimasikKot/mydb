package composable.app.tables.types

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.viewModels.TablesTypesViewModel

@Composable
fun appTablesTypes(
    debug: Boolean = false
) {
    val tabVM = TablesTypesViewModel()
    Column(Modifier) {
        appTablesTypesBar(tabVM, Modifier.animateContentSize(), debug)
        appTablesTypesList(tabVM, Modifier.animateContentSize(), debug)
    }
}