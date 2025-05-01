package com.Tfg.juego.ui.menu

import android.R
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.loguinRegistroArriba

@Composable
fun ajustes(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onMenuClick: () -> Unit,
    onVerCartas: () -> Unit,
){

    val context = LocalContext.current
    val sharedPref = remember { context.getSharedPreferences("AjustesIplodingRacoon", Context.MODE_PRIVATE) }
    val editorSharedPreferences = sharedPref.edit();

    if (sharedPref.getString("idioma", null) == null)
        editorSharedPreferences.putString("idioma", "system").apply()

    var idioma by remember { mutableStateOf(sharedPref.getString("idioma", "system") ?: "system") }


    loguinRegistroArriba(
        onLoginClick = onLoginClick,
        onRegisterClick = onRegisterClick,
        onPerfilClick = onPerfilClick,
        onMenuClick = onMenuClick
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(140.dp))

        BotonCustom(
            text = "Ver tipos de cartas",
            width = 190.dp,
            height = 50.dp,
            onClick = { onVerCartas() }
        )

        Spacer(modifier = Modifier.height(10.dp))

        BotonCustom(
            text = idioma.toString(),
            width = 130.dp,
            height = 80.dp,
            onClick = {
                val nuevoIdioma = cambiarIdioma(context)
                idioma = nuevoIdioma
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        BotonCustom(
            text = "Volver al menu",
            width = 130.dp,
            height = 80.dp,
            onClick = { onMenuClick() }
        )

    }
}

fun cambiarIdioma(context: Context): String {
    val sharedPref = context.getSharedPreferences("AjustesIplodingRacoon", Context.MODE_PRIVATE)
    val editorSharedPreferences = sharedPref.edit()

    val idioma = sharedPref.getString("idioma", "system")

    if (idioma == "system") {
        editorSharedPreferences.putString("idioma", "ingles").apply()
        return "ingles"
    } else if (idioma == "ingles") {
        editorSharedPreferences.putString("idioma", "español").apply()
        return "español"
    } else if (idioma == "español") {
        editorSharedPreferences.putString("idioma", "system").apply()
        return "system"
    }

    return ""
}