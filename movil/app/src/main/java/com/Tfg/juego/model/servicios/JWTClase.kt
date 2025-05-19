package com.Tfg.juego.model.servicios

import com.auth0.android.jwt.JWT

fun decodificarJWT(token: String): JWT? {
    try {
        return JWT(token)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}