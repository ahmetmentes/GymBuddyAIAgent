package com.gymbuddyaiagent

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getBaseUrl(): String = "http://localhost:8080"