package com.gymbuddyaiagent

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform