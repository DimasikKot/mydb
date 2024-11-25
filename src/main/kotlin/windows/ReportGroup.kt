package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.reportGroup.ReportGroupBar
import composable.reportGroup.ReportGroupList
import data.viewModels.MainViewModel
import theme.MainTheme

@Composable
fun ReportGroup(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    MainTheme(mainVM.setVM.theme) {
        Scaffold {
            Card(elevation = 10.dp, modifier = modifier) {
                Column(modifier = Modifier.padding(10.dp)) {
                    ReportGroupBar(mainVM.tabReportGroupVM)
                    ReportGroupList(mainVM.tabReportGroupVM, Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}