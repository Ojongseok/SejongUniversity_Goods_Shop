package com.example.sejonggoodsmallproject.ui.view.cart

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.example.sejonggoodsmallproject.R

class CartRemoveDialog(context: Context) {
    val dialog = Dialog(context)

    fun showDialog() {
        dialog.setContentView(R.layout.dialog_cart_remove_confirm)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()



    }
}