package com.gymbuddyaiagent

import kotlinx.browser.window

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun getBaseUrl(): String {
    val origin = window.location.origin
    return if (origin.isEmpty() || origin.startsWith("file")) {
        "http://localhost:8080"
    } else {
        origin
    }
}