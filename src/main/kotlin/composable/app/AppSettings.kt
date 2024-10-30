package composable.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.ui.UiTextButton
import data.viewModels.MainViewModel

@Composable
fun AppSettings(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            UiTextButton(
                "Текущая тема: ${mainVM.setVM.theme}",
                { mainVM.setVM.switchTheme() }, "Сменить тему"
            )
        }
        item {
            UiTextButton(
                "Главная страница при запуске: ${mainVM.setVM.appBarDefaultPage}",
                { mainVM.setVM.switchAppBarDefaultPage() },
                "Сменить"
            )
        }
        item {
            UiTextButton(
                "Главная таблица при запуске: ${mainVM.setVM.appTablesBarCurrentPage}",
                { mainVM.setVM.switchAppTablesBarCurrentPage() },
                "Сменить"
            )
        }
    }
}