package data

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlin.reflect.KProperty

class StringDS(default: String) {
    private var value by mutableStateOf(default)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): String = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: String) {
        value = newValue
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): String = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: String) {
        value = newValue
    }
}

class BooleanDS(default: Boolean) {
    private var value by mutableStateOf(default)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Boolean = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Boolean) {
        value = newValue
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Boolean = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Boolean) {
        value = newValue
    }
}

class IntDS(default: Int) {
    private var value by mutableIntStateOf(default)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Int = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Int) {
        value = newValue
    }
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Int = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Int) {
        value = newValue
    }
}

class LongDS(default: Long) {
    private var value by mutableLongStateOf(default)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Long = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Long) = newValue
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Long = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Long) = newValue
}

class FloatDS(default: Float) {
    private var value by mutableFloatStateOf(default)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Float = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Float) = newValue
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Float = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Float) = newValue
}

class DoubleDS(default: Double) {
    private var value by mutableDoubleStateOf(default)
    operator fun getValue(viewModel: ViewModel, property: KProperty<*>): Double = value
    operator fun setValue(viewModel: ViewModel, property: KProperty<*>, newValue: Double) = newValue
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Double = value
    operator fun setValue(nothing: Nothing?, property: KProperty<*>, newValue: Double) = newValue
}