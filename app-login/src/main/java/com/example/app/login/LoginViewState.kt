package com.example.app.login

data class LoginViewState(val state: LoginState) {
    companion object {
        fun idle(): LoginViewState {
            return LoginViewState(state = LoginState.Idle)
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object InFlight : LoginState()
    data class Success(val userName: String) : LoginState()
    object Error : LoginState()
}