package com.example.nytdevelopersmpapiclient.DaggerDI

import android.content.Context
import com.example.nytdevelopersmpapiclient.DaggerDI.GlobalModules.ContextModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ContextModule::class])
interface AppComponent {
    fun getContext() : Context
}