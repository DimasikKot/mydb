package composable.report

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
import data.viewModels.ReportStringFromTables
import data.viewModels.TablesReportViewModel

@Composable
fun ReportList(
    tabVM: TablesReportViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tabVM.listGet()) {
                Row(tabVM, it)
            }
        }
    }
}

@Composable
private fun Row(
    tabVM: TablesReportViewModel,
    it: ReportStringFromTables,
) {
    Row {
        Text(
            it.id.toString(),
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
}