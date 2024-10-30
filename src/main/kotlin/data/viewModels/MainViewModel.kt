package data.viewModels

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val url = "jdbc:postgresql://localhost:5432/mydb"
    val driver = "org.postgresql.Driver"
    val user = "postgres"
    val password = "Dima2004"
    val setVM = SettingsViewModel()
    val winVM = WindowsViewModel()
    val navVM = NavigationViewModel()
    val tabVM = TablesViewModel()
}