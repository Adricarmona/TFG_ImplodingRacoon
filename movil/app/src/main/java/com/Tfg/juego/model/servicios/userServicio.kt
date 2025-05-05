package com.Tfg.juego.model.servicios

import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient
import com.Tfg.juego.model.retrofit.dto.userPerfil

suspend fun getDatosPerfil(id: Int): userPerfil? {
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)

    try {
        return apiService.getUserPerfil(id)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}