package com.example.myshoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.myshoppinglist.data.ShopListRepositoryImpl
import com.example.myshoppinglist.domain.DeleteShopItemUseCase
import com.example.myshoppinglist.domain.EditShopItemUseCase
import com.example.myshoppinglist.domain.GetShopListUseCase
import com.example.myshoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository =
        ShopListRepositoryImpl //Нарушение: зависим от дата-слоя! Нужна инъекция зависимостей. Потом

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(item: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(item)
    }

    fun changeIsActiveState(item: ShopItem) {
        val newItem = item.copy(isActive = !item.isActive)
        editShopItemUseCase.editShopItem(newItem)
    }

}