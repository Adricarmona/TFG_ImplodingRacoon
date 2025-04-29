package com.Tfg.juego.ui.menu

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.Tfg.juego.model.servicios.login
import com.Tfg.juego.model.servicios.registrer
import com.Tfg.juego.model.webSockets.MyWebSocketListener
import com.Tfg.juego.model.webSockets.WebSocketManager
import com.Tfg.juego.ui.usables.loguinRegistroArriba
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.dialogoCargando
import com.Tfg.juego.ui.usables.outlinedTextFieldLoginRegistro
import com.Tfg.juego.ui.usables.textoLoginYRegistro
import com.Tfg.juego.ui.usables.textoOscuroLoginRegistro
import com.Tfg.juego.ui.usables.validarEmail
import kotlinx.coroutines.launch


@Composable
fun login(
    onLoginClick: () -> Unit,
    onMenuClick: () -> Unit,
    onRegisterClick: () -> Unit) {
    // Estado para guardar lo que el usuario escribe
    val usuario_correo = remember { mutableStateOf("") }
    val contrasenia = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // ws
    val webSocketManager = remember { WebSocketManager() }
    var message by remember { mutableStateOf("") }
    var receivedMessages by remember { mutableStateOf(listOf<String>()) }
    val listener = remember {
        MyWebSocketListener { msg ->
            receivedMessages = receivedMessages + msg
        }
    }


    // Dialogo de carga
    var showDialog = remember { mutableStateOf(false) }
    dialogoCargando(showDialog)

    loguinRegistroArriba(onLoginClick, onRegisterClick, onRegisterClick, onMenuClick)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(150.dp))

        textoLoginYRegistro("Login")

        Spacer(modifier = Modifier.height(100.dp))

        textoOscuroLoginRegistro("Usuario o Correo")
        outlinedTextFieldLoginRegistro(usuario_correo, "usuario/correo", "")

        Spacer(modifier = Modifier.height(20.dp))

        textoOscuroLoginRegistro("Contraseña")
        outlinedTextFieldLoginRegistro(contrasenia, "••••••••••", "password")

        Spacer(modifier = Modifier.height(20.dp))

        BotonCustom(
            text = "Iniciar sesion",
            width = 160.dp,
            height = 50.dp,
            onClick = {
                coroutineScope.launch {

                    if (  usuario_correo.value.isEmpty() || contrasenia.value.isEmpty() ) {

                        Toast.makeText(
                            context,
                            "Rellena todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        showDialog.value = true
                        val token = login(usuario_correo.value, contrasenia.value, context)

                        if ( token == "Incorrect username or password") {

                            showDialog.value = false
                            Toast.makeText(
                                context,
                                "Usuario o contraseña incorrectos",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else if (token == null) {

                            showDialog.value = false
                            Toast.makeText(
                                context,
                                "Error en el servidor",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            showDialog.value = false
                            webSocketManager.connect("wss://10.0.2.2:7089/WebSocket", listener)
                            webSocketManager.send("{\"TypeMessage\": \"join\",\"Identifier\": \"1\",\"Identifier2\": \"1\"}")
                            onMenuClick()
                        }

                    }

                }
            }
        )

        Spacer(modifier = Modifier.height(90.dp))

        BotonCustom(
            text = "Volver al menu",
            width = 160.dp,
            height = 40.dp,
            onClick = { onMenuClick() }
        )
    }

}

@Composable
fun registro(
    onMenuClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit) {
    // Estado para guardar lo que el usuario escribe
    val usuario = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val contrasenia = remember { mutableStateOf("") }
    val contraseniaRepetida = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Dialogo de carga
    var showDialog = remember { mutableStateOf(false) }
    dialogoCargando(showDialog)

    loguinRegistroArriba(onLoginClick, onRegisterClick, onRegisterClick, onMenuClick)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(150.dp))

        textoLoginYRegistro("Registro")

        Spacer(modifier = Modifier.height(50.dp))

        textoOscuroLoginRegistro("Usuario")
        outlinedTextFieldLoginRegistro(usuario, "usuario", "")

        Spacer(modifier = Modifier.height(20.dp))

        textoOscuroLoginRegistro("Correo")
        outlinedTextFieldLoginRegistro(correo, "correo", "")

        Spacer(modifier = Modifier.height(20.dp))

        textoOscuroLoginRegistro("Contraseña")
        outlinedTextFieldLoginRegistro(contrasenia, "••••••••••", "password")

        Spacer(modifier = Modifier.height(20.dp))

        textoOscuroLoginRegistro("Repetir contraseña")
        outlinedTextFieldLoginRegistro(contraseniaRepetida, "••••••••••", "password")

        Spacer(modifier = Modifier.height(20.dp))

        BotonCustom(
            text = "Registrar sesion",
            width = 170.dp,
            height = 50.dp,
            onClick = {

                coroutineScope.launch {

                    showDialog.value = true

                    if (usuario.value.isEmpty() || correo.value.isEmpty() || contrasenia.value.isEmpty() || contraseniaRepetida.value.isEmpty()) {
                        showDialog.value = false
                        Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT)
                            .show()

                    } else if (validarEmail(correo.value) == false) {
                        showDialog.value = false
                        Toast.makeText(context, "El correo no es valido", Toast.LENGTH_SHORT).show()

                    } else if (contrasenia.value != contraseniaRepetida.value) {
                        showDialog.value = false
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                            .show()

                    } else {

                        val token = registrer(correo.value, contrasenia.value, usuario.value, context)

                        if ( token != null && !token.equals("Error generating user")) {
                            showDialog.value = false
                            onMenuClick()

                        } else if(token.equals("Error generating user")) {
                            showDialog.value = false
                            Toast.makeText(context, "Usuario ya existente", Toast.LENGTH_SHORT)
                                .show()

                        } else {
                            showDialog.value = false
                            Toast.makeText(context, "Error al registrarse", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                }
            })

        Spacer(modifier = Modifier.height(20.dp))

        BotonCustom(
            text = "Volver al menu",
            width = 160.dp,
            height = 40.dp,
            onClick = { onMenuClick() }
        )
    }
}

