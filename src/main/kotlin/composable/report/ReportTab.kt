package composable.report

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import windows.ReportStringFromDB

@Composable
fun ReportTab(
    listReportStrings: MutableList<ReportStringFromDB>,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(10.dp)) {

        }
    }
}