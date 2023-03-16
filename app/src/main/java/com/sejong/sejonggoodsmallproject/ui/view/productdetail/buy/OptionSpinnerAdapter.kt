package com.sejong.sejonggoodsmallproject.ui.view.productdetail.buy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.sejong.sejonggoodsmallproject.databinding.ItemSpinnerBuyOptionBinding

class OptionSpinnerAdapter(context: Context, @LayoutRes private val resId: Int, private val menuList: List<String>)
    : ArrayAdapter<String>(context, resId, menuList) {

    // 드롭다운하지 않은 상태의 Spinner 항목의 뷰
    override fun getView(position: Int, converView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerBuyOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.tvSpinner.text = menuList[position]

        return binding.root
    }

    // 드롭다운된 항목들 리스트의 뷰
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerBuyOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.tvSpinner.text = menuList[position]

        return binding.root
    }

    override fun getCount() = menuList.size
}