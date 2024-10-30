package data.viewModels

import androidx.lifecycle.ViewModel
import data.BooleanDS
import data.IntDS

class WindowsViewModel : ViewModel() {
    var app by BooleanDS(true)
    var report by BooleanDS(false)
    var reportCurrentDevice by IntDS(0)
}