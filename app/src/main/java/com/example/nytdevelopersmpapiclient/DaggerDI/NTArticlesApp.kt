package com.example.nytdevelopersmpapiclient.DaggerDI

import android.app.Application
import com.example.nytdevelopersmpapiclient.BuildConfig

class NTArticlesApp : Application() {
    companion object {
        private lateinit var component: AppComponent
        fun getAppComponent(): AppComponent = component
    }

    override fun onCreate() {
        super.onCreate()
        component = initDaggerComponent()
    }

    private fun initDaggerComponent() : AppComponent {
        return DaggerAppComponent.builder().contextModule(ContextModule(this)).build()
    }
}