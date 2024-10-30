package data.viewModels

import androidx.lifecycle.ViewModel
import data.IntDB

class SettingsViewModel: ViewModel() {
    var theme by IntDB("theme", 0)
    fun switchTheme() {
        theme = when (theme) {
            0 -> 1
            1 -> 2
            2 -> 3
            3 -> 4
            4 -> 5
            5 -> 6
            else -> 0
        }
    }
    var appBarDefaultPage by IntDB("appBarDefaultPage", 1)
    fun switchAppBarDefaultPage() {
        appBarDefaultPage = when (appBarDefaultPage) {
            1 -> 2
            else -> 1
        }
    }
    var appTablesBarCurrentPage by IntDB("appTablesBarCurrentPage", 1)
    fun switchAppTablesBarCurrentPage() {
        appTablesBarCurrentPage = when (appTablesBarCurrentPage) {
            1 -> 2
            2 -> 3
            3 -> 4
            4 -> 5
            else -> 1
        }
    }
}