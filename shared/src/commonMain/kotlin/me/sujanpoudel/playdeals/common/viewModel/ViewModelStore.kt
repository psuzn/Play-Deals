package me.sujanpoudel.playdeals.common.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import me.sujanpoudel.playdeals.common.di.PrimaryDI
import me.sujanpoudel.playdeals.common.navigation.LocalViewModelFactory
import org.kodein.di.direct
import org.kodein.di.instance
import kotlin.reflect.KClass


interface ViewModelStore {
    fun <T : ViewModel> get(clazz: KClass<T>): T?

    fun save(viewModel: ViewModel)
}

class ViewModelFactory(val store: ViewModelStore) {
    inline fun <reified T : ViewModel> createInstanceOf(): T {
        return store.get(T::class) ?: PrimaryDI.direct.instance<T>().also(store::save)
    }
}

@Composable
inline fun <reified T : ViewModel> viewModel(): T {
    val factory = LocalViewModelFactory.current
    return remember(factory) { factory.createInstanceOf<T>() }
}
