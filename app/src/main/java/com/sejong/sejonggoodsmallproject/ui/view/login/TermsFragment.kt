package com.sejong.sejonggoodsmallproject.ui.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sejong.sejonggoodsmallproject.R
import com.sejong.sejonggoodsmallproject.databinding.FragmentTermsBinding

class TermsFragment : Fragment() {
    private var _binding : FragmentTermsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTermsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTermsBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this@TermsFragment)
                .commit()

            requireActivity().onBackPressed()
        }

        binding.btnSignupTermsAgree.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this@TermsFragment)
                .commit()
            requireActivity().onBackPressed()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}