package com.Tfg.juego.model.servicios

import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient
import com.Tfg.juego.model.retrofit.dto.userAmigos

suspend fun getFriendsService(idUsuario: Int): List<userAmigos>? {
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)

    try {
        return apiService.getAmigos(idUsuario)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}