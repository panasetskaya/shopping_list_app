package com.example.myshoppinglist.domain

class AddShopItemUseCase(private val repository: ShopListRepository) {

    suspend fun addShopItem(item: ShopItem) {
        repository.addShopItem(item)
    }
}