package com.Tfg.juego.ui.menu

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
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
    onRegisterClick: () -> Unit,
    onAmigosClick: () -> Unit
) {
    // Estado para guardar lo que el usuario escribe
    val usuario_correo = remember { mutableStateOf("") }
    val contrasenia = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // ws
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)
    val webSocketManager = remember { WebSocketManager() }
    var message by remember { mutableStateOf("") }
    var receivedMessages by remember { mutableStateOf(listOf<String>()) }
    val listener = remember {
        MyWebSocketListener { msg ->
            receivedMessages = receivedMessages + msg
            Log.d("Mensaje", receivedMessages.toString())
        }
    }


    // Dialogo de carga
    var showDialog = remember { mutableStateOf(false) }
    dialogoCargando(showDialog)

    loguinRegistroArriba(onLoginClick, onRegisterClick, onRegisterClick, onAmigosClick)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(150.dp))

        textoLoginYRegistro(stringResource(R.string.login))

        Spacer(modifier = Modifier.height(100.dp))

        textoOscuroLoginRegistro(stringResource(R.string.usuario_correo))
        outlinedTextFieldLoginRegistro(usuario_correo, stringResource(R.string.usuario_barra_correo), "")

        Spacer(modifier = Modifier.height(20.dp))

        textoOscuroLoginRegistro(stringResource(R.string.contrasena))
        outlinedTextFieldLoginRegistro(contrasenia, "••••••••••", "password")

        Spacer(modifier = Modifier.height(20.dp))

        var rellenarCampos = stringResource(R.string.rellena_todos_campos)
        var incorrectoUsuarioContrasenia = stringResource(R.string.usuario_contrasena_incorrectos)
        var errrorServidor = stringResource(R.string.error_servidor)
        BotonCustom(
            text = stringResource(R.string.iniciar_sesion),
            width = 160.dp,
            height = 50.dp,
            onClick = {
                coroutineScope.launch {

                    if (  usuario_correo.value.isEmpty() || contrasenia.value.isEmpty() ) {

                        Toast.makeText(
                            context,
                            rellenarCampos,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        showDialog.value = true
                        val token = login(usuario_correo.value, contrasenia.value, context)

                        if ( token == "Incorrect username or password") {

                            showDialog.value = false
                            Toast.makeText(
                                context,
                                incorrectoUsuarioContrasenia,
                                Toast.LENGTH_SHORT
                            ).show()

                        } else if (token == null) {

                            showDialog.value = false
                            Toast.makeText(
                                context,
                                errrorServidor,
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            showDialog.value = false
                            webSocketManager.connect(sharedPreferences.getString("baseUrl", "") + "WebSocket", listener)
                            //webSocketManager.send("{\"TypeMessage\": \"join\",\"Identifier\": \"1\",\"Identifier2\": \"1\"}")
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
    onRegisterClick: () -> Unit,
    onAmigosClick: () -> Unit
) {
    // Estado para guardar lo que el usuario escribe
    val usuario = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val contrasenia = remember { mutableStateOf("") }
    val contraseniaRepetida = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // WebSockets
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)
    val webSocketManager = remember { WebSocketManager() }
    var receivedMessages by remember { mutableStateOf(listOf<String>()) }
    val listener = remember {
        MyWebSocketListener { msg ->
            receivedMessages = receivedMessages + msg
            Log.d("Mensaje", receivedMessages.toString())
        }
    }


    // Dialogo de carga
    var showDialog = remember { mutableStateOf(false) }
    dialogoCargando(showDialog)

    loguinRegistroArriba(onLoginClick, onRegisterClick, onRegisterClick, onAmigosClick)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(150.dp))

        textoLoginYRegistro(stringResource(R.string.registrar))

        Spacer(modifier = Modifier.height(50.dp))

        textoOscuroLoginRegistro(stringResource(R.string.usuario))
        outlinedTextFieldLoginRegistro(usuario, stringResource(R.string.usuario), "")

        Spacer(modifier = Modifier.height(20.dp))

        textoOscuroLoginRegistro(stringResource(R.string.correo))
        outlinedTextFieldLoginRegistro(correo, stringResource(R.string.correo), "")

        Spacer(modifier = Modifier.height(20.dp))

        textoOscuroLoginRegistro(stringResource(R.string.contrasena))
        outlinedTextFieldLoginRegistro(contrasenia, "••••••••••", "password")

        Spacer(modifier = Modifier.height(20.dp))

        textoOscuroLoginRegistro(stringResource(R.string.repetir_contrasena))
        outlinedTextFieldLoginRegistro(contraseniaRepetida, "••••••••••", "password")

        Spacer(modifier = Modifier.height(20.dp))

        var rellenarCampos = stringResource(R.string.rellena_todos_campos)
        var correoNoValido = stringResource(R.string.correo_no_valido)
        var contrasniasNoValidas = stringResource(R.string.contrasenas_no_coinciden)
        var usuario_existente = stringResource(R.string.usuario_existente)
        var error_registrarse = stringResource(R.string.error_registrarse)
        BotonCustom(
            text = stringResource(R.string.registrar),
            width = 170.dp,
            height = 50.dp,
            onClick = {

                coroutineScope.launch {

                    showDialog.value = true

                    if (usuario.value.isEmpty() || correo.value.isEmpty() || contrasenia.value.isEmpty() || contraseniaRepetida.value.isEmpty()) {
                        showDialog.value = false
                        Toast.makeText(context, rellenarCampos, Toast.LENGTH_SHORT)
                            .show()

                    } else if (validarEmail(correo.value) == false) {
                        showDialog.value = false
                        Toast.makeText(context, correoNoValido, Toast.LENGTH_SHORT).show()

                    } else if (contrasenia.value != contraseniaRepetida.value) {
                        showDialog.value = false
                        Toast.makeText(context, contrasniasNoValidas, Toast.LENGTH_SHORT)
                            .show()

                    } else {

                        val token = registrer(correo.value, contrasenia.value, usuario.value, context)

                        if ( token != null && !token.equals("Error generating user")) {
                            showDialog.value = false
                            webSocketManager.connect(sharedPreferences.getString("baseUrl", "") + "WebSocket", listener)
                            onMenuClick()

                        } else if(token.equals("Error generating user")) {
                            showDialog.value = false
                            Toast.makeText(context, usuario_existente, Toast.LENGTH_SHORT)
                                .show()

                        } else {
                            showDialog.value = false
                            Toast.makeText(context, error_registrarse, Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                }
            })

        Spacer(modifier = Modifier.height(20.dp))

        BotonCustom(
            text = stringResource(R.string.volver_menu),
            width = 160.dp,
            height = 40.dp,
            onClick = { onMenuClick() }
        )
    }
}

