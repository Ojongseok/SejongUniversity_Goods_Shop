package com.sejong.sejonggoodsmallproject.ui.view.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.sejong.sejonggoodsmallproject.R
import com.sejong.sejonggoodsmallproject.data.model.FindPasswordPost
import com.sejong.sejonggoodsmallproject.databinding.FragmentFindPasswordBinding
import com.sejong.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FindPasswordFragment : Fragment() {
    private var _binding : FragmentFindPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFindPasswordBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboard()

        binding.btnFindPasswordBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()

            requireActivity().onBackPressed()
        }

        binding.btnFindPassComplete.setOnClickListener {
            val username = binding.etFindPassName.text.toString()
            val email = binding.etFindPassEmail.text.toString()
            binding.pbFindPassword1.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                val response = retrofitService.findPassword(FindPasswordPost(username,email))

                withContext(Dispatchers.Main) {
                    if (response.code() == 200) {
                        val frg = FindPasswordFragment2()
                        val bundle = Bundle()
                        bundle.putString("email", email)
                        frg.arguments = bundle

                        requireActivity().onBackPressed()

                        requireActivity().supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.horizon_enter_front,0)
                            .replace(R.id.init_container, frg,"backStack")
                            .addToBackStack("backStack")
                            .commitAllowingStateLoss()
                    } else {
                        Toast.makeText(requireContext(), "일치하는 계정을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                        binding.pbFindPassword1.visibility = View.INVISIBLE
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