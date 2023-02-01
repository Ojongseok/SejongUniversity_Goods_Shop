package com.example.sejonggoodsmallproject.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.ActivityInitBinding
import com.example.sejonggoodsmallproject.databinding.ActivityMainBinding
import com.example.sejonggoodsmallproject.ui.view.MainActivity

class InitActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityInitBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.init_container,SignupFragment()).commit()
        }

        binding.btnLogin.setOnClickListener {
            Toast.makeText(applicationContext,"로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, MainActivity()::class.java))
            finish()
        }
    }
}