package com.e.aplikacjaznanylekarz.Models

import java.io.Serializable

data class LoginResponse(
    var patient: Patient,
    var isCorrect: Boolean):Serializable {

}