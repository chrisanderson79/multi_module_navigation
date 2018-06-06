package com.example.app.login

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModel @Inject constructor(loginActionProcessor: LoginActionProcessor) : ViewModel() {

    private val intentsSubject: PublishSubject<LoginIntent> = PublishSubject.create()
    private val state: Observable<LoginViewState> = compose(intentsSubject, loginActionProcessor)

    fun processIntents(intents: Observable<LoginIntent>) {
        intents.subscribe(intentsSubject)
    }

    fun state(): Observable<LoginViewState> {
        return state
    }

    companion object {

        private fun compose(intentsSubject: Observable<LoginIntent>, loginActionProcessor: LoginActionProcessor): Observable<LoginViewState> {
            return intentsSubject
                    .map(this::actionFromIntent)
                    .flatMap(loginActionProcessor::mapActionToResult)
                    .scan(LoginViewState.idle(), reducer)
                    .replay(1)
                    .autoConnect(0)
        }

        private fun actionFromIntent(intent: LoginIntent): LoginAction {
            return when (intent) {
                LoginIntent.SubmitIntent -> LoginAction.SubmitAction
                LoginIntent.RetryIntent -> LoginAction.SubmitAction
            }
        }

        private val reducer = { previousState: LoginViewState, result: LoginResult ->
            when (result) {
                LoginResult.InFlight -> previousState.copy(state = LoginState.InFlight)
                LoginResult.Error -> previousState.copy(state = LoginState.Error)
                is LoginResult.Success -> previousState.copy(state = LoginState.Success(userName = result.userName))
            }
        }
    }
}