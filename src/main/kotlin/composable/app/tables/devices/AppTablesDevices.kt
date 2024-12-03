package composable.app.tables.devices

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data.viewModels.MainViewModel
import data.viewModels.TablesDevicesViewModel

@Composable
fun appTablesDevices(
    mainVM: MainViewModel,
    debug: Boolean = false
) {
    val tabVM = TablesDevicesViewModel()
    Column {
        appTablesDevicesBar(tabVM, Modifier.animateContentSize(), debug)
        appTablesDevicesList(tabVM, Modifier.animateContentSize(), mainVM, debug)
    }
}