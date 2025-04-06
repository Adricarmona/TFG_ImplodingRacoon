package com.Tfg.juego.model.retrofit

import com.Tfg.juego.model.retrofit.dto.loginDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("Auth/Login")
    suspend fun getLogin(@Body loginDto: loginDto): String

}