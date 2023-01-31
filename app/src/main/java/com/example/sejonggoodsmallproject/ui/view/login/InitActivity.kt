package com.example.sejonggoodsmallproject.ui.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.ActivityInitBinding
import com.example.sejonggoodsmallproject.databinding.ActivityMainBinding

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
//            supportFragmentManager.beginTransaction().replace(R.id.init_container,).commit()
        }
    }
}