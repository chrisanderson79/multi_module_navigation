package com.example.app.login

import io.reactivex.Single
import javax.inject.Inject

class StubLoginService @Inject constructor() : LoginService {
    override fun getUser(): Single<UserResponse> {
        return Single.fromCallable<UserResponse> {

            Thread.sleep(1000L * 5)
            //throw IOException()

            UserResponse(userName = "TEST")
        }
    }
}