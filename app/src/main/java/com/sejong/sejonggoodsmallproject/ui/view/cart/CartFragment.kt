package com.sejong.sejonggoodsmallproject.ui.view.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sejong.sejonggoodsmallproject.R
import com.sejong.sejonggoodsmallproject.databinding.FragmentCartBinding
import com.sejong.sejonggoodsmallproject.ui.view.home.MainActivity
import com.sejong.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.dialog_cart_remove_confirm.*
import kotlinx.android.synthetic.main.dialog_order_previous_alert.*

class CartFragment : Fragment() {
    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCartBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setTabLayout()

        binding.btnCartBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()

            requireActivity().onBackPressed()
        }
    }

    private fun setTabLayout() {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.cart_container,VisitInCartFragment()).commit()

        binding.cartFragmentTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.cart_container,VisitInCartFragment())
                            .commit()
                    }

                    1 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.cart_container,DelivetyInCartFragment())
                            .commit()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}