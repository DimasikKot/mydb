package data.viewModels

import androidx.lifecycle.ViewModel
import data.BooleanDS
import data.IntDS

class WindowsViewModel : ViewModel() {
    var app by BooleanDS(true)
}