package com.Tfg.juego.ui.menu

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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

    if (sharedPref.getString("tema", null) == null)
        editorSharedPreferences.putString("tema", "System").apply()

    var tema by remember { mutableStateOf(sharedPref.getString("tema", "System") ?: "System") }

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
                text = idioma,
                width = 130.dp,
                height = 60.dp,
                enabled = true,
                onClick = {
                    val nuevoIdioma = cambiarIdioma(context)
                    idioma = nuevoIdioma
                }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(stringResource(R.string.modo_oscuro))
            BotonCustom(
                text = tema,
                width = 130.dp,
                height = 60.dp,
                enabled = true,
                onClick = {
                    val colores = cambiarModoOscuro(context)
                    tema = colores

                    Toast.makeText(context, "Reinicia la aplciacion para aplicar los cambios", Toast.LENGTH_SHORT).show()
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

    if (idioma.equals("System")) {
        editorSharedPreferences.putString("idioma", "English").apply()
        cambiarIdiomaLogica(context, "en")
        return "English"
    } else if (idioma.equals("English")) {
        editorSharedPreferences.putString("idioma", "español").apply()
        cambiarIdiomaLogica(context, "es")
        return "español"
    } else if (idioma.equals("español")) {
        editorSharedPreferences.putString("idioma", "System").apply()
        cambiarIdiomaLogica(context, "System")
        return "System"
    } else {
        editorSharedPreferences.putString("idioma", "System").apply()
        cambiarIdiomaLogica(context, "System")
        return "System"
    }
}

fun cambiarIdiomaLogica(context: Context, idioma: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val localeManager = context.getSystemService(LocaleManager::class.java)
        if (idioma.equals("System")){
            localeManager.applicationLocales = LocaleList.getEmptyLocaleList()
        } else {
            localeManager.applicationLocales = LocaleList.forLanguageTags(idioma)
        }
    }
}


fun cambiarModoOscuro(context: Context): String {

    val sharedPref = context.getSharedPreferences("AjustesIplodingRacoon", Context.MODE_PRIVATE)
    val editorSharedPreferences = sharedPref.edit()

    val tema = sharedPref.getString("tema", "System")

    if (tema.equals("System") || tema.equals("system")) {
        editorSharedPreferences.putString("tema", "Modo oscuro").apply()
        return "Modo oscuro"
    } else if (tema.equals("Modo oscuro")) {
        editorSharedPreferences.putString("tema", "Modo claro").apply()
        return "Modo claro"
    } else if (tema.equals("Modo claro")) {
        editorSharedPreferences.putString("tema", "System").apply()
        cambiarIdiomaLogica(context, "System")
        return "System"
    }

    return ""

}