package org.simonolander.lambda.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel(dialog: Dialog?) : ViewModel() {
    val chatMessages = mutableStateListOf<ChatMessage>()
    var currentDialog by mutableStateOf(dialog)
        private set
    var typing by mutableStateOf(false)
        private set

    init {
        val currentDialog = currentDialog
        if (currentDialog is Message) {
            viewModelScope.launch { type(currentDialog) }
        }
    }

    private suspend fun type(currentDialog: Message) {
        typing = true
        delay(1000)
        if (this.currentDialog == currentDialog) {
            nextMessage()
        }
    }

    fun nextMessage() {
        typing = false
        val currentDialog = currentDialog
        if (currentDialog is Message) {
            chatMessages += ChatMessage(currentDialog.text)
            val nextDialog = currentDialog.next
            this.currentDialog = nextDialog
            if (nextDialog is Message) {
                viewModelScope.launch { type(nextDialog) }
            }
        }
    }

    fun respond(response: Response) {
        val currentDialog = currentDialog
        if (currentDialog is Question && currentDialog.responses.contains(response)) {
            this.currentDialog = response.second
        }
    }
}
