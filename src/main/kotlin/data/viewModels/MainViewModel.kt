package data.viewModels

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val url = "jdbc:postgresql://localhost:5432/postgres"
    val driver = "org.postgresql.Driver"
    val user = "postgres"
    val password = "Dima2004"
    val setVM = SettingsViewModel()
    val winVM = WindowsViewModel()
    val navVM = NavigationViewModel()
    val tabStringsVM = TablesStringsViewModel()
    val tabEmployeesVM = TablesEmployeesViewModel()
    val tabGroupsVM = TablesGroupsViewModel()
    val tabReportDeviceVM = TablesReportDeviceViewModel()
    val tabReportEmployeeVM = TablesReportEmployeeViewModel()
    val tabReportGroupVM = TablesReportGroupViewModel()
}