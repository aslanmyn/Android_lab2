package com.example.chatecholib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatecholib.databinding.ActivityChatBinding
import okhttp3.*
import okio.ByteString
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val adapter = MessageAdapter()
    private val webclient by lazy { OkHttpClient() }
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerViewChat.adapter = adapter
        initWebSocket()
        binding.buttonSend.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            if(message.isNotBlank()) {
                webSocket?.send(message)
                adapter.addMessage(Message(message, getCurrentTime(), false))
            }
        }
    }
    private fun initWebSocket() {
        val request = Request.Builder().url("wss://echo.websocket.org").build()
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {}
            override fun onMessage(webSocket: WebSocket, text: String) {
                runOnUiThread {
                    if(text == "203 = 0xcb") {
                        adapter.addMessage(Message("?", getCurrentTime(), true))
                    } else {
                        adapter.addMessage(Message(text, getCurrentTime(), true))
                    }
                }
            }
            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {}
            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {}
        }
        webSocket = webclient.newWebSocket(request, listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket?.close(1000, null)
        webSocket = null
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }
}