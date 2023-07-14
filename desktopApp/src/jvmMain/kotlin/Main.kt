import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sujanpoudel.playdeals.frontend.MainView
import org.jetbrains.skiko.Cursor
import java.awt.Menu
import java.awt.event.FocusEvent
import java.awt.event.FocusListener

fun main() = singleWindowApplication(
    title = "Chat",
    state = WindowState(
        size = DpSize(500.dp, 800.dp),
    ),
//    undecorated = true
) {

    val frameScope = this

    LaunchedEffect(Unit) {
        frameScope.window.addFocusListener(object : FocusListener {
            override fun focusGained(p0: FocusEvent?) {
            }

            override fun focusLost(p0: FocusEvent?) {

            }

        })
    }

    MainView()
}
