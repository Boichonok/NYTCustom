package com.example.nytdevelopersmpapiclient.DaggerDI

import android.content.Context
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ContextModule::class])
interface AppComponent {
    fun getContext() : Context
}