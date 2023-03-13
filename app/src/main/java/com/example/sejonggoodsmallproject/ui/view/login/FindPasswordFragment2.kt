package com.example.sejonggoodsmallproject.ui.view.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.FindPasswordPost
import com.example.sejonggoodsmallproject.data.model.FindPasswordPost2
import com.example.sejonggoodsmallproject.databinding.FragmentFindPassword2Binding
import com.example.sejonggoodsmallproject.util.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FindPasswordFragment2 : Fragment() {
    private var _binding : FragmentFindPassword2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFindPassword2Binding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboard()

        binding.btnFindPasswordBack2.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()

            requireActivity().onBackPressed()
        }

        binding.btnFindPassComplete2.setOnClickListener {
            val verifyNumber = binding.etFindPassVerifyNumber.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitInstance.retrofitService.findPasswordCheck(FindPasswordPost2(
                    arguments?.getString("email").toString(),verifyNumber.toInt())
                )

                val frg = FindPasswordFragment3()
                val bundle = Bundle()
                bundle.putString("email", arguments?.getString("email").toString())
                frg.arguments = bundle

                withContext(Dispatchers.Main) {
                    if (response.code() == 200) {
                        requireActivity().onBackPressed()

                        requireActivity().supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.horizon_enter_front,0)
                            .replace(R.id.init_container, frg,"backStack")
                            .addToBackStack("backStack")
                            .commitAllowingStateLoss()
                    } else {
                        Toast.makeText(requireContext(), "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
    private fun hideKeyboard() {
        if (requireActivity().currentFocus != null) {
            // 프래그먼트기 때문에 getActivity() 사용
            val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}