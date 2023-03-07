package com.example.sejonggoodsmallproject.ui.view.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.data.model.CartListResponse
import com.example.sejonggoodsmallproject.data.model.OdrOrderItems
import com.example.sejonggoodsmallproject.data.model.OptionPicked
import com.example.sejonggoodsmallproject.data.model.OrderDetailResponse
import com.example.sejonggoodsmallproject.databinding.ItemOrderCompleteProductListBinding

class OrderCompleteProductListAdapter(
    private var context: Context,
    private var orderDetailResponse: OrderDetailResponse,
    private val optionPickedList: ArrayList<OptionPicked>,
    private val responseCartList: ArrayList<CartListResponse>
) : RecyclerView.Adapter<OrderCompleteProductListAdapter.CustomViewHolder>() {
    private val list = orderDetailResponse.orderItems
    inner class CustomViewHolder(private val binding: ItemOrderCompleteProductListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OdrOrderItems, optionPicked: OptionPicked, cartListResponse: CartListResponse) {
            binding.tvItemOrderProductTitle.text = cartListResponse.title
            binding.tvItemOrderCompany.text = cartListResponse.seller
            binding.tvOrderCompleteName.text = item.seller.name
            binding.tvOrderCompleteBank.text = item.seller.bank
            binding.tvOrderCompleteBankNumber.text = item.seller.account

            Glide.with(context).load(cartListResponse.repImage.oriImgName).into(binding.ivProduct)

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
        val view = ItemOrderCompleteProductListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position], optionPickedList[position], responseCartList[position])
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