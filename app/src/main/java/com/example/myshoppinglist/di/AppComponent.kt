package com.example.myshoppinglist.di

import android.app.Application
import com.example.myshoppinglist.presentation.MainActivity
import com.example.myshoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ShoppingAppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}