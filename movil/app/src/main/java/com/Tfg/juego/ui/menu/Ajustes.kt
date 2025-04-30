package com.Tfg.juego.ui.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            text = "Ver ajustes de cartas",
            width = 200.dp,
            height = 100.dp,
            onClick = { onVerCartas() }
        )

    }
}