package com.example.myshoppinglist.domain

class EditShopItemUseCase(private val repository: ShopListRepository) {

    suspend fun editShopItem(item: ShopItem) {
        repository.editShopItem(item)
    }
}