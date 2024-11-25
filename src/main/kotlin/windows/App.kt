package windows

import androidx.compose.animation.Crossfade
import theme.mainTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.app.appBar
import composable.app.appSettings
import composable.app.appTables
import data.viewModels.MainViewModel

@Composable
fun app(mainVM: MainViewModel, modifier: Modifier = Modifier) {
    mainTheme(mainVM.setVM.theme) {
        Scaffold(
            topBar = {
                appBar(
                    mainVM.navVM,
                    modifier = Modifier.height(60.dp)
                )
            },
            modifier = modifier
        ) {
            Box(modifier = Modifier.padding(it)) {
                Crossfade(mainVM.navVM.appBarCurrentPage) { currentPage ->
                    when (currentPage) {
                        2 -> appSettings(mainVM, Modifier.padding(10.dp))
                        else -> appTables(mainVM, Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}