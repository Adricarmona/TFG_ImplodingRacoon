package com.Tfg.juego.ui.menu.amigos

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.model.retrofit.dto.userAmigos
import com.Tfg.juego.model.servicios.decodificarJWT
import com.Tfg.juego.model.servicios.getFriendsAddUser
import com.Tfg.juego.model.servicios.getFriendsService
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CardAmigos
import com.Tfg.juego.ui.usables.CardBuscarAmigos
import kotlinx.coroutines.launch

@Composable
fun buscarAmigos(
    onMenuClick: () -> Unit,
    onMisAmigos: () -> Unit,
    onSolicitudesDeAmistad: () -> Unit
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
                amigos = getFriendsAddUser(userId)
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
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.weight(1f),
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
                            painter = painterResource(R.drawable.img_no_info),
                            contentDescription = "No hay amigos",
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        amigos?.forEach { amigo ->
                            CardBuscarAmigos(
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