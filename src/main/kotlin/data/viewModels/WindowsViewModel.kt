package data.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import data.IntDB

class WindowsViewModel : ViewModel() {
    var app by mutableStateOf(true)
    var reportDevice by mutableIntStateOf(IntDB("reportDefaultDevice", 0).toInt())
    var reportGroup by mutableIntStateOf(IntDB("reportDefaultGroup", 0).toInt())
    var reportEmployee by mutableIntStateOf(IntDB("reportDefaultEmployee", 0).toInt())
}