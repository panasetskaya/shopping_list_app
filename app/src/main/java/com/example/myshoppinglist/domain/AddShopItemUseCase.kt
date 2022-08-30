package com.example.myshoppinglist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(private val repository: ShopListRepository) {

    suspend fun addShopItem(item: ShopItem) {
        repository.addShopItem(item)
    }
}