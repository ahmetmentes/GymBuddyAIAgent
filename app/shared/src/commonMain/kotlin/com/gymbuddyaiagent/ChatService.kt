package com.gymbuddyaiagent

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ChatRequest(val message: String)

@Serializable
data class ChatResponse(val reply: String)

class ChatService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 120_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 120_000
        }
    }

    suspend fun sendMessage(message: String): String {
        val response = client.post("${getBaseUrl()}/chat") {
            contentType(ContentType.Application.Json)
            setBody(ChatRequest(message))
        }
        return response.body<ChatResponse>().reply
    }
}
