package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.reportGroup.reportGroupBar
import composable.reportGroup.reportGroupList
import data.viewModels.MainViewModel
import theme.mainTheme

@Composable
fun reportGroup(
    mainVM: MainViewModel,
    reportEmployee: MutableIntState,
    modifier: Modifier = Modifier,
) {
    mainTheme(mainVM.setVM.theme) {
        Scaffold {
            Card(elevation = 10.dp, modifier = modifier) {
                Column(modifier = Modifier.padding(10.dp)) {
                    reportGroupBar(mainVM.tabReportGroupVM)
                    reportGroupList(mainVM.tabReportGroupVM, reportEmployee, Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}