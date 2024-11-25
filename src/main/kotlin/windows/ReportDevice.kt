package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.reportDevice.*
import data.viewModels.MainViewModel
import theme.mainTheme

@Composable
fun reportDevice(
    mainVM: MainViewModel,
    reportEmployee: MutableIntState,
    reportGroup: MutableIntState,
    modifier: Modifier = Modifier,
) {
    mainTheme(mainVM.setVM.theme) {
        Scaffold {
            Card(elevation = 10.dp, modifier = modifier) {
                Column(modifier = Modifier.padding(10.dp)) {
                    reportDeviceBar(mainVM.tabReportDeviceVM)
                    reportDeviceList(mainVM.tabReportDeviceVM, reportEmployee, reportGroup, Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}