package mainWindow

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ui.UiTextButton
import viewModels.MainViewModel

@Composable
fun Page3(mainVM: MainViewModel) {
    Column {
        UiTextButton(
            textText = mainVM.settingsVM.currentTheme.toString(),
            onClick = { mainVM.settingsVM.switchTheme() })
    }
}