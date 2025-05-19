package com.Tfg.juego.ui.menu.amigos

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.model.retrofit.dto.userAmigos
import com.Tfg.juego.model.servicios.decodificarJWT
import com.Tfg.juego.model.servicios.getFriendsAddUserAndNAmeService
import com.Tfg.juego.model.servicios.getFriendsAddUserService
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CardBuscarAmigos
import com.Tfg.juego.ui.usables.outlinedTextFieldLoginRegistro
import com.Tfg.juego.ui.usables.textoLoginYRegistro
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
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val buscadorAmigos = remember { mutableStateOf("") }




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

        textoLoginYRegistro(
            text = "Buscar amigos",
            fontSize = 40
        )

        Row(
            Modifier.padding(16.dp)
        ){
            outlinedTextFieldLoginRegistro(
                width = 250.dp,
                text = buscadorAmigos,
                placeholderTexto = "nombre amigo",
            )

            Spacer(modifier = Modifier.width(5.dp))

            BotonCustom(
                text = "Buscar",
                width = 250.dp
            ) {
                if (buscadorAmigos.value.isEmpty()) {
                    coroutineScope.launch {
                        isLoading = true
                        amigos = getFriendsAddUserService(userId)
                        isLoading = false
                    }
                } else {
                    coroutineScope.launch {
                        isLoading = true
                        amigos = getFriendsAddUserAndNAmeService(userId,buscadorAmigos.value)
                        isLoading = false
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.height(50.dp)
                            .width(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black
                        )
                    }
                }
                else -> {

                    if (amigos.isNullOrEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.img_no_info),
                            contentDescription = "No hay amigos",
                            modifier = Modifier.height(150.dp)
                                .width(150.dp)
                        )
                    } else {
                        amigos?.forEach { amigo ->
                            CardBuscarAmigos(
                                Nombre = amigo.nombreUsuario,
                                ImageUrl = amigo.foto,
                                idAmigo = amigo.id,
                                idUsuario = userId,
                                context = LocalContext.current
                            )
                        }
                    }

                }
            }
        }
    }
}