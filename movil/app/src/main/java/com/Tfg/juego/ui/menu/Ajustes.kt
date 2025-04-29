package com.Tfg.juego.ui.menu

import androidx.compose.runtime.Composable
import com.Tfg.juego.ui.usables.loguinRegistroArriba

@Composable
fun ajustes(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onMenuClick: () -> Unit
){
    loguinRegistroArriba(
        onLoginClick = onLoginClick,
        onRegisterClick = onRegisterClick,
        onPerfilClick = onPerfilClick,
        onMenuClick = onMenuClick
    )


}