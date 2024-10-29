package data.viewModels

import androidx.lifecycle.ViewModel
import data.IntDS

class NavigationViewModel : ViewModel() {
    var currentPage by IntDS(1)
}