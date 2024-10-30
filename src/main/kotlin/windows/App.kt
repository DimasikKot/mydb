package windows

import androidx.compose.animation.Crossfade
import theme.MainTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.app.AppBar
import composable.app.AppSettings
import composable.app.AppTables
import data.viewModels.MainViewModel

@Composable
fun App(mainVM: MainViewModel, modifier: Modifier = Modifier) {
    MainTheme(mainVM.setVM.theme) {
        Scaffold(
            topBar = {
                AppBar(
                    mainVM.navVM,
                    modifier = Modifier.height(60.dp)
                )
            },
            modifier = modifier
        ) {
            Box(modifier = Modifier.padding(it)) {
                Crossfade(mainVM.navVM.appBarCurrentPage) { currentPage ->
                    when (currentPage) {
                        2 -> AppSettings(mainVM, Modifier.padding(10.dp))
                        else -> AppTables(mainVM, Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}