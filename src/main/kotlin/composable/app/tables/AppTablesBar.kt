package composable.app.tables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.viewModels.MainViewModel
import data.viewModels.NavigationViewModel

@Composable
fun appTablesBar(
    navVM: NavigationViewModel,
    unit1: () -> Unit = { navVM.appTablesBarCurrentPage = 1 },
    unit2: () -> Unit = { navVM.appTablesBarCurrentPage = 2 },
    unit3: () -> Unit = { navVM.appTablesBarCurrentPage = 3 },
    unit4: () -> Unit = { navVM.appTablesBarCurrentPage = 4 },
    unit5: () -> Unit = { navVM.appTablesBarCurrentPage = 5 },
    modifier: Modifier = Modifier,
    debug: Boolean
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                elevation = 10.dp,
                modifier = Modifier.weight(1f)
            ) {
                NavigationRailItem(
                    icon = {
                        Column(Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Default.Style,
                                contentDescription = null,
                                Modifier.weight(3f).fillMaxSize().align(Alignment.CenterHorizontally)
                            )
                            Text(
                                "Типы устройств",
                                style = MaterialTheme.typography.button,
                                modifier = Modifier.weight(1f).padding(top = 10.dp).align(Alignment.CenterHorizontally)
                            )
                        }
                    },
                    selected = navVM.appTablesBarCurrentPage == 1,
                    onClick = unit1,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Card(
                elevation = 10.dp,
                modifier = Modifier.weight(1f)
            ) {
                NavigationRailItem(
                    icon = {
                        Column(Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Default.Devices,
                                contentDescription = null,
                                Modifier.weight(3f).fillMaxSize().align(Alignment.CenterHorizontally)
                            )
                            Text(
                                "Устройства",
                                style = MaterialTheme.typography.button,
                                modifier = Modifier.weight(1f).padding(top = 10.dp).align(Alignment.CenterHorizontally)
                            )
                        }
                    },
                    selected = navVM.appTablesBarCurrentPage == 2,
                    onClick = unit2,
                    modifier = Modifier.fillMaxSize()
                )
            }
            if (debug) {
                Card(
                    elevation = 10.dp,
                    modifier = Modifier.weight(1f)
                ) {
                    NavigationRailItem(
                        icon = {
                            Column(Modifier.fillMaxSize()) {
                                Icon(
                                    Icons.Default.Newspaper,
                                    contentDescription = null,
                                    Modifier.weight(3f).fillMaxSize().align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    "Строки",
                                    style = MaterialTheme.typography.button,
                                    modifier = Modifier.weight(1f).padding(top = 10.dp).align(Alignment.CenterHorizontally)
                                )
                            }
                        },
                        selected = navVM.appTablesBarCurrentPage == 3,
                        onClick = unit3,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Card(
                elevation = 10.dp,
                modifier = Modifier.weight(1f)
            ) {
                NavigationRailItem(
                    icon = {
                        Column(Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Default.People,
                                contentDescription = null,
                                Modifier.weight(3f).fillMaxSize().align(Alignment.CenterHorizontally)
                            )
                            Text(
                                "Сотрудники",
                                style = MaterialTheme.typography.button,
                                modifier = Modifier.weight(1f).padding(top = 10.dp).align(Alignment.CenterHorizontally)
                            )
                        }
                    },
                    selected = navVM.appTablesBarCurrentPage == 4,
                    onClick = unit4,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Card(
                elevation = 10.dp,
                modifier = Modifier.weight(1f)
            ) {
                NavigationRailItem(
                    icon = {
                        Column(Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Default.Groups,
                                contentDescription = null,
                                Modifier.weight(3f).fillMaxSize().align(Alignment.CenterHorizontally)
                            )
                            Text(
                                "Отделы",
                                style = MaterialTheme.typography.button,
                                modifier = Modifier.weight(1f).padding(top = 10.dp).align(Alignment.CenterHorizontally)
                            )
                        }
                    },
                    selected = navVM.appTablesBarCurrentPage == 5,
                    onClick = unit5,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}