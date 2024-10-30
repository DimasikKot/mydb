package composable.app.tables.devices

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import data.viewModels.MainViewModel

@Composable
fun AppTablesDevices(mainVM: MainViewModel) {
    Column {
        AppTablesDevicesBar(mainVM)
        AppTablesDevicesList(mainVM)
    }
}