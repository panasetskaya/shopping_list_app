package com.example.myshoppinglist.domain

data class ShopItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val isActive: Boolean
)
