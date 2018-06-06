package com.example.app.application

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ExampleApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder()
                    .build()
}
