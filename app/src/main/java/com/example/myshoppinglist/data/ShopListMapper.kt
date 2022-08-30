package com.example.myshoppinglist.data

import com.example.myshoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor(){

    fun mapEntitytoDbModel(shopItem: ShopItem) = ShopItemDbModel (
        name = shopItem.name,
        id = shopItem.id,
        quantity = shopItem.quantity,
        isActive = shopItem.isActive
            )

    fun mapDbModeltoEntity(model: ShopItemDbModel) = ShopItem(
        name = model.name,
        id = model.id,
        quantity = model.quantity,
        isActive = model.isActive
    )

    fun mapDbModelListtoEntityList(list: List<ShopItemDbModel>) = list.map {
        mapDbModeltoEntity(it)
    }

}