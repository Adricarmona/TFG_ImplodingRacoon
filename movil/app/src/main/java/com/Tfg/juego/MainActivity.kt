package com.Tfg.juego

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.Tfg.juego.model.webSockets.MyWebSocketListener
import com.Tfg.juego.model.webSockets.WebSocketManager
import com.Tfg.juego.ui.navigation.Navigation
import com.Tfg.juego.ui.navigation.menu
import com.Tfg.juego.ui.theme.JuegoTheme
import java.util.prefs.Preferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPref = this.getSharedPreferences("AjustesImplodingRacoon", Context.MODE_PRIVATE)
        val sharedPreferences = this.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)

        val tema = sharedPref.getString("tema", "System")

        val token = sharedPreferences.getString("token", "")

        var modoOscuro: Boolean? = false
        if (tema.equals("Modo oscuro"))
            modoOscuro = true
        else if (tema.equals("Modo claro"))
            modoOscuro = false
        else
            modoOscuro = null;

        setContent {
            val webSocketManager = remember { WebSocketManager() }
            var receivedMessages by remember { mutableStateOf(listOf<String>()) }
            val listener = remember {
                MyWebSocketListener { msg ->
                    receivedMessages = receivedMessages + msg
                    Log.d("Mensaje", receivedMessages.toString())
                }
            }

            if (token != null){
                webSocketManager.connect(sharedPreferences.getString("baseUrl", "") + "WebSocket", listener)
            }
            JuegoTheme(
                darkTheme = modoOscuro ?: isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                menu()
            }
        }
    }
}