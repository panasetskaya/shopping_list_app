package com.example.myshoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun getItemById(id: Int): ShopItem {
        return mapper.mapDbModeltoEntity(shopListDao.getShopItem(id))
    }

    override fun getShopList(): LiveData<List<ShopItem>> =
        MediatorLiveData<List<ShopItem>>().apply {
            addSource(shopListDao.getShopList()) {
                value = mapper.mapDbModelListtoEntityList(it)
            }
        }

    override fun addShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntitytoDbModel(item))
    }

    override fun deleteShopItem(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override fun editShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntitytoDbModel(item))
    }
}