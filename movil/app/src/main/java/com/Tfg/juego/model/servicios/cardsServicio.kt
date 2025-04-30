package com.Tfg.juego.model.servicios

import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient
import com.Tfg.juego.model.retrofit.dto.cardAndStyle

suspend fun getCartasTipo(tipo: Int): List<cardAndStyle>? {
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)

    try {
        return apiService.getAllCartas(tipo)
    } catch (e: Exception) {
        e.printStackTrace()
        println("Error en getCartasTipo: ${e.message}")
        println("Causa: ${e.cause}")
        return null
    }
}