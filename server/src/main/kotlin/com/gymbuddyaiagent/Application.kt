package com.gymbuddyaiagent

import ai.koog.agents.core.agent.AIAgent
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // Get the Gemini API key from the GOOGLE_API_KEY environment variable
    val apiKey = System.getenv("GOOGLE_API_KEY")
        ?: error("The API key is not set.")

    // Create an agent
    val agent = AIAgent(
        promptExecutor = simpleGoogleAIExecutor(apiKey),
        llmModel = GoogleModels.Gemini2_5Pro
    )

    // Run the agent
    val result = agent.run("Hello! How can you help me?")
    println(result)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText(sayHello("Ktor"))
        }
    }
}