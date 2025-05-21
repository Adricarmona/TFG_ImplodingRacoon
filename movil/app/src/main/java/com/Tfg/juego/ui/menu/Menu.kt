package com.Tfg.juego.ui.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.usables.loguinRegistroArriba
import com.Tfg.juego.ui.usables.BotonCustom


/**
 * EL menu inicial con todos los botones
 */
@Composable
fun menuInicial(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onUnirseClick: () -> Unit,
    onAjustesClick: () -> Unit,
    onSobreNosotrosClick: () -> Unit,
    onAmigosClick: () -> Unit,
) {
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)
    val context = LocalContext.current

    loguinRegistroArriba(
        onLoginClick = onLoginClick,
        onRegisterClick = onRegisterClick,
        onPerfilClick = onPerfilClick,
        onAmigosClick = onAmigosClick
        )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(160.dp))

        Image(
            painter =  painterResource(R.drawable.img_imploding_racoons_logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .size(300.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        BotonCustom(
            text = stringResource(R.string.unirseMesa),
            height = 200.dp,
            width = 300.dp,
            enabled = !sharedPreferences.getString("token", "").isNullOrEmpty()
        )
        { onUnirseClick() }

        Spacer(modifier = Modifier.height(20.dp))

        BotonCustom(
            text = stringResource(R.string.wiki),
            height = 60.dp,
            width = 300.dp
        )
        {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2:4200/wiki"))
            context.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotonCustom(
                text = stringResource(R.string.ajustes),
                height = 60.dp,
                width = 142.dp
            )
            { onAjustesClick() }

            Spacer(modifier = Modifier.width(15.dp))

            BotonCustom(
                text = stringResource(R.string.sobre_nosotros),
                height = 60.dp,
                width = 142.dp
            )
            { onSobreNosotrosClick() }
        }
    }
}


