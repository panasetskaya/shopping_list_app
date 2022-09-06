package com.example.myshoppinglist.application

import android.app.Application
import com.example.myshoppinglist.di.DaggerAppComponent

class ShoppingApp: Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

}