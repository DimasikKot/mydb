package data.viewModels

import androidx.lifecycle.ViewModel
import data.BooleanDS

class WindowsViewModel : ViewModel() {
    var app by BooleanDS(true)
}