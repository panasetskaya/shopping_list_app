package com.example.myshoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {

    override suspend fun getItemById(id: Int): ShopItem {
        return mapper.mapDbModeltoEntity(shopListDao.getShopItem(id))
    }

    override fun getShopList(): LiveData<List<ShopItem>> =
        MediatorLiveData<List<ShopItem>>().apply {
            addSource(shopListDao.getShopList()) {
                value = mapper.mapDbModelListtoEntityList(it)
            }
        }

    override suspend fun addShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntitytoDbModel(item))
    }

    override suspend fun deleteShopItem(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override suspend fun editShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntitytoDbModel(item))
    }
}