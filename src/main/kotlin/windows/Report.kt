package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.report.*
import data.viewModels.MainViewModel
import theme.MainTheme

@Composable
fun Report(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    MainTheme(mainVM.setVM.theme) {
        Scaffold {
            Card(elevation = 10.dp, modifier = modifier) {
                Column(modifier = Modifier.padding(10.dp)) {
                    ReportBar(mainVM.tabReportVM)
                    ReportList(mainVM.tabReportVM, Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}