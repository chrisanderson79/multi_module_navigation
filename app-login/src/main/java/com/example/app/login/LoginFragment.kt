package com.example.app.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.app.common.viewmodel.ViewModelFactory
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var loginRouter: LoginRouter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val navigationController: NavController by lazy { findNavController() }
    private val disposables = CompositeDisposable()
    private lateinit var loginViewModel: LoginViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(LoginViewModel::class.java)

        disposables.add(loginViewModel.state()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processStates))

        loginViewModel.processIntents(intents())
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    private fun intents(): Observable<LoginIntent> =
            RxView.clicks(loginButton).map<LoginIntent> { LoginIntent.SubmitIntent }
                    .mergeWith(RxView.clicks(loginRetryButton).map<LoginIntent> { LoginIntent.RetryIntent })

    private fun processStates(loginViewState: LoginViewState) {
        when (loginViewState.state) {
            LoginState.Idle -> showIdle()
            LoginState.InFlight -> showProgress()
            LoginState.Error -> showError()
            is LoginState.Success -> {
                showSuccess(loginViewState.state.userName)
                openPayments()
            }
        }
    }

    private fun showIdle() {
        loginProgress.visibility = View.GONE
        loginButton.visibility = View.VISIBLE
        loginError.visibility = View.GONE
        loginRetryButton.visibility = View.GONE
    }

    private fun showProgress() {
        loginProgress.visibility = View.VISIBLE
        loginButton.visibility = View.GONE
        loginError.visibility = View.GONE
        loginRetryButton.visibility = View.GONE
    }

    private fun showError() {
        loginProgress.visibility = View.GONE
        loginButton.visibility = View.GONE
        loginError.visibility = View.VISIBLE
        loginRetryButton.visibility = View.VISIBLE
    }

    private fun showSuccess(userName: String) {
        loginProgress.visibility = View.GONE
        loginButton.visibility = View.GONE
        loginError.visibility = View.GONE
        loginRetryButton.visibility = View.GONE

        Toast.makeText(loginProgress.context, "Hello: $userName", Toast.LENGTH_SHORT).show()
    }

    private fun openPayments() {
        loginRouter.openPayments(navigationController)
    }
}