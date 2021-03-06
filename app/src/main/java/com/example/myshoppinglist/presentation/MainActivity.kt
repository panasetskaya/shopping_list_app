package com.example.myshoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
        setupRecyclerView()
        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceAddItem()
                launchFragment(fragment)
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainerMain == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        shopListAdapter = ShopListAdapter()
        shopListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        with(binding.recyclerViewShopList) {
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
        setupSwipeListener(binding.recyclerViewShopList)
    }

    private fun setupOnLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeIsActiveState(it)
        }
    }

    private fun setupOnClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceEditItem(it.id)
                launchFragment(fragment)
            }
        }
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : MyItemTouchCallback(viewModel, shopListAdapter) {}
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

//    private fun showSorryDialog() {
//        val alertDialog = AlertDialog.Builder(this)
//        alertDialog.setTitle("??????????")
//        alertDialog.setMessage("???????? ?????? ?????????????????????? ?????????????? ?? ????????????")
//        alertDialog.setPositiveButton("????") { dialog, i ->
//            dialog.cancel()
//        }
//        alertDialog.show()
//    }
}