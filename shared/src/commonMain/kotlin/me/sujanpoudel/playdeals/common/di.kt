package me.sujanpoudel.playdeals.common

import me.sujanpoudel.playdeals.common.ui.screens.first.FirstScreenViewModel
import me.sujanpoudel.playdeals.common.ui.screens.second.SecondScreenViewModel
import me.sujanpoudel.playdeals.common.ui.screens.themeSwitcher.ThemeSwitcherViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val PrimaryDI = DI {
    bindProvider { FirstScreenViewModel() }
    bindProvider { SecondScreenViewModel() }
    bindProvider { ThemeSwitcherViewModel() }
}