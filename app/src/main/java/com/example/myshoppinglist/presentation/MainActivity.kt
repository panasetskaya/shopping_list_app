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
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            shopListAdapter.submitList(it)

        }
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
        val callback = object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                deleteDialog(item)
                        // здесь он таки убирает айтем в любом случае (правда, если не
                    // удаляли, то при обратном скролле он появляется снова)
            }
        }
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

    private fun deleteDialog(item: ShopItem) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Удаление")
        alertDialog.setMessage("Вы уверены, что хотите удалить этот товар из списка?")
        alertDialog.setPositiveButton("Да") { dialog, i ->
            viewModel.deleteShopItem(item)
            dialog.cancel()
        }
        alertDialog.setNegativeButton("Нет") { dialog, i ->
            dialog.cancel()
        }
        alertDialog.show()
    }
}