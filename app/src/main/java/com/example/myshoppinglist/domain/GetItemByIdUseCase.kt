package com.example.myshoppinglist.domain

import javax.inject.Inject

class GetItemByIdUseCase @Inject constructor(private val repository: ShopListRepository) {

    suspend fun getItemById(id: Int): ShopItem {
        return repository.getItemById(id)
    }
}