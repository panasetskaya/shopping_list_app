package com.example.myshoppinglist.domain

class AddShopItemUseCase(private val repository: ShopListRepository) {

    fun addShopItem(item: ShopItem) {
        repository.addShopItem(item)
    }
}