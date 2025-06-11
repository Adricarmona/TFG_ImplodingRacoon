package com.Tfg.juego.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Tfg.juego.R
import com.Tfg.juego.ui.menu.ajustes
import com.Tfg.juego.ui.menu.amigos.amigos
import com.Tfg.juego.ui.menu.amigos.buscarAmigos
import com.Tfg.juego.ui.menu.amigos.solicitudesAmistad
import com.Tfg.juego.ui.menu.cartas.verCartas
import com.Tfg.juego.ui.menu.login
import com.Tfg.juego.ui.menu.menuInicial
import com.Tfg.juego.ui.menu.perfil
import com.Tfg.juego.ui.menu.registro
import com.Tfg.juego.ui.menu.sobreNosotros
import com.Tfg.juego.ui.menu.unirMesa

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            menuInicial(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") },
                onPerfilClick = { navController.navigate("perfil") },
                onUnirseClick = { navController.navigate("unirseMesa") },
                onAjustesClick = { navController.navigate("ajustes") },
                onSobreNosotrosClick = { navController.navigate("sobreNosotros") },
                onAmigosClick = { navController.navigate("amigos") }
            )
        }
        composable("login") {
            login(
                onMenuClick = { navController.navigate("menu") },
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") },
                onAmigosClick = { navController.navigate("amigos") }
            )
        }
        composable("registro") {
            registro(
                onMenuClick = { navController.navigate("menu") },
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") },
                onAmigosClick = { navController.navigate("amigos") }
            )
        }
        composable("perfil") {
            perfil(
                onMenuClick = { navController.navigate("menu") },
                onAmigosClick = { navController.navigate("amigos") }
            )
        }
        composable("unirseMesa") {
            unirMesa(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") },
                onPerfilClick = { navController.navigate("perfil") },
                onAmigosClick = { navController.navigate("amigos") },
                onMenuClick = { navController.navigate("menu")}
            )
        }
        composable("ajustes") {
            ajustes(
                onMenuClick = { navController.navigate("menu") },
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
        composable("amigos") {
            amigos(
                onMenuClick = { navController.navigate("menu") },
                onBuscarAmigos = { navController.navigate("buscarAmigos") },
                onSolicitudesDeAmistad = { navController.navigate("solicitudesAmistad") }
            )
        }
        composable("buscarAmigos") {
            buscarAmigos(
                onMenuClick = { navController.navigate("menu") },
                onMisAmigos = { navController.navigate("amigos") },
                onSolicitudesDeAmistad = { navController.navigate("solicitudesAmistad") }
            )
        }
        composable("solicitudesAmistad") {
            solicitudesAmistad(
                onMenuClick = { navController.navigate("menu") },
                onMisAmigos = { navController.navigate("amigos") },
                onBuscarAmigos = { navController.navigate("buscarAmigos") }
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

