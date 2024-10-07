package viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WindowViewModel {
    var main by mutableStateOf(true)
    var two by mutableStateOf(false)
}