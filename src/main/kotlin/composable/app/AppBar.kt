package composable.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.viewModels.NavigationViewModel

@Composable
fun appBar(
    navVM: NavigationViewModel,
    unit1: () -> Unit = { navVM.appBarCurrentPage = 1 },
    unit2: () -> Unit = { navVM.appBarCurrentPage = 2 },
    modifier: Modifier = Modifier,
) {
    Surface(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Card(
                elevation = 10.dp,
                modifier = Modifier.weight(1f)
            ) {
                NavigationRailItem(
                    icon = {
                        Row(Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Default.TableRows,
                                contentDescription = null,
                                Modifier.size(50.dp).align(Alignment.CenterVertically)
                            )
                            Text(
                                "Таблицы",
                                style = MaterialTheme.typography.button,
                                modifier = Modifier.padding(start = 10.dp).align(Alignment.CenterVertically)
                            )
                        }
                    },
                    selected = navVM.appBarCurrentPage == 1,
                    onClick = unit1
                )
            }
            Card(
                elevation = 10.dp,
                modifier = Modifier.weight(1f)
            ) {
                NavigationRailItem(
                    icon = {
                        Row(Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                                Modifier.size(50.dp).align(Alignment.CenterVertically)
                            )
                            Text(
                                "Настройки",
                                style = MaterialTheme.typography.button,
                                modifier = Modifier.padding(start = 10.dp).align(Alignment.CenterVertically)
                            )
                        }
                    },
                    selected = navVM.appBarCurrentPage == 2,
                    onClick = unit2
                )
            }
        }
    }
}