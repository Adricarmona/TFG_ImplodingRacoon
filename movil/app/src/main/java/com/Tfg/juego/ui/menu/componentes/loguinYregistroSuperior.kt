package com.Tfg.juego.ui.menu.componentes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.usables.BotonCustom
import com.auth0.android.jwt.JWT

/**
 *
 *      Barra de arriba
 *
 */
@Composable
fun loguinRegistroArriba(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onMenuClick: () -> Unit
)
{
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        if (!sharedPreferences.getString("token", "").isNullOrEmpty()) {
            usuarioIniciado(onPerfilClick, onMenuClick)
        } else {
            usuarioSinIniciar(onLoginClick, onRegisterClick)
        }

    }
}


/**
 * Botones de cuando estes sin iniciar
 */
@Composable
fun usuarioSinIniciar(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit)
{

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){

        BotonCustom(
            text = "Registrar",
            width = 170.dp,
            onClick = { onRegisterClick() }
        )

        Spacer(modifier = Modifier.width(10.dp))

        BotonCustom(
            text = "Iniciar sesion",
            width = 170.dp,
            onClick = { onLoginClick() }
        )

    }

}

/**
 * Este composable es de cuando en el navbar estas con la cuenta iniciada
 */
@Composable
fun usuarioIniciado(
    onPerfilClick: () -> Unit,
    onMenuClick: () -> Unit
)
{
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)
    val image: Painter = painterResource(id = R.drawable.img_perfil)

    val token = sharedPreferences.getString("token", "")
    val jwt = JWT(token.toString())
    val nombre = jwt.getClaim("name").asString()

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){
        BotonCustom(
            text = "Amigos",
            onClick = {  }
        )

        Spacer(modifier = Modifier.width(120.dp))

        Text(
            text=nombre.toString(),
            modifier = Modifier.clickable(onClick = {
                onPerfilClick()
            })
        )

        Spacer(modifier = Modifier.width(10.dp))

        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(44.dp)) // Borde negro
                .clickable(onClick = { onPerfilClick() })
        )
    }
}