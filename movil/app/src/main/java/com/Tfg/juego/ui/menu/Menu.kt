package com.Tfg.juego.ui.menu

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.usables.loguinRegistroArriba
import com.Tfg.juego.ui.usables.BotonCustom


/**
 * EL menu inicial con todos los botones
 */
@Composable
fun menuInicial(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onMenuClick: () -> Unit,
    onUnirseClick: () -> Unit,
    onAjustesClick: () -> Unit,
) {
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)

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

        Image(
            painter =  painterResource(id = R.drawable.img_imploding_racoons_logo),
            contentDescription = "",
            modifier = Modifier
                .size(300.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        BotonCustom(
            text = "Unirse a una mesa",
            height = 200.dp,
            width = 300.dp,
            enabled = !sharedPreferences.getString("token", "").isNullOrEmpty()
        )
        { onUnirseClick() }

        Spacer(modifier = Modifier.height(20.dp))

        BotonCustom(
            text = "Wiki",
            height = 60.dp,
            width = 300.dp
        )
        { /* Acción */ }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotonCustom(
                text = "Ajustes",
                height = 60.dp,
                width = 142.dp
            )
            { onAjustesClick() }

            Spacer(modifier = Modifier.width(15.dp))

            BotonCustom(
                text = "Sobre nosotros",
                height = 60.dp,
                width = 142.dp
            )
            { }
        }
    }
}


