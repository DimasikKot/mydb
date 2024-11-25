package composable.app.tables.employees

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data.viewModels.TablesEmployeesViewModel

@Composable
fun appTablesEmployees(tabVM: TablesEmployeesViewModel) {
    Column(Modifier) {
        appTablesEmployeesBar(tabVM)
        appTablesEmployeesList(tabVM)
    }
}