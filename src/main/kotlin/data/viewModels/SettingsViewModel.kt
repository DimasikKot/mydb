package data.viewModels

import androidx.lifecycle.ViewModel
import data.db.IntDB

class SettingsViewModel: ViewModel() {
    var currentTheme by IntDB("theme", 0)
    fun switchTheme() {
        currentTheme = when (currentTheme) {
            0 -> 1
            1 -> 2
            2 -> 3
            3 -> 4
            4 -> 5
            5 -> 6
            else -> 0
        }
    }
}