package com.example.app.login

sealed class LoginAction {
    object SubmitAction : LoginAction()
}