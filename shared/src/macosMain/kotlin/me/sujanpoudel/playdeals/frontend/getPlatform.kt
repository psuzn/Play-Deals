package me.sujanpoudel.playdeals.frontend

class DesktopPlatform : Platform {
    override val name: String = "Desktop Platform"
}

actual fun getPlatform(): Platform {
    return DesktopPlatform()
}