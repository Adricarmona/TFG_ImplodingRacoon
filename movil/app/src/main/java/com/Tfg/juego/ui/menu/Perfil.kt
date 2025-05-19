package com.Tfg.juego.ui.menu

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Tfg.juego.R
import com.Tfg.juego.model.retrofit.dto.userPerfil
import com.Tfg.juego.model.servicios.getDatosPerfil
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.textoLoginYRegistro
import androidx.core.content.edit
import com.Tfg.juego.model.servicios.decodificarJWT
import kotlinx.coroutines.launch

@Composable
fun perfil(
    onMenuClick: () -> Unit,
    onAmigosClick: () -> Unit
) {
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("token", "")

    val jwt = decodificarJWT(token.toString())
    val userId = jwt?.getClaim("id")?.asInt() ?: 1
    val nombre = jwt?.getClaim("name")?.asString() ?: "Usuario"

    // Estados locales
    var perfil by remember { mutableStateOf<userPerfil?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Coroutine scope para la petición
    val coroutineScope = rememberCoroutineScope()

    // Ejecutar la petición al iniciar
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                perfil = getDatosPerfil(userId)
                error = null
            } catch (e: Exception) {
                error = "Error al cargar el perfil: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
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
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    BotonCustom(
                        text = "Reintentar",
                        onClick = {
                            isLoading = true
                            error = null
                            coroutineScope.launch {
                                try {
                                    perfil = getDatosPerfil(userId)
                                    error = null
                                } catch (e: Exception) {
                                    error = "Error al cargar el perfil: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = perfil?.urlFoto ?: R.drawable.img_perfil,
                        contentDescription = stringResource(R.string.suImagenPerfil),
                        modifier = Modifier.size(200.dp),
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    textoLoginYRegistro(
                        text = perfil?.nombreUsuario ?: nombre,
                        fontSize = 32,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    BotonCustom(
                        text = stringResource(R.string.amigos) + ": " + (perfil?.cantidadAmigos ?: "0"),
                        onClick = { onAmigosClick() }
                    )

                    val urlPerfil = "https://www.youtube.com/watch?v=Z7VHSR5Kwd0" // Cambia por la URL real
                    val annotatedString = buildAnnotatedString {
                        withLink(
                            link = LinkAnnotation.Url(urlPerfil),
                        ) {
                            append(stringResource(R.string.modificarPerfilWeb))
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = annotatedString,
                        style = TextStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        ),
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BotonCustom(
                text = stringResource(R.string.desloguear),
                onClick = {
                    sharedPreferences.edit { putString("token", "") }
                    onMenuClick()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BotonCustom(
                text = stringResource(R.string.volver_menu),
                width = 200.dp,
                onClick = { onMenuClick() }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}