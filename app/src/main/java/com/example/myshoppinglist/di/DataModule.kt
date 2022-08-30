package com.example.myshoppinglist.di

import android.app.Application
import com.example.myshoppinglist.data.AppDatabase
import com.example.myshoppinglist.data.ShopListDao
import com.example.myshoppinglist.data.ShopListRepositoryImpl
import com.example.myshoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ShoppingAppScope
    @Binds
    fun bindRepo(repoImpl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ShoppingAppScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }

    }
}