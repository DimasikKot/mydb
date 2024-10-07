package viewModels

import androidx.lifecycle.ViewModel
import data.properties.StringDB
import java.util.prefs.Preferences

class MainViewModel: ViewModel() {
    fun delete(key: String) = Preferences.userRoot().node(this::class.java.name).remove(key)
    var textField by StringDB("textField", "textFieldDefault")
    val settingsVM = SettingsViewModel()
    val windowVM = WindowViewModel()
}