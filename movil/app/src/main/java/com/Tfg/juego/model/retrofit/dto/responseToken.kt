package com.Tfg.juego.model.retrofit.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class responseToken(
    @JsonProperty("message")
    val message: String,
    @JsonProperty("code")
    val code: Int
)