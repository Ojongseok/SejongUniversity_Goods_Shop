package com.example.sejonggoodsmallproject.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.MutableLiveData
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentSignupBinding
import com.example.sejonggoodsmallproject.ui.view.MainActivity
import com.example.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import com.example.sejonggoodsmallproject.util.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {
    private var _binding : FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private var emailFlag = false
    private var passFlag = false
    private var nameFlag = false
    private var birthFlag = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackPressed()
        setSignupTextWatcher()


    }

    private fun setSignupTextWatcher() {
        // 이메일 입력 감지
        binding.etEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvWarnEmail.visibility = View.VISIBLE
                binding.ivCheckEmail.visibility = View.INVISIBLE
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etEmail.text.contains('@')) {
                    binding.tvWarnEmail.visibility = View.INVISIBLE
                    binding.ivCheckEmail.visibility = View.VISIBLE
                    emailFlag = true
                } else {
                    binding.tvWarnEmail.visibility = View.VISIBLE
                    binding.ivCheckEmail.visibility = View.INVISIBLE
                    emailFlag = false
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                setSignupBtnFlag()
            }
        })
        // 비밀번호 입력 감지
        binding.etPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvWarnPassword.visibility = View.VISIBLE
                binding.ivCheckPassword.visibility = View.INVISIBLE
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etPassword.text.length>=8) {
                    binding.tvWarnPassword.visibility = View.INVISIBLE
                    binding.ivCheckPassword.visibility = View.VISIBLE
                } else {
                    binding.tvWarnPassword.visibility = View.VISIBLE
                    binding.ivCheckPassword.visibility = View.INVISIBLE
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                setSignupBtnFlag()
            }
        })
        // 비밀번호 확인 입력 감지
        binding.etPasswordConfirm.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvWarnPasswordConfirm.visibility = View.VISIBLE
                binding.ivCheckPasswordConfirm.visibility = View.INVISIBLE
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etPassword.text.toString() == binding.etPasswordConfirm.text.toString()) {
                    binding.tvWarnPasswordConfirm.visibility = View.INVISIBLE
                    binding.ivCheckPasswordConfirm.visibility = View.VISIBLE
                    passFlag = true
                } else {
                    binding.tvWarnPasswordConfirm.visibility = View.VISIBLE
                    binding.ivCheckPasswordConfirm.visibility = View.INVISIBLE
                    passFlag = false
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                setSignupBtnFlag()
            }
        })
        // 이름 입력 감지
        binding.etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameFlag = binding.etName.text.isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {
                setSignupBtnFlag()
            }
        })
        // 생년월일 입력 감지
        binding.etBirth.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                birthFlag = binding.etBirth.text.isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {
                setSignupBtnFlag()
            }
        })
    }

    private fun setSignupBtnFlag() {
        // 회원가입 버튼 활성화
        if (emailFlag && passFlag && nameFlag && birthFlag) {
            binding.btnSignupComplete.setBackgroundResource(R.drawable.background_rec_10dp_red_stroke_red_soild)

            binding.btnSignupComplete.setOnClickListener {
                // 회원가입 서버로 보내기
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val username = binding.etName.text.toString()
                val birth = binding.etBirth.text.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    val response = retrofitService.authSignup(email, password, username, birth)

                    if (response.isSuccessful) {
                        requireActivity().supportFragmentManager.beginTransaction().remove(this@SignupFragment).commit()
                    } else {
                        response.errorBody()
                    }
                }
            }
        } else {
            binding.btnSignupComplete.setBackgroundResource(R.drawable.background_rec_10dp_grey_soild)
        }
    }

    private fun setBackPressed() {
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SignupFragment).commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction().remove(this@SignupFragment).commit()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}