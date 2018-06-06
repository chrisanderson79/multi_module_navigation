package com.example.app.login

import androidx.navigation.NavController
import javax.inject.Inject

class LoginRouterImpl @Inject constructor() : LoginRouter {
    override fun openPayments(navigationController: NavController) {
        navigationController.navigate(LoginFragmentDirections.openPaymentsAction())
    }
}