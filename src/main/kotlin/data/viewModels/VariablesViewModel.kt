package data.viewModels

import androidx.lifecycle.ViewModel
import data.db.StringDS

class VariablesViewModel : ViewModel() {
    var textField by StringDS("TextField")
}