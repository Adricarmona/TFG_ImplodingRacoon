package com.Tfg.juego.ui.menu

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.Tfg.juego.ui.menu.componentes.loguinRegistroArriba

@Composable
fun perfil(
    onMenuClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit) {

    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("tokenusuario", Context.MODE_PRIVATE)

    loguinRegistroArriba(onLoginClick, onRegisterClick, onRegisterClick, onMenuClick)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(140.dp))

        Button(
            onClick = {
                sharedPreferences.edit().putString("token", "").apply()
                onMenuClick()
                      },
        ) {
            Text("desloguearte")
        }

        Button(
            onClick = {
                onMenuClick()
            },
        ) {
            Text("salir al menu")
        }

    }

}