package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.reportEmployee.reportEmployeeBar
import composable.reportEmployee.reportEmployeeList
import data.viewModels.MainViewModel
import theme.mainTheme

@Composable
fun reportEmployee(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    mainTheme(mainVM.setVM.theme) {
        Scaffold {
            Card(elevation = 10.dp, modifier = modifier) {
                Column(modifier = Modifier.padding(10.dp)) {
                    reportEmployeeBar(mainVM.tabReportEmployeeVM, mainVM = mainVM)
                    reportEmployeeList(mainVM.tabReportEmployeeVM, Modifier.padding(top = 10.dp), mainVM)
                }
            }
        }
    }
}