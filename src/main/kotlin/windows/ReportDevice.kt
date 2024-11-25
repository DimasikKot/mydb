package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.reportDevice.*
import data.viewModels.MainViewModel
import theme.MainTheme

@Composable
fun ReportDevice(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    MainTheme(mainVM.setVM.theme) {
        Scaffold {
            Card(elevation = 10.dp, modifier = modifier) {
                Column(modifier = Modifier.padding(10.dp)) {
                    ReportDeviceBar(mainVM.tabReportDeviceVM)
                    ReportDeviceList(mainVM.tabReportDeviceVM, Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}