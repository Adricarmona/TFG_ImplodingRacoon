package com.Tfg.juego.ui.menu

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.Tfg.juego.model.servicios.login
import com.Tfg.juego.model.servicios.registrer
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CheckBoxLoginRegistro
import com.Tfg.juego.ui.usables.outlinedTextFieldLoginRegistro
import com.Tfg.juego.ui.usables.textoLoginYRegistro
import com.Tfg.juego.ui.usables.textoOscuroLoginRegistro
import kotlinx.coroutines.launch
import java.util.prefs.Preferences


@Composable
fun login(
    botonMenu: () -> Unit
) {
    // Estado para guardar lo que el usuario escribe
    val usuario_correo = remember { mutableStateOf("") }
    val contrasenia = remember { mutableStateOf("") }
    val recuerdaCuenta = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Spacer(modifier = Modifier.height(150.dp))

    textoLoginYRegistro("Login")

    Spacer(modifier = Modifier.height(100.dp))

    textoOscuroLoginRegistro("Usuario o Correo")
    outlinedTextFieldLoginRegistro(usuario_correo, "usuario/correo")

    Spacer(modifier = Modifier.height(20.dp))

    textoOscuroLoginRegistro("Contraseña")
    outlinedTextFieldLoginRegistro(contrasenia, "••••••••••")

    Spacer(modifier = Modifier.height(20.dp))

    BotonCustom(
        text = "Iniciar sesion",
        width = 160.dp,
        height = 50.dp,
        onClick = {
            coroutineScope.launch {

                if (login(usuario_correo.value, contrasenia.value, context) != null){
                    botonMenu()
                }

            }
        }
    )

    Spacer(modifier = Modifier.height(90.dp))

    BotonCustom(
        text = "Volver al menu",
        width = 160.dp,
        height = 40.dp,
        onClick = { botonMenu() }
    )

}

@Composable
fun registro(
    botonMenu: () -> Unit
) {
    // Estado para guardar lo que el usuario escribe
    val usuario = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val contrasenia = remember { mutableStateOf("") }
    val contraseniaRepetida = remember { mutableStateOf("") }
    val recuerdaCuenta = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Spacer(modifier = Modifier.height(70.dp))

    textoLoginYRegistro("Registro")

    Spacer(modifier = Modifier.height(50.dp))

    textoOscuroLoginRegistro("Usuario")
    outlinedTextFieldLoginRegistro(usuario, "usuario")

    Spacer(modifier = Modifier.height(20.dp))

    textoOscuroLoginRegistro("Correo")
    outlinedTextFieldLoginRegistro(correo, "correo")

    Spacer(modifier = Modifier.height(20.dp))

    textoOscuroLoginRegistro("Contraseña")
    outlinedTextFieldLoginRegistro(contrasenia, "••••••••••")

    Spacer(modifier = Modifier.height(20.dp))

    textoOscuroLoginRegistro("Repetir contraseña")
    outlinedTextFieldLoginRegistro(contraseniaRepetida, "••••••••••")

    Spacer(modifier = Modifier.height(20.dp))

    BotonCustom(
        text = "Registrar sesion",
        width = 170.dp,
        height = 50.dp,
        onClick = {

            coroutineScope.launch {

                println(contrasenia.value)
                if (registrer(correo.value, contrasenia.value, usuario.value, context) != null){
                    botonMenu()
                }

            }

        }
    )

    Spacer(modifier = Modifier.height(20.dp))

    BotonCustom(
        text = "Volver al menu",
        width = 160.dp,
        height = 40.dp,
        onClick = { botonMenu() }
    )

}

