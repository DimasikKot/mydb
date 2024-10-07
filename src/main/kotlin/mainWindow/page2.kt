package mainWindow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import viewModels.MainViewModel

@Composable
fun Page2(mainVM: MainViewModel) {
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mainVM.textField,
            onValueChange = {mainVM.textField = it},
            label = { Text("Title OutlinedTextField")}
        )
        Text(mainVM.textField)
    }
}