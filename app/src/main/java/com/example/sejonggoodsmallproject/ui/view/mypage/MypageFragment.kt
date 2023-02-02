package com.example.sejonggoodsmallproject.ui.view.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {
    private var _binding : FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackPressed()


    }


    private fun setBackPressed() {
//        binding.btnSearchBack.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
//        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction().remove(this@MypageFragment).commit()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}