package com.Tfg.juego.ui.usables

import android.widget.Toast
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
import com.Tfg.juego.model.servicios.setFriendRequestService
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
            placeholder = painterResource(R.drawable.img_mapacheicono),
            error = painterResource(R.drawable.img_mapacheicono),
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
                    deleteFriendsService(idUsuario,idAmigo)
                }
            }
        ) {
            Text("eliminar amigo")
        }
    }
}

@Composable
fun CardBuscarAmigos(Nombre: String,ImageUrl: String, idAmigo: Int, idUsuario: Int, context: android.content.Context) {
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
            placeholder = painterResource(R.drawable.img_mapacheicono),
            error = painterResource(R.drawable.img_mapacheicono),
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
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            border = BorderStroke(1.5.dp, Color.Black),
            onClick = {
                coroutineScope.launch {
                    val haFuncionado = setFriendRequestService(idUsuario,idAmigo)

                    if(haFuncionado){
                        Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        ) {
            Text("solicitar amigo")
        }
    }

}


@Composable
fun CardAceptarAmigosRequest(Nombre: String,ImageUrl: String, idAmigo: Int, idUsuario: Int, context: android.content.Context) {
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
            placeholder = painterResource(R.drawable.img_mapacheicono),
            error = painterResource(R.drawable.img_mapacheicono),
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
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            border = BorderStroke(1.5.dp, Color.Black),
            modifier = Modifier.width(95.dp),
            onClick = {
                coroutineScope.launch {
                    val haFuncionado = setFriendRequestService(idUsuario,idAmigo)

                    if(haFuncionado){
                        Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        ) {
            Text("aceptar")
        }
        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            border = BorderStroke(1.5.dp, Color.Black),
            modifier = Modifier.width(100.dp),
            onClick = {
                coroutineScope.launch {
                    val haFuncionado = setFriendRequestService(idUsuario,idAmigo)

                    if(haFuncionado){
                        Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        ) {
            Text("declinar")
        }
    }

}