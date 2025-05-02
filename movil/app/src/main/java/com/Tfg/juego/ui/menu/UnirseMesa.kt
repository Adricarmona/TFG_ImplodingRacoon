package com.Tfg.juego.ui.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CheckBoxLoginRegistro
import com.Tfg.juego.ui.usables.loguinRegistroArriba
import com.Tfg.juego.ui.usables.outlinedTextFieldLoginRegistro
import com.Tfg.juego.ui.usables.textoLoginYRegistro

@Composable
fun unirMesa(
    onMenuClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPerfilClick: () -> Unit
)
{
    val codigoMesa = remember { mutableStateOf("") }
    val passwordMesa = remember { mutableStateOf(false) }
    val passwordMesaText = remember { mutableStateOf("") }

    loguinRegistroArriba(onMenuClick, onLoginClick, onRegisterClick, onPerfilClick)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(140.dp))

        /// le pongo otro espacio por dejar la parte de arriba igual a la de las demas
        Spacer(modifier = Modifier.height(110.dp))

        textoLoginYRegistro(
            text = stringResource(R.string.entrar_mesa),
            fontSize = 32,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(90.dp))

        outlinedTextFieldLoginRegistro(
            text = codigoMesa,
            placeholderTexto = stringResource(R.string.codigo_mesa),
        )

        Spacer(modifier = Modifier.height(20.dp))

        CheckBoxLoginRegistro(
            recuerdaCuenta = passwordMesa,
            string = stringResource(R.string.sala_contrasena)
        )

        Spacer(modifier = Modifier.height(20.dp))

        outlinedTextFieldLoginRegistro(
            text = passwordMesaText,
            placeholderTexto = stringResource(R.string.contrasena),
            enabled = passwordMesa.value
        )

        Spacer(modifier = Modifier.height(40.dp))

        BotonCustom(
            text = stringResource(R.string.unirse),
            width = 160.dp,
            height = 50.dp,
        ) { }

    }
}