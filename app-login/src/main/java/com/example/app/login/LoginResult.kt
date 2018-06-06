package com.example.app.login

sealed class LoginResult {
    object InFlight : LoginResult()
    data class Success(val userName: String) : LoginResult()
    object Error : LoginResult()
}