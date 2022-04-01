package com.example.myshoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            adapter.shopList = it

        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.recyclerViewShopList)
        adapter = ShopListAdapter()
        rvShopList.adapter = adapter
    }

}