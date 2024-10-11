package composable.pages.app

import androidx.compose.runtime.Composable
import composable.ui.UiTextButton
import data.viewModels.MainViewModel

@Composable
fun AppSettings(mainVM: MainViewModel) {
    UiTextButton(mainVM.setVM.currentTheme.toString(), { mainVM.setVM.switchTheme() })
}