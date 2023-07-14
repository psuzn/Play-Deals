package me.sujanpoudel.playdeals.frontend.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.sujanpoudel.playdeals.frontend.PlayDealApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayDealApp()
        }
    }
}
