package composable.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.ui.uiTextButton
import data.requestSQL
import data.StringDB
import data.viewModels.MainViewModel

@Composable
fun appSettings(
    mainVM: MainViewModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            uiTextButton(
                "Текущая тема: ${mainVM.setVM.theme}",
                { mainVM.setVM.switchTheme() }, "Сменить тему"
            )
        }
        item {
            uiTextButton(
                "Главная страница при запуске: ${mainVM.setVM.appBarDefaultPage}",
                { mainVM.setVM.switchAppBarDefaultPage() },
                "Сменить"
            )
        }
        item {
            uiTextButton(
                "Главная таблица при запуске: ${mainVM.setVM.appTablesBarCurrentPage}",
                { mainVM.setVM.switchAppTablesBarCurrentPage() },
                "Сменить"
            )
        }
        item {
            TextField(
                value = if (mainVM.setVM.reportDefaultDevice != 0) mainVM.setVM.reportDefaultDevice.toString() else "",
                onValueChange = {
                    if (it == "") mainVM.setVM.reportDefaultDevice =
                        0 else if (it.matches(regex = Regex("^\\d*\$"))) mainVM.setVM.reportDefaultDevice = it.toInt()
                },
                label = { Text(if (mainVM.setVM.reportDefaultDevice == 0) "При запуске учёт устройства с оперделённым ID выключен" else "При запуске учёт устройства с ID включен") },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
            )
        }
        item {
            TextField(
                value = if (mainVM.setVM.reportDefaultEmployee != 0) mainVM.setVM.reportDefaultEmployee.toString() else "",
                onValueChange = {
                    if (it == "") mainVM.setVM.reportDefaultEmployee =
                        0 else if (it.matches(regex = Regex("^\\d*\$"))) mainVM.setVM.reportDefaultEmployee = it.toInt()
                },
                label = { Text(if (mainVM.setVM.reportDefaultEmployee == 0) "При запуске учёт сотрудника с оперделённым ID выключен" else "При запуске учёт сотрудника с ID включен") },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
            )
        }
        item {
            TextField(
                value = if (mainVM.setVM.reportDefaultGroup != 0) mainVM.setVM.reportDefaultGroup.toString() else "",
                onValueChange = {
                    if (it == "") mainVM.setVM.reportDefaultGroup =
                        0 else if (it.matches(regex = Regex("^\\d*\$"))) mainVM.setVM.reportDefaultGroup = it.toInt()
                },
                label = { Text(if (mainVM.setVM.reportDefaultGroup == 0) "При запуске учёт группы с оперделённым ID выключен" else "При запуске учёт группы с ID включен") },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
            )
        }
        item {
            var textSQL by StringDB("textSQL", "")
            TextField(
                value = textSQL,
                onValueChange = { textSQL = it },
                label = { Text("SQL запрос") },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
            )
            TextField(
                value = requestSQL(textSQL),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
            )
        }
    }
}