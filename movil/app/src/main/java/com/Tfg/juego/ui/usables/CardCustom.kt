package com.Tfg.juego.ui.usables

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.Tfg.juego.R
import com.Tfg.juego.ui.menu.cartas.verCartas
import com.Tfg.juego.ui.theme.JuegoTheme


@Composable
fun CardCustom(Titulo: String, Subtitulo: String, Descripcion: String, ImageUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Título superior
            Text(
                text = Titulo,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Imagen
            AsyncImage(
                model = ImageUrl,
                contentDescription = "Imagen de la tarjeta",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                alignment = Alignment.Center,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.img_no_imagenes_mapache),
                onError = { error ->
                    println("Error cargando imagen: ${error.result.throwable}")
                },
                onSuccess = { success ->
                    println("Imagen cargada con éxito")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Título inferior
            Text(
                text = Subtitulo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descripción
            Text(
                text = Descripcion,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        }
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
        CardCustom("Titulo", "Subtitulo","", "Descripcion")
    }
}