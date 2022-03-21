package com.example.myshoppinglist.domain

interface ShopListRepository {

    fun getItemById(id: Int): ShopItem

    fun getShopList(): List<ShopItem>

    fun addShopItem(item: ShopItem)

    fun deleteShopItem(item: ShopItem)

    fun editShopItem(item: ShopItem)

}