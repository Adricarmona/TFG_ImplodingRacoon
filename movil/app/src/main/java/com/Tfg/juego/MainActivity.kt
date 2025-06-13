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
import androidx.core.content.edit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPref = getSharedPreferences("AjustesImplodingRacoon", MODE_PRIVATE)
        val sharedPreferences = getSharedPreferences("tokenusuario", MODE_PRIVATE)

        if (sharedPreferences.getString("baseUrl", null).isNullOrEmpty()) {
            sharedPreferences.edit { putString("baseUrl", "wss://implodingracoons.tryasp.net/") }
        }

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
            val BASE_URL = sharedPreferences.getString("baseUrl", "") + "WebSocket"
            val webSocketManager = remember { WebSocketManager() }
            var receivedMessages by remember { mutableStateOf(listOf<String>()) }
            val listener = remember {
                MyWebSocketListener { msg ->
                    receivedMessages = receivedMessages + msg
                    Log.d("Mensajessss", receivedMessages.toString())
                }
            }

            Log.d("Token", token + "")

            if (token != ""){
                Log.d("WebSocketURL", BASE_URL)
                webSocketManager.connect(BASE_URL, listener)
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