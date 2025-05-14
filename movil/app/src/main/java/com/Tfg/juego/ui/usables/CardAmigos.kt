package com.Tfg.juego.ui.usables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Tfg.juego.R
import com.Tfg.juego.model.servicios.deleteFriendsService
import kotlinx.coroutines.launch

@Composable
fun CardAmigos(Nombre: String,ImageUrl: String, idAmigo: Int, idUsuario: Int) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){

        AsyncImage(
            model = ImageUrl,
            contentDescription = "Icono Usuario",
            modifier = Modifier
                .height(40.dp),
            alignment = Alignment.Center,
            placeholder = painterResource(R.drawable.img_perfil),
            error = painterResource(R.drawable.img_perfil),
            onError = { error ->
                println("Error cargando imagen: ${error.result.throwable}")
            },
            onSuccess = { success ->
                println("Imagen cargada con éxito")
            }
        )

        Spacer(modifier = Modifier.width(20.dp))

        textoLoginYRegistro(
            text = Nombre,
            fontSize = 20
        )

        Spacer(modifier = Modifier.width(20.dp))

        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            border = BorderStroke(1.5.dp, Color.Black),
            onClick = {
                coroutineScope.launch {
                    val friends = deleteFriendsService(idUsuario,idAmigo)
                    println("$friends")
                }
            }
        ) {
            Text("eliminar amigo")
        }
    }
}