package com.example.myshoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myshoppinglist.R

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    private var screenMode = SCREEN_MODE_DEFAULT
    private var id = ID_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }

    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(id)
            else -> throw RuntimeException("Unknown screen mode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("No param: screen mode")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown param: screen mode $mode")
        }
        screenMode = mode
        if (mode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("No param: shopItem id")
            }
            id = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ID_DEFAULT)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val ID_DEFAULT = -1
        private const val SCREEN_MODE_DEFAULT = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }

    }

    override fun onEditingFinished() {
        Toast.makeText(this@ShopItemActivity, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }
}