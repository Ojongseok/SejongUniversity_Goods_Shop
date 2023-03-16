package com.sejong.sejonggoodsmallproject.ui.view.productdetail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.sejong.sejonggoodsmallproject.R

class HelpDialog(context: Context) {
    val dialog = Dialog(context)

    fun showDialog() {
        dialog.setContentView(R.layout.dialog_product_detail_help)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

}