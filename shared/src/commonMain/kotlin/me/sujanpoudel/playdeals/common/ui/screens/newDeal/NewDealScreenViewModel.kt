package me.sujanpoudel.playdeals.common.ui.screens.newDeal

import me.sujanpoudel.playdeals.common.viewModel.ViewModel

class NewDealScreenViewModel() : ViewModel() {
  override fun onClose() {
    println("$this :: close")
  }
}
