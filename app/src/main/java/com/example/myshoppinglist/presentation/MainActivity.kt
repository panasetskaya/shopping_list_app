package com.example.myshoppinglist.presentation

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            shopListAdapter.submitList(it)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.recyclerViewShopList)
        shopListAdapter = ShopListAdapter()
        with (rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupOnLongClickListener()
        setupOnClickListener()
        setupSwipeListener(rvShopList)
    }

    private fun setupOnLongClickListener() {
        shopListAdapter.onShopItemLongClickListener =  {
            viewModel.changeIsActiveState(it)
        }
    }

    private fun setupOnClickListener() {
        shopListAdapter.onShopItemClickListener =  {
            showSorryDialog()
        }
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object: MyItemTouchCallback(viewModel, shopListAdapter) {}
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun showSorryDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Сорри")
        alertDialog.setMessage("Пока нет возможности перейти к товару")
        alertDialog.setPositiveButton("Ок") { dialog, i ->
            dialog.cancel()
        }
        alertDialog.show()
    }
}