package composable.app.tables.devices

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.MainViewModel
import data.viewModels.TablesDevicesViewModel

@Composable
fun appTablesDevices(
    mainVM: MainViewModel,
    debug: Boolean = false
) {
    val tabVM = TablesDevicesViewModel()
    Column {
        appTablesDevicesBar(tabVM, debug)
        appTablesDevicesList(tabVM, mainVM, debug)
    }
}