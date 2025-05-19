package com.Tfg.juego

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.Tfg.juego.ui.navigation.Navigation
import com.Tfg.juego.ui.navigation.menu
import com.Tfg.juego.ui.theme.JuegoTheme
import java.util.prefs.Preferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPref = this.getSharedPreferences("AjustesIplodingRacoon", Context.MODE_PRIVATE)
        val tema = sharedPref.getString("tema", "System")

        var modoOscuro: Boolean? = false
        if (tema.equals("Modo oscuro"))
            modoOscuro = true
        else if (tema.equals("Modo claro"))
            modoOscuro = false
        else
            modoOscuro = null;

        setContent {
            JuegoTheme(
                darkTheme = modoOscuro ?: isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                menu()
            }
        }
    }
}