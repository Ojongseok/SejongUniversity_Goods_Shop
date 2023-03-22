package com.sejong.sejonggoodsmallproject.ui.view.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.MutableLiveData
import com.sejong.sejonggoodsmallproject.R
import com.sejong.sejonggoodsmallproject.data.model.SignupPost
import com.sejong.sejonggoodsmallproject.databinding.FragmentSignupBinding
import com.sejong.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupFragment : Fragment() {
    private var _binding : FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private var emailFlag = false
    private var passFlag = false
    private var nameFlag = false
    private var birthFlag = false
    private var checkBoxFlag = false
    private var passFlagLiveData : MutableLiveData<Int> = MutableLiveData()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSignupTextWatcher()

        passFlagLiveData.observe(viewLifecycleOwner) {
            if (it == 1) {
                passFlag = true
            } else if (it == 0) {
                passFlag = false
            }
        }

        binding.checkboxSignupTerms.setOnCheckedChangeListener { compoundButton, isCheckedd ->
            if (isCheckedd) {
                checkBoxFlag = true
                setSignupBtnFlag()
            } else {
                checkBoxFlag = false
                setSignupBtnFlag()
            }
        }

        binding.tvShowTerms.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.horizon_enter_front, 0)
                .add(R.id.init_container,TermsFragment(),"backStack")
                .addToBackStack("backStack")
                .commitAllowingStateLoss()
        }

        binding.btnBackSignup.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this@SignupFragment)
                .commit()
        }
    }

    private fun setSignupTextWatcher() {
        // 이메일 입력 감지
        binding.etEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
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
                setSignupBtnFlag()
            }
            override fun afterTextChanged(p0: Editable?) { }
        })
        // 비밀번호 입력 감지
        binding.etPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etPassword.text.length >= 8) {
                    binding.tvWarnPassword.visibility = View.INVISIBLE
                    binding.ivCheckPassword.visibility = View.VISIBLE
                    if (binding.etPasswordConfirm.text.isEmpty()) {
                        binding.tvWarnPassword.visibility = View.INVISIBLE
                        binding.ivCheckPassword.visibility = View.VISIBLE
                        passFlagLiveData.value = 1
                    } else if (binding.etPassword.text.toString() == binding.etPasswordConfirm.text.toString()) {
                        binding.tvWarnPassword.visibility = View.INVISIBLE
                        binding.ivCheckPassword.visibility = View.VISIBLE
                        passFlagLiveData.value = 1
                    } else {
                        binding.tvWarnPassword.visibility = View.VISIBLE
                        binding.ivCheckPassword.visibility = View.INVISIBLE
                        passFlagLiveData.value = 0
                    }
                } else {
                    binding.tvWarnPassword.visibility = View.VISIBLE
                    binding.ivCheckPassword.visibility = View.INVISIBLE
                    passFlagLiveData.value = 0
                }
                setSignupBtnFlag()
            }
            override fun afterTextChanged(p0: Editable?) { }
        })
        // 비밀번호 확인 입력 감지
        binding.etPasswordConfirm.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etPassword.text.toString() == binding.etPasswordConfirm.text.toString()) {
                    binding.tvWarnPasswordConfirm.visibility = View.INVISIBLE
                    binding.ivCheckPasswordConfirm.visibility = View.VISIBLE
                    passFlagLiveData.value = 1
                } else {
                    binding.tvWarnPasswordConfirm.visibility = View.VISIBLE
                    binding.ivCheckPasswordConfirm.visibility = View.INVISIBLE
                    passFlagLiveData.value = 0
                }
                setSignupBtnFlag()
            }
            override fun afterTextChanged(p0: Editable?) { }
        })
        // 이름 입력 감지
        binding.etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                nameFlag = binding.etName.text.isNotEmpty()
                setSignupBtnFlag()
            }
        })
        // 생년월일 입력 감지
        binding.etBirth.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val birth = binding.etBirth.text.toString()
                birthFlag = (birth.length == 8)
                setSignupBtnFlag()
            }
        })
    }

    private fun setSignupBtnFlag() {
        // 회원가입 버튼 활성화
        if (emailFlag && passFlag && nameFlag && birthFlag && checkBoxFlag
            && binding.etPassword.text.toString() == binding.etPasswordConfirm.text.toString()) {
            binding.btnSignupComplete.setBackgroundResource(R.drawable.background_rec_10dp_red_stroke_red_solid)

            binding.btnSignupComplete.setOnClickListener {
                // 회원가입 서버로 보내기
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val username = binding.etName.text.toString()
                val birth = binding.etBirth.text.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    val response = retrofitService.authSignup(SignupPost(email, password, username, birth))

                    withContext(Dispatchers.Main) {
                        when(response.code()) {
                            200 -> {
                                requireActivity().supportFragmentManager.beginTransaction().remove(this@SignupFragment).commit()
                                Toast.makeText(requireContext(), "회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                            }
                            400 -> {
                                Toast.makeText(requireContext(), "이미 가입된 이메일입니다.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        } else {
            binding.btnSignupComplete.setBackgroundResource(R.drawable.background_rec_10dp_grey_solid)
            binding.btnSignupComplete.setOnClickListener {
                if (!birthFlag) {
                    Toast.makeText(requireContext(), "생년월일 형식은 YYYYMMDD 입니다.",Toast.LENGTH_SHORT).show()
                } else if (!passFlag) {
                    Toast.makeText(requireContext(), "비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}