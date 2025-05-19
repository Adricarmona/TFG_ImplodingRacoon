package com.Tfg.juego.model.servicios

import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient
import com.Tfg.juego.model.retrofit.dto.userAmigos

suspend fun setFriendRequestService(idUsuario: Int, idAmigo: Int): Boolean {
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)

    try {
        return apiService.setFriendRequest(idUsuario,idAmigo)
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}