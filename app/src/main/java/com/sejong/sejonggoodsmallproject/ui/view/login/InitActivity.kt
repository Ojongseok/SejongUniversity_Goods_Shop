package com.sejong.sejonggoodsmallproject.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sejong.sejonggoodsmallproject.R
import com.sejong.sejonggoodsmallproject.databinding.ActivityInitBinding
import com.sejong.sejonggoodsmallproject.ui.view.home.MainActivity
import com.sejong.sejonggoodsmallproject.util.MyApplication

class InitActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityInitBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this

//        MyApplication.prefs.setString("accessToken", "Not Login State")
        Log.d("tag", MyApplication.prefs.getString("accessToken",""))

        if (MyApplication.prefs.getString("accessToken","Not Login State") != "Not Login State") {
            Toast.makeText(applicationContext, "환영합니다 :)", Toast.LENGTH_SHORT).show()

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.btn_signup -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front, 0)
                    .add(R.id.init_container, SignupFragment(),"backStack")
                    .addToBackStack("backStack")
                    .commitAllowingStateLoss()
            }

            R.id.btn_login -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front,0)
                    .add(R.id.init_container, LoginFragment(),"backStack")
                    .addToBackStack("backStack")
                    .commitAllowingStateLoss()
            }

            R.id.btn_no_login_enter -> {
                MyApplication.prefs.setString("accessToken", "Not Login State")

                Toast.makeText(applicationContext, "환영합니다 :)", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}