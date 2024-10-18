package data.viewModels

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val setVM = SettingsViewModel()
    val winVM = WindowsViewModel()
}