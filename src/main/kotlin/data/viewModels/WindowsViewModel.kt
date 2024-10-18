package data.viewModels

import androidx.lifecycle.ViewModel
import data.db.BooleanDS
import data.db.IntDS

class WindowsViewModel : ViewModel() {
    var app by BooleanDS(true)
    var report by BooleanDS(true)
    var currentDevice by IntDS(1)
}