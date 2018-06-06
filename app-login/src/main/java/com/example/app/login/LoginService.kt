package com.example.app.login

import io.reactivex.Single

interface LoginService {
    fun getUser() : Single<UserResponse>
}

data class UserResponse(val userName: String)