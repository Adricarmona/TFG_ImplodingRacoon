package com.Tfg.juego.ui.usables

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Tfg.juego.R
import com.Tfg.juego.model.retrofit.dto.userPerfil
import com.Tfg.juego.model.servicios.getDatosPerfil
import com.Tfg.juego.model.webSockets.MyWebSocketListener
import com.Tfg.juego.model.webSockets.WebSocketManager
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.awaitAll

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
    onAmigosClick: () -> Unit
)
{
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        if (!sharedPreferences.getString("token", "").isNullOrEmpty()) {
            usuarioIniciado(onPerfilClick, onAmigosClick)
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
            text = stringResource(R.string.registrar),
            width = 170.dp,
            onClick = { onRegisterClick() }
        )

        Spacer(modifier = Modifier.width(10.dp))

        BotonCustom(
            text = stringResource(R.string.iniciar_sesion),
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
    onAmigosClick: () -> Unit
)
{
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)

    val token = sharedPreferences.getString("token", "")
    val jwt = JWT(token.toString())
    val nombre = jwt.getClaim("name").asString()
    val id = jwt.getClaim("id").asInt()

    val webSocketManager = remember { WebSocketManager() }
    var receivedMessages by remember { mutableStateOf(listOf<String>()) }
    val listener = remember {
        MyWebSocketListener { msg ->
            receivedMessages = receivedMessages + msg
            Log.d("Mensaje", receivedMessages.toString())
        }
    }

    val perfilState = remember { mutableStateOf<userPerfil?>(null) }

    LaunchedEffect(id) {
        try {
            val datosPerfil = id?.let { getDatosPerfil(it) } // Llamada a la función suspend
            perfilState.value = datosPerfil
            if (token != null){
                webSocketManager.connect(sharedPreferences.getString("baseUrl", "") + "WebSocket", listener)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){
        BotonCustom(
            text = stringResource(R.string.amigos),
            onClick = { onAmigosClick() }
        )

        Spacer(modifier = Modifier.width(120.dp))

        Text(
            text = nombre.toString(),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = {
                onPerfilClick()
            })

        )

        Spacer(modifier = Modifier.width(10.dp))

        AsyncImage(
            model = perfilState.value?.urlFoto,
            contentDescription = "",
            placeholder = painterResource(R.drawable.img_mapacheicono),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, shape = CircleShape) // Borde negro
                .clickable(onClick = { onPerfilClick() })
        )
    }
}