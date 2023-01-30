package com.example.sejonggoodsmallproject.ui.view.productdetail.buy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentBuyBinding
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity

class BuyFragment : Fragment() {
    private var _binding : FragmentBuyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBuyBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ltDown.setOnClickListener {
            (activity as ProductDetailActivity).supportFragmentManager.beginTransaction().remove(this).commit()
        }
        setSpinner()
    }

    private fun setSpinner() {
        val list = listOf("옵션1 선택하기","블랙","카키","핑크","그린")
        val list2 = listOf("옵션2 선택하기", "S","M","L","XL")

        binding.spinner.adapter = OptionSpinnerAdapter(requireContext(), R.layout.item_spinner_buy_option,list)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = binding.spinner.getItemAtPosition(p2).toString()
                Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // 선택되지 않은 경우
            }
        }

        binding.spinner2.adapter = OptionSpinnerAdapter(requireContext(), R.layout.item_spinner_buy_option,list2)
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = binding.spinner.getItemAtPosition(p2).toString()

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}