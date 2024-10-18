package composable.pages.app

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import composable.ui.UiTextButton
import data.viewModels.MainViewModel

@Composable
fun AppSettings(mainVM: MainViewModel) {
    LazyColumn {
        item {
            UiTextButton("Текущая тема: ${mainVM.setVM.currentTheme}", { mainVM.setVM.switchTheme() }, "Сменить тему")
        }
    }
}