package com.Tfg.juego.ui.menu

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.loguinRegistroArriba

@Composable
fun ajustes(
    onMenuClick: () -> Unit,
    onVerCartas: () -> Unit,
){

    val context = LocalContext.current
    val sharedPref = remember { context.getSharedPreferences("AjustesIplodingRacoon", Context.MODE_PRIVATE) }
    val editorSharedPreferences = sharedPref.edit();

    if (sharedPref.getString("idioma", null) == null)
        editorSharedPreferences.putString("idioma", "System").apply()

    var idioma by remember { mutableStateOf(sharedPref.getString("idioma", "System") ?: "System") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Spacer(modifier = Modifier.height(140.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(stringResource(R.string.seleccionar_carta))
            BotonCustom(
                text = stringResource(R.string.ver_tipos_cartas),
                width = 190.dp,
                height = 40.dp,
                onClick = { onVerCartas() }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(stringResource(R.string.idioma))
            BotonCustom(
                text = idioma.toString(),
                width = 130.dp,
                height = 60.dp,
                enabled = false,
                onClick = {
                    val nuevoIdioma = cambiarIdioma(context)
                    idioma = nuevoIdioma
                }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(stringResource(R.string.modo_oscuro))
            BotonCustom(
                text = "System",
                width = 130.dp,
                height = 60.dp,
                enabled = false,
                onClick = {
                    cambiarModoOscuro(context)
                }
            )

        }

        Spacer(modifier = Modifier.weight(1F))

        BotonCustom(
            text = stringResource(R.string.volver_menu),
            width = 130.dp,
            height = 80.dp,
            onClick = { onMenuClick() }
        )

        Spacer(modifier = Modifier.height(100.dp))

    }
}

fun cambiarIdioma(context: Context): String {
    val sharedPref = context.getSharedPreferences("AjustesIplodingRacoon", Context.MODE_PRIVATE)
    val editorSharedPreferences = sharedPref.edit()

    val idioma = sharedPref.getString("idioma", "System")

    if (idioma == "System") {
        editorSharedPreferences.putString("idioma", "English").apply()
        return "English"
    } else if (idioma == "English") {
        editorSharedPreferences.putString("idioma", "español").apply()
        return "español"
    } else if (idioma == "español") {
        editorSharedPreferences.putString("idioma", "system").apply()
        return "System"
    }

    return ""
}

fun cambiarModoOscuro(context: Context) {

    /// hay que trabajar en esto quiza

}