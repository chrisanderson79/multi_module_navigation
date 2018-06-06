package com.example.app.login

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginService: LoginService) {
    fun getUser() : Single<User> {
        return loginService.getUser()
                .subscribeOn(Schedulers.io())
                .map(userResponseMapper)
    }

    companion object {
        private val userResponseMapper = { userResponse: UserResponse ->
            User(userName = userResponse.userName)
        }
    }
}

data class User(val userName: String)