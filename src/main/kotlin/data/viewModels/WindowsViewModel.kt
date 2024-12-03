package data.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WindowsViewModel : ViewModel() {
    var app by mutableStateOf(true)
    var reportDevice by mutableIntStateOf(0)
}