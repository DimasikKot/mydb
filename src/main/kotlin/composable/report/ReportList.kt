package composable.report

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import windows.ReportStringFromDB

@Composable
fun ReportList(allReportStrings: MutableList<ReportStringFromDB>) {
    Card(
        elevation = 25.dp,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
            items(allReportStrings) {
                Row(Modifier.padding(start = 10.dp, end = 10.dp)) {
                    Text(
                        it.stringNumber.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        it.date,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.employeeID.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.employeeName,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.groupId.toString(),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                    Text(
                        it.groupName,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                }
                Spacer(
                    Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp)
                        .border(1.dp, MaterialTheme.colors.background)
                )
            }
            item {

            }
        }
    }
}