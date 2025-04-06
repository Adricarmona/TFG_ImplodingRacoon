package com.Tfg.juego.model.servicios

import android.util.Log
import com.Tfg.juego.model.UsuarioSingleton
import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient
import com.Tfg.juego.model.retrofit.dto.loginDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun login(emailOrUser: String, password: String){
    val loginDto = loginDto(emailOrUser, password)
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)
    var token: String? = null
    CoroutineScope(Dispatchers.IO).launch {
        try {
            token = apiService.getLogin(loginDto)
            UsuarioSingleton.setUser(token)
            println(token)
        } catch (e : Exception){
            Log.e("Error login", "Error login servicio: "+e.message )
        }

    }
}