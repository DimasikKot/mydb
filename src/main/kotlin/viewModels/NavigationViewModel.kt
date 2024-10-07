package viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NavigationViewModel: ViewModel() {
    private val mainPage = 1
    var currentPage by mutableIntStateOf(mainPage)
}