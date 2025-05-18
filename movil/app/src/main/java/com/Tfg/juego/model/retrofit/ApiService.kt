package com.Tfg.juego.model.retrofit

import com.Tfg.juego.model.retrofit.dto.cardAndStyle
import com.Tfg.juego.model.retrofit.dto.loginDto
import com.Tfg.juego.model.retrofit.dto.registerDto
import com.Tfg.juego.model.retrofit.dto.responseToken
import com.Tfg.juego.model.retrofit.dto.userAmigos
import com.Tfg.juego.model.retrofit.dto.userPerfil
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("Auth/Login")
    suspend fun getLogin(@Body loginDto: loginDto): responseToken

    @POST("Auth/Register")
    suspend fun getRegister(@Body registerDto: registerDto): responseToken

    @GET("Cards/GetCardByIdImage/type={type}")
    suspend fun getAllCartas(@Path("type") type: Int): List<cardAndStyle>

    @GET("User/GetUserById/{id}")
    suspend fun getUserPerfil(@Path("id") id: Int): userPerfil

    @GET("User/GetFriendsbyUserId/{id}")
    suspend fun getAmigos(@Path("id") id: Int): List<userAmigos>

    @DELETE("User/DeleteFriend/{id}")
    suspend fun deleteAmigo(@Path("id") id: Int, @Query("friendid") idFriend: Int): responseToken

    @GET("User/GetUsersByIdForFriends/{id}")
    suspend fun getFriendsUsers(@Path("id") id: Int): List<userAmigos>

    @GET("User/GetUsersByIdAndNameForFriends/{id}")
    suspend fun getFriendsUsersByName(@Path("id") id: Int, @Query("name") name: String): List<userAmigos>

    @GET("User/SetFriendRequest/")
    suspend fun setFriendRequest(@Query("id") id: Int, @Query("friendId") idFriend: Int): Boolean

    @PUT("User/AcceptFriendRequest")
    suspend fun acceptFriendRequest(@Query("id") id: Int, @Query("friendId") idFriend: Int): Boolean

    @GET("User/GetFriendRequests/{id}")
    suspend fun getFriendRequests(@Path("id") id: Int): List<userAmigos>
}