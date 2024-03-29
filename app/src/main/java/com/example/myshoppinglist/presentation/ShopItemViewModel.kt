package com.example.myshoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppinglist.domain.AddShopItemUseCase
import com.example.myshoppinglist.domain.EditShopItemUseCase
import com.example.myshoppinglist.domain.GetItemByIdUseCase
import com.example.myshoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val editShopItemUseCase: EditShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {

    private val _nameErrorStateLiveData = MutableLiveData<Boolean>()
    val nameErrorStateLiveData: LiveData<Boolean>
        get() = _nameErrorStateLiveData

    private val _countErrorStateLiveData = MutableLiveData<Boolean>()
    val countErrorStateLiveData: LiveData<Boolean>
        get() = _countErrorStateLiveData

    private val _shopitemLiveData = MutableLiveData<ShopItem>()
    val shopitemLiveData: LiveData<ShopItem>
        get() = _shopitemLiveData

    private val _mayCloseLiveData = MutableLiveData<Unit>()
    val mayCloseLiveData: LiveData<Unit>
        get() = _mayCloseLiveData

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopitemLiveData.value?.let {
                viewModelScope.launch {
                    val shopItem = it.copy(name = name, quantity = count)
                    editShopItemUseCase.editShopItem(shopItem)
                    finishWork()
                }
            }
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {

            viewModelScope.launch {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }
        }
    }

    fun getItemById(id: Int) {
        viewModelScope.launch {
            val item = getItemByIdUseCase.getItemById(id)
            _shopitemLiveData.value = item
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _nameErrorStateLiveData.value = true
            result = false
        }
        if (count <= 0) {
            _countErrorStateLiveData.value = true
            result = false
        }
        return result
    }

    fun resetErrorState() {
        _nameErrorStateLiveData.value = false
        _countErrorStateLiveData.value = false
    }

    private fun finishWork() {
        _mayCloseLiveData.value = Unit
    }
}