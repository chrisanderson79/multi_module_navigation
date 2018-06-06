package com.example.app.login

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginAppModule {

    @ContributesAndroidInjector(modules = arrayOf(LoginFragmentModule::class))
    internal abstract fun contributeLoginFragment(): LoginFragment

    @Binds
    internal abstract fun bindLoginService(router: StubLoginService): LoginService

    @Binds
    internal abstract fun bindLoginRouter(router: LoginRouterImpl): LoginRouter
}