package com.Tfg.juego.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.theme.JuegoTheme
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CheckBoxLoginRegistro
import com.Tfg.juego.ui.usables.outlinedTextFieldLoginRegistro
import com.Tfg.juego.ui.usables.textoLoginYRegistro
import com.Tfg.juego.ui.usables.textoOscuroLoginRegistro

@Composable
fun menu() {
    var menuInicial by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        if (false) {
            usuarioIniciado()
        } else {
            usuarioSinIniciar({ menuInicial = 1 }, { menuInicial = 2 })
        }

        if (menuInicial == 1) {
            registro( { menuInicial = 0 })
        } else if(menuInicial == 2){
            login( { menuInicial = 0 })
        } else {
            menuInicial()
        }

    }
}

/**
 * Botones de cuando estes sin iniciar
 */
@Composable
fun usuarioSinIniciar(
    botonRegistro: () -> Unit,
    botonLogin: () -> Unit) {

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){

        BotonCustom(
            text = "Registrar",
            width = 170.dp,
            onClick = { botonRegistro() }
        )

        Spacer(modifier = Modifier.width(10.dp))

        BotonCustom(
            text = "Iniciar sesion",
            width = 170.dp,
            onClick = { botonLogin() }
        )

    }

}

/**
 * EL menu inicial con todos los botones
 */
@Composable
fun menuInicial() {
    val image: Painter = painterResource(id = R.drawable.img_iconochatgpt)

    Spacer(modifier = Modifier.height(45.dp))

    Image(
        painter = image,
        contentDescription = "",
        modifier = Modifier
            .size(200.dp)
    )

    Spacer(modifier = Modifier.height(45.dp))

    BotonCustom(
        text = "Unirse a una mesa",
        height = 200.dp,
        width = 300.dp)
    { /* Acción */ }

    Spacer(modifier = Modifier.height(20.dp))

    BotonCustom(
        text = "Wiki",
        height = 60.dp,
        width = 300.dp)
    { /* Acción */ }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BotonCustom(
            text = "Ajustes",
            height = 60.dp,
            width = 142.dp)
        { /* Acción */ }

        Spacer(modifier = Modifier.width(15.dp))

        BotonCustom(
            text = "Sobre nosotros",
            height = 60.dp,
            width = 142.dp)
        { /* Acción */ }
    }
}


/**
 * Este composable es de cuando en el navbar estas con la cuenta iniciada
 */
@Composable
fun usuarioIniciado() {
    val image: Painter = painterResource(id = R.drawable.img_perfil)

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){
        BotonCustom(
            text = "Amigos",
            onClick = { /* Acción */ }
        )

        Spacer(modifier = Modifier.width(120.dp))

        Text("Usuario")

        Spacer(modifier = Modifier.width(10.dp))

        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(44.dp)) // Borde negro
        )
    }
}

@Composable
fun login(
    botonMenu: () -> Unit
) {
    // Estado para guardar lo que el usuario escribe
    val usuario_correo = remember { mutableStateOf("") }
    val contrasenia = remember { mutableStateOf("") }
    val recuerdaCuenta = remember { mutableStateOf(true) }

    Spacer(modifier = Modifier.height(150.dp))

    textoLoginYRegistro("Login")

    Spacer(modifier = Modifier.height(100.dp))

    textoOscuroLoginRegistro("Usuario o Correo")
    outlinedTextFieldLoginRegistro(usuario_correo, "usuario/correo")

    Spacer(modifier = Modifier.height(20.dp))

    textoOscuroLoginRegistro("Contraseña")
    outlinedTextFieldLoginRegistro(contrasenia, "••••••••••")

    val checkedState = remember { mutableStateOf(true) }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(55.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        CheckBoxLoginRegistro(
            recuerdaCuenta,
            "Recordar cuenta"
        )

    }

    BotonCustom(
        text = "Iniciar sesion",
        width = 160.dp,
        height = 50.dp,
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

    Spacer(modifier = Modifier.height(110.dp))

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

    val checkedState = remember { mutableStateOf(true) }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(55.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        CheckBoxLoginRegistro(
            recuerdaCuenta,
            "Recordar cuenta"
        )

    }

    BotonCustom(
        text = "Iniciar sesion",
        width = 160.dp,
        height = 50.dp,
        onClick = { botonMenu() }
    )

}



/**
 * EL PREVIEW PARA VER LAS COSAS
 */
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "es")
@Composable
fun previewAyuda() {
    JuegoTheme {
        menu()
    }
}