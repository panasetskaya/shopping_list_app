package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.myshoppinglist.R
import com.example.myshoppinglist.databinding.ItemDisabledBinding
import com.example.myshoppinglist.databinding.ItemEnabledBinding
import com.example.myshoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    companion object {
        const val ENABLED = 1
        const val DISABLED = 0
        const val MAX_POOL_SIZE = 15
        // желательно проверить пул_сайз на слабых устройствах, устройствах с большим экраном - не создается ли слишком много вьюхолдеров?
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
//        val binding = when (viewType) {
//            ENABLED -> ItemEnabledBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//            DISABLED -> ItemDisabledBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//            else -> throw RuntimeException("No such viewType: $viewType!")
//        }
        val layout = when (viewType) {
            ENABLED -> R.layout.item_enabled
            DISABLED -> R.layout.item_disabled
            else -> throw RuntimeException("No such viewType: $viewType!")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isActive) ENABLED
        else DISABLED
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        when (binding) {
            is ItemEnabledBinding -> {
                binding.itemName.text = item.name
                binding.itemCount.text = item.quantity.toString()
            }
            is ItemDisabledBinding -> {
                binding.itemName.text = item.name
                binding.itemCount.text = item.quantity.toString()
            }
        }

        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(item)
            true
        }
        holder.binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(item)
            true
        }
    }
}