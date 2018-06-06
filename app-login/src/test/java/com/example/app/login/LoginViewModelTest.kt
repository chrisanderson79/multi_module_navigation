package com.example.app.login

import org.junit.Test

class LoginViewModelTest {

    @Test
    fun inFlightStateEmitted_whenFailingSubmitIntentProcessed() {
        test {
            given {
                loginResultIsInFlight()
            }
            whenever {
                submitIntentProcessed()
            }
            then {
                inFlightStateIsEmitted()
            }
        }
    }

    @Test
    fun errorStateEmitted_whenFailingSubmitIntentProcessed() {
        test {
            given {
                loginResultIsError()
            }
            whenever {
                submitIntentProcessed()
            }
            then {
                errorStateIsEmitted()
            }
        }
    }

    @Test
    fun successStateEmitted_whenFailingSubmitIntentProcessed() {
        test {
            given {
                loginResultIsSuccess()
            }
            whenever {
                submitIntentProcessed()
            }
            then {
                successStateIsEmitted()
            }
        }
    }
}

