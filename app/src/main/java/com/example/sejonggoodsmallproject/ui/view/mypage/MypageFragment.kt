package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentMypageBinding
import com.example.sejonggoodsmallproject.ui.view.login.InitActivity

class MypageFragment : Fragment() {
    private var _binding : FragmentMypageBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMypageBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()
        }

        binding.btnMypageLogin.setOnClickListener {
            startActivity(Intent(requireContext(), InitActivity::class.java))
            requireActivity().finish()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(0, R.anim.horizon_exit_front)
                    .remove(this@MypageFragment)
                    .commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun onClick(view: View) {
        when (view.id) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}