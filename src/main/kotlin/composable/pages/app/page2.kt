package composable.pages.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.viewModels.MainViewModel

@Composable
fun Page2(mainVM: MainViewModel) {
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mainVM.varVM.textField,
            onValueChange = { mainVM.varVM.textField = it },
            label = { Text("Title OutlinedTextField") }
        )
        Text(mainVM.varVM.textField)
    }
}