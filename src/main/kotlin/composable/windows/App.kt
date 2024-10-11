package composable.windows

import theme.MainTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import composable.pages.app.*
import data.viewModels.MainViewModel
import data.viewModels.NavigationViewModel

@Composable
fun App(mainVM: MainViewModel) {
    MainTheme(mainVM.setVM.currentTheme) {
        val navVM = remember { NavigationViewModel() }
        Scaffold(
            topBar = {
                TopBar(navVM)
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                when (navVM.currentPage) {
                    2 -> Page2(mainVM)
                    3 -> AppSettings(mainVM)
                    else -> Page1(mainVM)
                }
            }
        }
    }
}