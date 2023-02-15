package com.example.sejonggoodsmallproject.ui.view.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.example.sejonggoodsmallproject.R

class LoginDialog(context: Context) {
    val dialog = Dialog(context)

    fun showDialog() {
        dialog.setContentView(R.layout.dialog_login_confirm)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

}