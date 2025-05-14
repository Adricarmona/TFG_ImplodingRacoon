package com.Tfg.juego.ui.menu.amigos

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Tfg.juego.R
import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.dto.userAmigos
import com.Tfg.juego.model.retrofit.dto.userPerfil
import com.Tfg.juego.model.servicios.decodificarJWT
import com.Tfg.juego.model.servicios.getDatosPerfil
import com.Tfg.juego.model.servicios.getFriendsService
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CardAmigos
import com.Tfg.juego.ui.usables.textoLoginYRegistro
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun amigos(
    onMenuClick: () -> Unit
) {
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("token", "")

    val jwt = decodificarJWT(token.toString())
    val userId = jwt?.getClaim("id")?.asInt() ?: 1

    var amigos by remember { mutableStateOf<List<userAmigos>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                amigos = getFriendsService(userId)
                println("Amigos obtenidos: $amigos")
                error = null
            } catch (e: Exception) {
                error = "Error al cargar el perfil: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }


    Column(
            horizontalAlignment = Alignment.CenterHorizontally
            ){
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
                text = "Buscar amigos",
                width = 180.dp,
                onClick = { }
            )

            Spacer(modifier = Modifier.width(10.dp))

            BotonCustom(
                text = "Solicitudes amistad",
                width = 200.dp,
                onClick = { }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        textoLoginYRegistro(
            text = "Amigos",
            fontSize = 40
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {

                }
                else -> {

                    if (amigos.isNullOrEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.img_no_imagenes_mapache),
                            contentDescription = "No hay amigos",
                            )
                    } else {
                        amigos?.forEach { amigo ->
                            CardAmigos(
                                Nombre = amigo.nombreUsuario,
                                ImageUrl = amigo.foto,
                                idAmigo = amigo.id,
                                idUsuario = userId
                            )
                        }
                    }

                }
            }

        }

    }

}
