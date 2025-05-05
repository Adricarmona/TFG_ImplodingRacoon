package com.Tfg.juego.model.retrofit

import com.Tfg.juego.model.retrofit.dto.cardAndStyle
import com.Tfg.juego.model.retrofit.dto.loginDto
import com.Tfg.juego.model.retrofit.dto.registerDto
import com.Tfg.juego.model.retrofit.dto.responseToken
import com.Tfg.juego.model.retrofit.dto.userPerfil
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("Auth/Login")
    suspend fun getLogin(@Body loginDto: loginDto): responseToken

    @POST("Auth/Register")
    suspend fun getRegister(@Body registerDto: registerDto): responseToken

    @GET("Cards/GetCardByIdImage/type={type}")
    suspend fun getAllCartas(@Path("type") type: Int): List<cardAndStyle>

    @GET("User/GetUserById/id={id}")
    suspend fun getUserPerfil(@Path("id") id: Int): userPerfil
}