package com.example.app.application

import com.example.app.login.LoginAppModule
import com.example.app.main.MainActivityModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        MainActivityModule::class,
        LoginAppModule::class))
interface ApplicationComponent : AndroidInjector<ExampleApplication>