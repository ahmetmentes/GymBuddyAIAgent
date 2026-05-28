package com.gymbuddyaiagent

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleLLMClient
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ChatRequest(val message: String)

@Serializable
data class ChatResponse(val reply: String)

fun main() {
    val apiKey = System.getenv("GOOGLE_API_KEY")
        ?: error("GOOGLE_API_KEY environment variable is not set.")

    val googleClient = GoogleLLMClient(apiKey)
    val executor = MultiLLMPromptExecutor(googleClient)

    val agent = AIAgent.builder()
        .promptExecutor(executor)
        .llmModel(GoogleModels.Gemini2_5Pro)
        .build()

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        routing {
            post("/chat") {
                val request = call.receive<ChatRequest>()
                val result = agent.run(request.message)
                call.respond(ChatResponse(reply = result))
            }
            get("/health") {
                call.respondText("OK")
            }
        }
    }.start(wait = true)
}
