package com.Tfg.juego.ui.menu.amigos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Tfg.juego.ui.usables.BotonCustom

@Composable
fun buscarAmigos(
    onMenuClick: () -> Unit,
    onMisAmigos: () -> Unit,
    onSolicitudesDeAmistad: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(40.dp)
        )

        BotonCustom(
            text = "Volver al menu",
            width = 200.dp,
            onClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            BotonCustom(
                text = "Mis amigos",
                width = 180.dp,
                onClick = onMisAmigos
            )

            Spacer(modifier = Modifier.width(10.dp))

            BotonCustom(
                text = "Solicitudes amistad",
                width = 200.dp,
                onClick = onSolicitudesDeAmistad
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column {

        }
    }
}