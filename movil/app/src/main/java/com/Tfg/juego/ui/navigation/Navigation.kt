package com.Tfg.juego.ui.navigation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Tfg.juego.R
import com.Tfg.juego.ui.menu.ajustes
import com.Tfg.juego.ui.menu.cartas.verCartas
import com.Tfg.juego.ui.menu.login
import com.Tfg.juego.ui.menu.menuInicial
import com.Tfg.juego.ui.menu.perfil
import com.Tfg.juego.ui.menu.registro
import com.Tfg.juego.ui.menu.sobreNosotros
import com.Tfg.juego.ui.menu.unirMesa
import com.Tfg.juego.ui.theme.JuegoTheme

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            menuInicial(
                onMenuClick = { navController.navigate("menu") },
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") },
                onPerfilClick = { navController.navigate("perfil") },
                onUnirseClick = { navController.navigate("unirseMesa") },
                onAjustesClick = { navController.navigate("ajustes") },
                onSobreNosotrosClick = { navController.navigate("sobreNosotros") }
            )
        }
        composable("login") {
            login(
                onMenuClick = { navController.navigate("menu") },
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") }
            )
        }
        composable("registro") {
            registro(
                onMenuClick = { navController.navigate("menu") },
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") }
            )
        }
        composable("perfil") {
            perfil(
                onMenuClick = { navController.navigate("menu") },
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") }
            )
        }
        composable("unirseMesa") {
            unirMesa(
                onMenuClick = { navController.navigate("menu") },
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") },
                onPerfilClick = { navController.navigate("perfil") },
                )
        }
        composable("ajustes") {
            ajustes(
                onMenuClick = { navController.navigate("menu") },
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") },
                onPerfilClick = { navController.navigate("perfil") },
                onVerCartas = { navController.navigate("verCartas") }
            )
        }
        composable("verCartas") {
            verCartas(
                onAjustes = { navController.navigate("ajustes") },
            )
        }
        composable("sobreNosotros") {
            sobreNosotros(
                onMenuClick = { navController.navigate("menu") },
            )
        }
    }
}

@Composable
fun menu() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.img_fondo), // Reemplaza con tu imagen
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Navigation()
        }
    }
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