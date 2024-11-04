package composable.app.tables.employees

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data.viewModels.TablesEmployeesViewModel

@Composable
fun AppTablesEmployees(tabVM: TablesEmployeesViewModel) {
    Column(Modifier) {
        AppTablesEmployeesBar(tabVM)
        AppTablesEmployeesList(tabVM)
    }
}