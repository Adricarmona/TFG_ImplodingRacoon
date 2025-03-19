package com.Tfg.juego.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Tfg.juego.R
import com.Tfg.juego.ui.theme.JuegoTheme
import com.Tfg.juego.ui.usables.BotonCustom

@Composable
fun menu() {
    Column(
        modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        if (false) {
            usuarioIniciado()
        } else {
            usuarioSinIniciar()
        }

    }
}

@Composable
fun usuarioSinIniciar() {

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){

        BotonCustom(
            text = "Registrar",
            backgroundColor = Color.White,
            textColor = Color.Black,
            onClick = { /* Acción */ }
        )

        Spacer(modifier = Modifier.width(30.dp))

        BotonCustom(
            text = "Iniciar session",
            backgroundColor = Color.White,
            textColor = Color.Black,
            onClick = { /* Acción */ }
        )

    }

}

/**
 * Este composable es de cuando en el navbar estas con la cuenta iniciada
 */
@Composable
fun usuarioIniciado() {
    val image: Painter = painterResource(id = R.drawable.img_perfil)

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){
        BotonCustom(
            text = "Amigos",
            backgroundColor = Color.White,
            textColor = Color.Black,
            onClick = { /* Acción */ }
        )

        Spacer(modifier = Modifier.width(120.dp))

        Text("Usuario")

        Spacer(modifier = Modifier.width(10.dp))

        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(44.dp)) // Borde negro
        )
    }
}


/**
 * EL PREVIEW PARA VER LAS COSAS
 */
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "es")
@Composable
fun previewAyuda() {
    JuegoTheme {
        menu()
    }
}