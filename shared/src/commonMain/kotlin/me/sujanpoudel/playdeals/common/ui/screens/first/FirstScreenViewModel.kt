package me.sujanpoudel.playdeals.common.ui.screens.first

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sujanpoudel.playdeals.common.viewModel.ViewModel
import me.sujanpoudel.playdeals.common.viewModel.viewModelScope


class FirstScreenViewModel : ViewModel() {
    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                println(this@FirstScreenViewModel)
            }
        }
    }
}