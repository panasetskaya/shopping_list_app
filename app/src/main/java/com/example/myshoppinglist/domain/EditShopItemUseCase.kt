package com.example.myshoppinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val repository: ShopListRepository) {

    suspend fun editShopItem(item: ShopItem) {
        repository.editShopItem(item)
    }
}