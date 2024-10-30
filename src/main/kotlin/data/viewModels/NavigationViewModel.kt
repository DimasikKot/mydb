package data.viewModels

import androidx.lifecycle.ViewModel
import data.IntDB
import data.IntDS
import data.StringDB

class NavigationViewModel : ViewModel() {
    var appBarCurrentPage by IntDS(IntDB("appBarDefaultPage", 1).toInt())
    var appTablesBarCurrentPage by IntDS(IntDB("appTablesBarCurrentPage", 1).toInt())
}