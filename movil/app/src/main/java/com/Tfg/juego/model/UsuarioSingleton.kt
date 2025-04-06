package com.Tfg.juego.model

object UsuarioSingleton {
    private var _user: String? = null

    fun getUser(): String? {
        return _user
    }

    fun setUser(newUser: String) {
        _user = newUser
    }

    fun clearUser() {
        _user = null
    }
}