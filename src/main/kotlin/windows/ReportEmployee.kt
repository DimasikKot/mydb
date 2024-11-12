package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.report.*
import composable.reportEmployee.ReportEmployeeBar
import composable.reportEmployee.ReportEmployeeList
import data.viewModels.MainViewModel
import theme.MainTheme

@Composable
fun ReportEmployee(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    MainTheme(mainVM.setVM.theme) {
        Scaffold {
            Card(elevation = 10.dp, modifier = modifier) {
                Column(modifier = Modifier.padding(10.dp)) {
                    ReportEmployeeBar(mainVM.tabReportEmployeeVM)
                    ReportEmployeeList(mainVM.tabReportEmployeeVM, Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}