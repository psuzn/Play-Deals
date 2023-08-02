package me.sujanpoudel.playdeals.common.ui.screens.themeSwitcher

import me.sujanpoudel.playdeals.common.viewModel.ViewModel

class ThemeSwitcherViewModel : ViewModel() {
  override fun onClose() {
    println("$this :: close")
  }
}
