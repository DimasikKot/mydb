package composable.pages.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.db.*
import data.viewModels.NavigationViewModel

@Composable
fun AppTables() {
    val navVM = remember { NavigationViewModel() }
    Row {
        TablesBar(navVM)
        CurrentTable(navVM)
    }
}

@Composable
private fun CurrentTable(
    navVM: NavigationViewModel
) {
    Card(elevation = 5.dp, modifier = Modifier.fillMaxSize().padding(top = 10.dp, bottom = 10.dp, end = 10.dp)) {
        Box(modifier = Modifier.padding(10.dp)) {
            Crossfade(navVM.currentPage) { currentPage ->
                when (currentPage) {
                    2 -> {
                        Devices()
                    }

                    3 -> {
                        Text("3")
                    }

                    4 -> {
                        Text("4")
                    }

                    5 -> {
                        Text("5")
                    }

                    else -> {
                        Types()
                    }
                }
            }
        }
    }
}