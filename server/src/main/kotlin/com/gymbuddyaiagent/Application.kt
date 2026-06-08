package com.gymbuddyaiagent

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.openai.OpenAIClientSettings
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.llm.LLMProvider
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ChatRequest(val message: String)

@Serializable
data class ChatResponse(val reply: String)

fun main() {
    val apiKey = System.getenv("GOOGLE_API_KEY")
        ?: error("GOOGLE_API_KEY environment variable is not set.")

    val geminiClient = OpenAILLMClient(
        apiKey = apiKey,
        settings = OpenAIClientSettings(
            baseUrl = "https://generativelanguage.googleapis.com/v1beta/openai"
        )
    )
    val executor = MultiLLMPromptExecutor(geminiClient)

    val geminiModel = LLModel(
        provider = LLMProvider.Google,
        id = "gemini-2.5-pro"
    )

    val agent = AIAgent.builder()
        .promptExecutor(executor)
        .llmModel(geminiModel)
        .build()

    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    embeddedServer(Netty, port = port) {
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
            staticResources("/", "static")
        }
    }.start(wait = true)
}
