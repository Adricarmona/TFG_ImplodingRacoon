package com.Tfg.juego.ui.menu

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Tfg.juego.R
import com.Tfg.juego.ui.theme.JuegoTheme
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.textoLoginYRegistro
import com.auth0.android.jwt.JWT
import androidx.core.content.edit

@Composable
fun perfil(
    onMenuClick: () -> Unit,
    onAmigosClick: () -> Unit
) {
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("token", "")
    val jwt = JWT(token.toString())
    val nombre = jwt.getClaim("name").asString()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // Take remaining space
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {
            AsyncImage(
                model = "https://img.freepik.com/vector-premium/ilustracion-plana-vectorial-profilo-usuario-avatar-imagen-perfil-icono-persona-adecuado-perfiles-redes-sociales-iconos-protectores-pantalla-como-plantillax9xa_719432-1723.jpg",
                contentDescription = "Su imagen de perfil",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(5.dp))

            textoLoginYRegistro(
                text = nombre.toString(),
                fontSize = 32,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(5.dp))

            BotonCustom(
                text = stringResource(R.string.amigos) + ": " + 0,
                onClick = { onAmigosClick() }
            )

            var urlPerfil = "https://www.youtube.com/watch?v=Z7VHSR5Kwd0"

            val annotatedString = buildAnnotatedString {
                append("Visita nuestro ")

                withLink(
                    link = LinkAnnotation.Url(urlPerfil) /// esto hay que ponerlo con el link que toca
                ) {
                    append("no se que poner aqui")
                }
            }

            Text(text = annotatedString)
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
