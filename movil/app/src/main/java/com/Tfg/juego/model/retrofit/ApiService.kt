package com.Tfg.juego.model.retrofit

import com.Tfg.juego.model.retrofit.dto.loginDto
import com.Tfg.juego.model.retrofit.dto.registerDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("Auth/Login")
    suspend fun getLogin(@Body loginDto: loginDto): ResponseBody

    @POST("Auth/Register")
    suspend fun getRegister(@Body registerDto: registerDto): ResponseBody

}