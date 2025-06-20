package com.Tfg.juego.ui.usables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Tfg.juego.R
import com.Tfg.juego.model.servicios.acceptFriendsRequestService
import com.Tfg.juego.model.servicios.deleteFriendRequestService
import com.Tfg.juego.model.servicios.deleteFriendsService
import com.Tfg.juego.model.servicios.setFriendRequestService
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun CardAmigos(
    Nombre: String,
    ImageUrl: String,
    idAmigo: Int,
    idUsuario: Int,
    context: Context,
    function: () -> Job
) {
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
            }
        )

        Spacer(modifier = Modifier.width(20.dp))

        nombreUsuarios(
            text = Nombre,
            with = 100.dp
        )

        Spacer(modifier = Modifier.width(20.dp))

        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary),
            onClick = {
                coroutineScope.launch {
                    var jsonResultado = deleteFriendsService(idUsuario,idAmigo)

                    if (jsonResultado != null) {
                        if (jsonResultado.message.equals("Amigo eliminado")) {
                            Toast.makeText(context, R.string.amigoEliminado, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, R.string.errorEliminarAmigo, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, R.string.errorEliminarAmigo, Toast.LENGTH_SHORT).show()
                    }

                    function()
                }
            }
        ) {
            Text(stringResource(R.string.eliminarAmigo))
        }
    }
}

@Composable
fun CardBuscarAmigos(Nombre: String,ImageUrl: String, idAmigo: Int, idUsuario: Int, context: android.content.Context) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
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
            }
        )

        Spacer(modifier = Modifier.width(20.dp))

        nombreUsuarios(
            text = Nombre,
            with = 120.dp
        )

        Spacer(modifier = Modifier.width(20.dp))

        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary),
            onClick = {
                coroutineScope.launch {
                    val haFuncionado = setFriendRequestService(idUsuario,idAmigo)

                    if(haFuncionado){
                        Toast.makeText(context, R.string.solicitudEnviada, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, R.string.solicitudEnviada, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        ) {
            Text(
                text = stringResource(R.string.solicitaAmigo),
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }

}


@Composable
fun CardAceptarAmigosRequest(
    Nombre: String,
    ImageUrl: String,
    idAmigo: Int,
    idUsuario: Int,
    context: Context,
    function: () -> Job
) {
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
            }
        )

        Spacer(modifier = Modifier.width(20.dp))

        nombreUsuarios(
            text = Nombre,
            with = 130.dp
        )

        Spacer(modifier = Modifier.width(20.dp))

        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            border = BorderStroke(1.5.dp, Color.Black),
            modifier = Modifier.width(50.dp),
            onClick = {
                coroutineScope.launch {

                    var jsonResultado = acceptFriendsRequestService(idUsuario,idAmigo)

                    if (jsonResultado != null) {
                        if (jsonResultado) {
                            Toast.makeText(context, R.string.solicitudAceptada, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, R.string.solicitudAceptada, Toast.LENGTH_SHORT).show()
                        }
                    }

                    function()
                }
            }
        ) {
            Text("✓")
        }
        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            border = BorderStroke(1.5.dp, Color.Black),
            modifier = Modifier.width(50.dp),
            onClick = {
                coroutineScope.launch {
                    var stringResutlado = deleteFriendRequestService(idUsuario,idAmigo)

                    if (stringResutlado != null) {
                        if (stringResutlado.equals("Solicitud de amistad eliminada")) {
                            Toast.makeText(context, R.string.solicitudEliminada, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, R.string.alEliminarSolicitud, Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(context, R.string.alEliminarSolicitud, Toast.LENGTH_SHORT).show()
                    }

                    function()
                }
            }
        ) {
            Text("✗")
        }
    }

}