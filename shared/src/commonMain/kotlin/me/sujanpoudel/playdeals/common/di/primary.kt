package me.sujanpoudel.playdeals.common.di

import org.kodein.di.DI

val PrimaryDI = DI {
  importOnce(persistentModule)
  importOnce(networkingModule)
  importOnce(viewModelModule)
}
