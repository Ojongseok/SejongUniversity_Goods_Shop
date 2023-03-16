package com.sejong.sejonggoodsmallproject.ui.view.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sejong.sejonggoodsmallproject.R
import com.sejong.sejonggoodsmallproject.data.model.LoginPost
import com.sejong.sejonggoodsmallproject.databinding.FragmentLoginBinding
import com.sejong.sejonggoodsmallproject.ui.view.home.MainActivity
import com.sejong.sejonggoodsmallproject.util.MyApplication
import com.sejong.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackPressed()

        binding.btnLoginComplete.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.pbLogin.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.IO).launch {
                    val response = retrofitService.authLogin(LoginPost(email,password))

                    withContext(Dispatchers.Main) {
                        when(response.code()) {
                            200 -> {
                                val accessToken = response.body()?.token!!
                                MyApplication.prefs.setString("accessToken",accessToken)

                                Toast.makeText(requireContext(),"환영합니다 :)",Toast.LENGTH_SHORT).show()

                                startActivity(Intent(requireContext(), MainActivity()::class.java))
                                requireActivity().finish()
                            }

                            400 -> {
                                binding.pbLogin.visibility = View.INVISIBLE
                                Toast.makeText(requireContext(),"이메일 및 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(),"이메일 및 비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnFindId.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.horizon_enter_front,0)
                .add(R.id.init_container, FindEmailFragment(),"backStack")
                .addToBackStack("backStack")
                .commitAllowingStateLoss()
        }
    }

    private fun setBackPressed() {
        binding.btnLoginBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this)
                .commit()

            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}