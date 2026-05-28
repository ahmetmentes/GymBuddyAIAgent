package com.gymbuddyaiagent

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class ChatMessage(val text: String, val isUser: Boolean)

class ChatViewModel : ViewModel() {
    private val chatService = ChatService()

    val messages = mutableStateListOf<ChatMessage>()
    val isLoading = mutableStateOf(false)

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        messages.add(ChatMessage(text = text, isUser = true))
        isLoading.value = true

        viewModelScope.launch {
            try {
                val reply = chatService.sendMessage(text)
                messages.add(ChatMessage(text = reply, isUser = false))
            } catch (e: Exception) {
                messages.add(ChatMessage(text = "Error: ${e.message}", isUser = false))
            } finally {
                isLoading.value = false
            }
        }
    }
}
