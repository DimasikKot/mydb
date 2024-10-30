package composable.app.tables.types

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.ui.UiButton
import data.viewModels.MainViewModel

@Composable
fun AppTablesTypesBar(mainVM: MainViewModel) {
    mainVM.tabVM.typesUpdate()
    Row {
        UiButton(
            Icons.Default.Update,
            modifier = Modifier.size(120.dp)
        ) {
            mainVM.tabVM.typesUpdate()
        }
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Card(
                elevation = 10.dp,
                modifier = Modifier.heightIn(min = 55.dp)
            ) {
                Row(Modifier.background(MaterialTheme.colors.secondaryVariant).padding(10.dp)) {
                    Icon(
                        Icons.Default.Style, contentDescription = null,
                        modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                    )
                    Text(
                        "Типы устройств",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                    )
                }
            }
            Card(
                elevation = 10.dp,
                modifier = Modifier.padding(top = 10.dp).heightIn(min = 55.dp)
            ) {
                Row(
                    Modifier
                        .background(MaterialTheme.colors.secondaryVariant).padding(10.dp)
                ) {
                    Row(Modifier.weight(1f)) {
                        var descending by remember { mutableStateOf(false) }
                        Icon(
                            if (descending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                                .clickable {
                                    descending = mainVM.tabVM.typesOrderBy("id")
                                }
                        )
                        Text(
                            "ID",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Row(Modifier.weight(1f).padding(start = 10.dp)) {
                        var descending by remember { mutableStateOf(false) }
                        Icon(
                            if (descending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(35.dp).fillMaxSize().align(Alignment.CenterVertically)
                                .clickable {
                                    descending = mainVM.tabVM.typesOrderBy("name")
                                }
                        )
                        Text(
                            "Названия",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}