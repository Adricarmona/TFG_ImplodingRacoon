package com.Tfg.juego.model.servicios

import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient


suspend fun deleteFriendRequestService(idUsuario: Int, idAmigo: Int): String? {
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)

    try {
        return apiService.deleteFriendRequest(idUsuario,idAmigo)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}