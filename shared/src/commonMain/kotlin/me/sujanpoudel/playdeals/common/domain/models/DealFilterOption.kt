package me.sujanpoudel.playdeals.common.domain.models

data class Selectable<T>(val data: T, val selected: Boolean)

sealed class DealFilterOption(val label: String) {
  data object NewlyAddedApps : DealFilterOption("New Deals")
  data object FreeApps : DealFilterOption("Free Apps")

  data class Category(val title: String, val value: String) : DealFilterOption(title)
}
