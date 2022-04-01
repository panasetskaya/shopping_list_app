package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    companion object {
        const val ENABLED = 1
        const val DISABLED = 0
    }


    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.item_name)
        val tvCount = view.findViewById<TextView>(R.id.item_count)
    }

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = if (viewType== ENABLED) LayoutInflater.from(parent.context).inflate(
            R.layout.item_enabled,
            parent,
            false
        ) else LayoutInflater.from(parent.context).inflate(
            R.layout.item_disabled,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.isActive) ENABLED
        else DISABLED
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = shopList[position]
        holder.tvName.text = item.name
        holder.tvCount.text = item.quantity.toString()
        holder.view.setOnLongClickListener {
            item.isActive = !item.isActive
            true
            TODO("НО: чтобы заработало, приходится скроллить туда-обратно. как обновить прямо на экране??")
        }
    }

    override fun getItemCount(): Int = shopList.size

}