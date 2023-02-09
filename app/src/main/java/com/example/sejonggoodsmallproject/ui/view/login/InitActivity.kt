package com.example.sejonggoodsmallproject.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.ActivityInitBinding
import com.example.sejonggoodsmallproject.ui.view.MainActivity
import com.example.sejonggoodsmallproject.util.MyApplication

class InitActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityInitBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this

        Log.d("태그", MyApplication.prefs.getString("accessToken",""))
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.btn_signup -> {
                supportFragmentManager.beginTransaction().replace(R.id.init_container,SignupFragment()).commit()
            }
            R.id.btn_login -> {
                supportFragmentManager.beginTransaction().replace(R.id.init_container,LoginFragment()).commit()
            }
            R.id.btn_no_login_enter -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }
    }
}