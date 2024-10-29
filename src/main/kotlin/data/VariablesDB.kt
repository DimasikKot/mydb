package data

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import java.util.prefs.Preferences
import kotlin.reflect.KProperty

//fun VariableDBDelete(key: String) = Preferences.userRoot().node(this::class.java.name).remove(key)

class StringDB(private val key: String, default: String) {
    private var value by mutableStateOf(Preferences.userRoot().node(this::class.java.name).get(key, default))
    private fun countSave(value: String) = Preferences.userRoot().node(this::class.java.name).put(key, value)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): String = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: String) {
        value = newValue
        countSave(value)
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): String = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: String) {
        value = newValue
        countSave(value)
    }
}

class BooleanDB(private val key: String, default: Boolean) {
    private var value by mutableStateOf(Preferences.userRoot().node(this::class.java.name).getBoolean(key, default))
    private fun countSave(value: Boolean) = Preferences.userRoot().node(this::class.java.name).putBoolean(key, value)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Boolean = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Boolean) {
        value = newValue
        countSave(value)
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Boolean = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Boolean) {
        value = newValue
        countSave(value)
    }
}

class IntDB(private val key: String, default: Int) {
    private var value by mutableIntStateOf(Preferences.userRoot().node(this::class.java.name).getInt(key, default))
    private fun countSave(value: Int) = Preferences.userRoot().node(this::class.java.name).putInt(key, value)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Int = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Int) {
        value = newValue
        countSave(value)
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Int = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Int) {
        value = newValue
        countSave(value)
    }
}

class LongDB(private val key: String, default: Long) {
    private var value by mutableLongStateOf(Preferences.userRoot().node(this::class.java.name).getLong(key, default))
    private fun countSave(value: Long) = Preferences.userRoot().node(this::class.java.name).putLong(key, value)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Long = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Long) {
        value = newValue
        countSave(value)
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Long = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Long) {
        value = newValue
        countSave(value)
    }
}

class FloatDB(private val key: String, default: Float) {
    private var value by mutableFloatStateOf(Preferences.userRoot().node(this::class.java.name).getFloat(key, default))
    private fun countSave(value: Float) = Preferences.userRoot().node(this::class.java.name).putFloat(key, value)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Float = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Float) {
        value = newValue
        countSave(value)
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Float = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Float) {
        value = newValue
        countSave(value)
    }
}

class DoubleDB(private val key: String, default: Double) {
    private var value by mutableDoubleStateOf(
        Preferences.userRoot().node(this::class.java.name).getDouble(key, default)
    )

    private fun countSave(value: Double) = Preferences.userRoot().node(this::class.java.name).putDouble(key, value)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Double = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Double) {
        value = newValue
        countSave(value)
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Double = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Double) {
        value = newValue
        countSave(value)
    }
}