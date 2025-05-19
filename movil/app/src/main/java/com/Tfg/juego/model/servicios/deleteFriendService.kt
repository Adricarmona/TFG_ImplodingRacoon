package com.Tfg.juego.model.servicios

import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient
import com.Tfg.juego.model.retrofit.dto.responseToken

suspend fun deleteFriendsService(idUsuario: Int, idFriend: Int): responseToken? {
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)

    try {
        return apiService.deleteAmigo(idUsuario,idFriend)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}