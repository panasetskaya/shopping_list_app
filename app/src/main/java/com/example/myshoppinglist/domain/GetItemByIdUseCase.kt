package com.example.myshoppinglist.domain

class GetItemByIdUseCase(private val repository: ShopListRepository) {

    fun getItemById(id: Int): ShopItem {
        return repository.getItemById(id)
    }
}