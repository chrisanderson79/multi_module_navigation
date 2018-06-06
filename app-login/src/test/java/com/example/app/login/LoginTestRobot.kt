package com.example.app.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.assertj.core.api.Assertions


internal fun test(func: LoginTestRobot.() -> Unit) = LoginTestRobot().apply { func() }
internal fun LoginTestRobot.given(func: LoginTestRobot.() -> Unit) = apply { func() }
internal fun LoginTestRobot.whenever(func: LoginTestRobot.() -> Unit) = apply { func() }
internal fun LoginTestRobot.then(func: LoginTestRobot.() -> Unit) = apply { func() }

internal class LoginTestRobot {

    private val loginActionProcessor: LoginActionProcessor = mock()
    private val loginViewModel: LoginViewModel
    private var state: TestObserver<LoginViewState>

    init {
        loginViewModel = LoginViewModel(loginActionProcessor)
        state = loginViewModel.state().test()
    }

    fun loginResultIsInFlight() {
        com.nhaarman.mockito_kotlin.whenever(loginActionProcessor.mapActionToResult(LoginAction.SubmitAction)).thenReturn(Observable.just(LoginResult.InFlight))
    }

    fun loginResultIsError() {
        com.nhaarman.mockito_kotlin.whenever(loginActionProcessor.mapActionToResult(LoginAction.SubmitAction)).thenReturn(Observable.just(LoginResult.Error))
    }

    fun loginResultIsSuccess() {
        com.nhaarman.mockito_kotlin.whenever(loginActionProcessor.mapActionToResult(LoginAction.SubmitAction)).thenReturn(Observable.just(LoginResult.Success(userName = USER_NAME)))
    }

    fun submitIntentProcessed() {
        loginViewModel.processIntents(Observable.just(LoginIntent.SubmitIntent))
    }

    fun inFlightStateIsEmitted() {
        verify(loginActionProcessor).mapActionToResult(LoginAction.SubmitAction)
        Assertions.assertThat(state.values().size).isEqualTo(2)
        Assertions.assertThat(state.values()[0]).isEqualTo(IDLE_STATE)
        Assertions.assertThat(state.values()[1]).isEqualTo(IN_FLIGHT_STATE)
    }

    fun errorStateIsEmitted() {
        verify(loginActionProcessor).mapActionToResult(LoginAction.SubmitAction)
        Assertions.assertThat(state.values().size).isEqualTo(2)
        Assertions.assertThat(state.values()[0]).isEqualTo(IDLE_STATE)
        Assertions.assertThat(state.values()[1]).isEqualTo(ERROR_STATE)
    }

    fun successStateIsEmitted() {
        verify(loginActionProcessor).mapActionToResult(LoginAction.SubmitAction)
        Assertions.assertThat(state.values().size).isEqualTo(2)
        Assertions.assertThat(state.values()[0]).isEqualTo(IDLE_STATE)
        Assertions.assertThat(state.values()[1]).isEqualTo(SUCCESS_STATE)
    }

    companion object {
        private const val USER_NAME = "TEST"
        private val IDLE_STATE = LoginViewState.idle()
        private val IN_FLIGHT_STATE = LoginViewState.idle().copy(state = LoginState.InFlight)
        private val ERROR_STATE = LoginViewState.idle().copy(state = LoginState.Error)
        private val SUCCESS_STATE = LoginViewState.idle().copy(state = LoginState.Success(userName = USER_NAME))
    }
}