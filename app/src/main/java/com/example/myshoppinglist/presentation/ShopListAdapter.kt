package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter :ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    companion object {
        const val ENABLED = 1
        const val DISABLED = 0
        const val MAX_POOL_SIZE = 15
        // желательно проверить пул_сайз на слабых устройствах, устройствах с большим экраном - не создается ли слишком много вьюхолдеров?
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = when (viewType) {
            ENABLED -> LayoutInflater.from(parent.context).inflate(
                R.layout.item_enabled,
                parent,
                false
            )
            DISABLED -> LayoutInflater.from(parent.context).inflate(
                R.layout.item_disabled,
                parent,
                false
            )
            else -> throw RuntimeException("No such viewType: $viewType!")
        }
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isActive) ENABLED
        else DISABLED
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvName.text = item.name
        holder.tvCount.text = item.quantity.toString()
        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(item)
            true
        }
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(item)
            true
        }
    }
}