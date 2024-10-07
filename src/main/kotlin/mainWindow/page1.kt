package mainWindow

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import viewModels.MainViewModel

@Composable
fun Page1(mainVM: MainViewModel) {
    Button(onClick = {mainVM.windowVM.two = !mainVM.windowVM.two}) {
        Text(mainVM.windowVM.two.toString())
    }
}