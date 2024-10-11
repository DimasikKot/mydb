package data.viewModels

import androidx.lifecycle.ViewModel
import data.db.IntDS

class NavigationViewModel : ViewModel() {
    var currentPage by IntDS(1)
}