package com.example.app.login

import io.reactivex.Observable
import javax.inject.Inject

class LoginActionProcessor @Inject constructor(private val loginUseCase: LoginUseCase) {
    internal fun mapActionToResult(action: LoginAction): Observable<LoginResult> {
        return when (action) {
            is LoginAction.SubmitAction -> getUser()
        }
    }

    private fun getUser() : Observable<LoginResult> =
            loginUseCase.getUser()
                    .toObservable()
                    .map<LoginResult> { LoginResult.Success(userName = it.userName) }
                    .onErrorReturn { LoginResult.Error }
                    .startWith(LoginResult.InFlight)
}