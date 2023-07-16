package me.sujanpoudel.playdeals.common.ui

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import me.sujanpoudel.playdeals.common.utils.asStateFlowClass

enum class AppearanceMode {
    SYSTEM,
    DARK,
    LIGHT,
    BLACK;
}

enum class UIAppearanceMode {
    DARK,
    LIGHT,
    BLACK;

    val isDark
        get() = this == DARK || this == BLACK
}

@Stable
class AppearanceManager() {
    private var _appearanceMode = MutableStateFlow(loadAppearanceMode())
    val appearanceMode = _appearanceMode.asStateFlowClass()

    fun setAppearanceMode(appearanceMode: AppearanceMode) {
        saveAppearanceMode(appearanceMode)
        _appearanceMode.value = appearanceMode
    }

    private fun loadAppearanceMode(): AppearanceMode {
        return AppearanceMode.SYSTEM
    }

    private fun saveAppearanceMode(uiAppearanceMode: AppearanceMode) {
        // TODO: persist it
    }
}