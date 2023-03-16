package com.sejong.sejonggoodsmallproject.ui.view.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sejong.sejonggoodsmallproject.data.model.CartListResponse
import com.sejong.sejonggoodsmallproject.data.model.OptionPicked
import com.sejong.sejonggoodsmallproject.databinding.ItemOrderProductListBinding

class OrderCartProductListAdapter(
    private var context: Context,
    private var list : List<CartListResponse>,
    private val optionPickedList : ArrayList<OptionPicked>) : RecyclerView.Adapter<OrderCartProductListAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemOrderProductListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartListResponse, optionPicked: OptionPicked) {
            binding.tvItemOrderProductTitle.text = item.title
            binding.tvItemOrderCompany.text = item.seller
            Glide.with(context).load(item.repImage.oriImgName).into(binding.ivProduct)

            binding.tvItemOrderPrice.text = priceUpdate(item.price)
            binding.tvItemOrderProductQuantity.text = "수량 ${optionPicked.quantity}개"

            binding.tvItemOrderProductOption.text = if (optionPicked.option1 != null && optionPicked.option2 != null) {
                "${optionPicked.option1}, ${optionPicked.option2}"
            } else if (optionPicked.option1 != null && optionPicked.option2 == null) {
                "${optionPicked.option1}"
            } else if (optionPicked.option1 == null && optionPicked.option2 != null) {
                "${optionPicked.option2}"
            } else if (optionPicked.option1 == null && optionPicked.option2 == null) {
                "선택사항 없음"
            } else { "" }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemOrderProductListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position], optionPickedList[position])
    }

    fun priceUpdate(price: Int) : String {
        if (price in 1000..999999) {
            val priceList = price.toString().toCharArray().toMutableList()
            priceList.add(priceList.size-3,',')
            return priceList.joinToString("") + "원"
        } else {
            return price.toString() + "원"
        }
    }

    override fun getItemCount() = list.size
}