package com.Tfg.juego.model.webSockets

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MyWebSocketListener(
    private val onMessageReceived: (String) -> Unit
) : WebSocketListener() {
    private val coroutineScope = MainScope()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        // Conexión establecida
        onMessageReceived("Conexión abierta")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        coroutineScope.launch(Dispatchers.Main) {
            onMessageReceived(text)
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // Mensaje recibido como bytes (opcional)
        onMessageReceived(bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        // Conexión cerrándose
        onMessageReceived("Cerrando: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        // Error en la conexión
        onMessageReceived("Error: ${t.message}")
    }
}