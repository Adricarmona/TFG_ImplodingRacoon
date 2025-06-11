package com.Tfg.juego.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CheckBoxLoginRegistro
import com.Tfg.juego.ui.usables.loguinRegistroArriba
import com.Tfg.juego.ui.usables.outlinedTextFieldLoginRegistro
import com.Tfg.juego.ui.usables.textoLoginYRegistro

@Composable
fun unirMesa(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onAmigosClick: () -> Unit,
    onMenuClick: () -> Unit
)
{
    val codigoMesa = remember { mutableStateOf("") }
    val passwordMesa = remember { mutableStateOf(false) }
    val passwordMesaText = remember { mutableStateOf("") }

    loguinRegistroArriba(onLoginClick, onRegisterClick, onPerfilClick, onAmigosClick)

    Column(
        modifier = Modifier.fillMaxSize()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(180.dp))

        Image(
            painter = painterResource(id = R.drawable.img_work_inprogres),
            contentDescription = "Logo",
            modifier = Modifier.height(180.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        textoLoginYRegistro(
            text = stringResource(R.string.entrar_mesa),
            fontSize = 32,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(90.dp))

        outlinedTextFieldLoginRegistro(
            text = codigoMesa,
            placeholderTexto = stringResource(R.string.codigo_mesa),
        )

        Spacer(modifier = Modifier.height(20.dp))

        CheckBoxLoginRegistro(
            recuerdaCuenta = passwordMesa,
            string = stringResource(R.string.sala_contrasena)
        )

        Spacer(modifier = Modifier.height(20.dp))

        outlinedTextFieldLoginRegistro(
            text = passwordMesaText,
            placeholderTexto = stringResource(R.string.contrasena),
            enabled = passwordMesa.value
        )

        Spacer(modifier = Modifier.height(40.dp))

        BotonCustom(
            text = stringResource(R.string.unirse),
            width = 160.dp,
            height = 50.dp,
            enabled = false
        ) { }

        Spacer(modifier = Modifier.height(24.dp))

        BotonCustom(
            text = stringResource(R.string.volver_menu),
            width = 180.dp,
        ) { onMenuClick() }

    }
}