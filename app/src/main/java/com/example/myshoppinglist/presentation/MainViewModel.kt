package com.example.myshoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppinglist.domain.DeleteShopItemUseCase
import com.example.myshoppinglist.domain.EditShopItemUseCase
import com.example.myshoppinglist.domain.GetShopListUseCase
import com.example.myshoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(item: ShopItem) {
        viewModelScope.launch { deleteShopItemUseCase.deleteShopItem(item) }
    }

    fun changeIsActiveState(item: ShopItem) {
        val newItem = item.copy(isActive = !item.isActive)
        viewModelScope.launch { editShopItemUseCase.editShopItem(newItem) }
    }

}