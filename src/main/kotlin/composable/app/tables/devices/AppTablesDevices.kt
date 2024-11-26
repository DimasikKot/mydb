package composable.app.tables.devices

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import data.viewModels.MainViewModel
import data.viewModels.TablesDevicesViewModel

@Composable
fun appTablesDevices(
    mainVM: MainViewModel,
    tabVM: TablesDevicesViewModel
) {
    Column {
        appTablesDevicesBar(tabVM)
        appTablesDevicesList(tabVM)
    }
}