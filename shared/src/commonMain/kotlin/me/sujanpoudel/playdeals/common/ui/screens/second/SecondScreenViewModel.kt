package me.sujanpoudel.playdeals.common.ui.screens.second

import me.sujanpoudel.playdeals.common.viewModel.ViewModel

class SecondScreenViewModel : ViewModel() {
    override fun onClose() {
        println("$this :: close")
    }
}