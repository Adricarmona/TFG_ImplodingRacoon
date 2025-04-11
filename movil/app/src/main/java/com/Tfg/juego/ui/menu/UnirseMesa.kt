package com.Tfg.juego.ui.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.Tfg.juego.ui.menu.componentes.loguinRegistroArriba
import com.Tfg.juego.ui.usables.textoLoginYRegistro

@Composable
fun unirMesa(
    onMenuClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPerfilClick: () -> Unit
)
{

    loguinRegistroArriba(onMenuClick, onLoginClick, onRegisterClick, onPerfilClick)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(140.dp))

        textoLoginYRegistro(
            text = "Entrar en una mesa",
            fontSize = 32,
            textAlign = TextAlign.Center,
        )

    }
}