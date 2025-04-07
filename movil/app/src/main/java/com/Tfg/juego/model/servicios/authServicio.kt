package com.Tfg.juego.model.servicios

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.Tfg.juego.model.retrofit.ApiService
import com.Tfg.juego.model.retrofit.RetrofitClient
import com.Tfg.juego.model.retrofit.dto.loginDto
import com.Tfg.juego.model.retrofit.dto.registerDto
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.prefs.Preferences

suspend fun login(emailOrUser: String, password: String, context: Context): String? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val loginDto = loginDto(emailOrUser, password)
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)
    var token: String? = null

        try {

            val respuesta = apiService.getLogin(loginDto)
            token = respuesta.string()
            sharedPreferences.edit().putString("token", token).apply()

            return token

        } catch (e : Exception){

            sharedPreferences.edit().putString("token", "").apply()
            Log.e("Error login", "Error login servicio: "+e.message )
            return null

        }

}

suspend fun registrer(email: String, password: String, user: String, context: Context): String? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val registerDto = registerDto(user, email, password)
    val apiService = RetrofitClient.getClient().create(ApiService::class.java)
    var token: String? = null

    try {

        val respuesta = apiService.getRegister(registerDto)
        token = respuesta.string()
        sharedPreferences.edit().putString("token", token).apply()
        return token

    } catch (e : Exception){

        Log.e("Error registro", "Error registro servicio: "+e.message )
        return null

    }

}