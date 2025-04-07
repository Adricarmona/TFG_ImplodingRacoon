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

@Composable
fun perfil(irMenu: () -> Unit) {

    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    Column(
        modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                sharedPreferences.edit().putString("token", "").apply()
                irMenu()
                      },
        ) {
            Text("test")
        }

    }

}