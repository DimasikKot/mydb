package composable.app.tables.employees

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data.viewModels.MainViewModel
import data.viewModels.TablesEmployeesViewModel

@Composable
fun appTablesEmployees(
    mainVM: MainViewModel,
    debug: Boolean = false
) {
    println(debug)
    val tabVM = TablesEmployeesViewModel()
    Column(Modifier) {
        appTablesEmployeesBar(tabVM)
        appTablesEmployeesList(tabVM, mainVM)
    }
}