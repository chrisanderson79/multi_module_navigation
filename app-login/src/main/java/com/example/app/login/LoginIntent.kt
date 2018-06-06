package com.example.app.login

sealed class LoginIntent {
    object SubmitIntent : LoginIntent()
    object RetryIntent : LoginIntent()
}