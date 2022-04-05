package com.example.myshoppinglist.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

open class MyItemTouchCallback(private val viewModel: MainViewModel, private val adapter: ShopListAdapter): ItemTouchHelper.Callback() {

    private var swipeback = false
    private var isAlertDialogShowed = false

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeback) {
            swipeback = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        setTouchListener(recyclerView)
        Log.d("MyTag", "show dX: $dX")
        if (dX > -300f && !isAlertDialogShowed) {
            isAlertDialogShowed = true
            val alertDialog = AlertDialog.Builder(recyclerView.context)
            alertDialog.setTitle("Удаление")
            alertDialog.setMessage("Вы уверены, что хотите удалить этот товар из списка?")
            alertDialog.setPositiveButton("Да") { dialog, i ->
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
                isAlertDialogShowed = false
                dialog.cancel()
            }
            alertDialog.setNegativeButton("Нет") { dialog, i ->
                isAlertDialogShowed = false
                dialog.cancel()
            }
            alertDialog.create().show()
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(recyclerView: RecyclerView) {
        recyclerView.setOnTouchListener { view, motionEvent ->
            swipeback =
                motionEvent.action == MotionEvent.ACTION_CANCEL || motionEvent.action == MotionEvent.ACTION_UP
            view.performClick()
            false
        }
    }
}