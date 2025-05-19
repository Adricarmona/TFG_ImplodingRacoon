package com.Tfg.juego.model.servicios

import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient
import com.Tfg.juego.model.retrofit.dto.userAmigos

suspend fun getFriendsAddUserAndNAmeService(idUsuario: Int, name: String): List<userAmigos>? {
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)

    try {
        return apiService.getFriendsUsersByName(idUsuario,name)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}