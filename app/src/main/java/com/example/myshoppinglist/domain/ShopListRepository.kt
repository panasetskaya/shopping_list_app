package com.example.myshoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun getItemById(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun addShopItem(item: ShopItem)

    suspend fun deleteShopItem(item: ShopItem)

    suspend fun editShopItem(item: ShopItem)

}