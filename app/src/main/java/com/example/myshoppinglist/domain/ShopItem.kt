package com.example.myshoppinglist.domain

data class ShopItem(
    val name: String,
    val quantity: Int,
    var isActive: Boolean = true,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
