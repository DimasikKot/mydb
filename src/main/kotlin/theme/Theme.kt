package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

private val lightColorScheme = Colors(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryVariant = primaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryVariant = secondaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    isLight = true
)

private val darkColorScheme = Colors(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryVariant = primaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryVariant = secondaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    isLight = false
)

private val mediumContrastLightColorScheme = Colors(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryVariant = primaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryVariant = secondaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    isLight = true
)

private val highContrastLightColorScheme = Colors(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryVariant = primaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryVariant = secondaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    isLight = false
)

private val mediumContrastDarkColorScheme = Colors(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryVariant = primaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryVariant = secondaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    isLight = true
)

private val highContrastDarkColorScheme = Colors(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryVariant = primaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryVariant = secondaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    isLight = false
)

@Composable
fun mainTheme(
    currentTheme: Int, darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val colorScheme: Colors = when (currentTheme) {
        1 -> lightColorScheme
        2 -> darkColorScheme
        3 -> mediumContrastLightColorScheme
        4 -> mediumContrastDarkColorScheme
        5 -> highContrastLightColorScheme
        6 -> highContrastDarkColorScheme
        else -> when {
            darkTheme -> darkColorScheme
            else -> lightColorScheme
        }
    }

    MaterialTheme(
        colors = colorScheme, typography = Typography, content = content
    )
}