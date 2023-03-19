package com.sejong.sejonggoodsmallproject.ui.view.order

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sejong.sejonggoodsmallproject.data.model.CartListResponse
import com.sejong.sejonggoodsmallproject.data.model.OdrOrderItems
import com.sejong.sejonggoodsmallproject.data.model.OptionPicked
import com.sejong.sejonggoodsmallproject.data.model.OrderDetailResponse
import com.sejong.sejonggoodsmallproject.databinding.ItemOrderCompleteProductListBinding

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
            binding.tvOrderCompleteDeliveryfee.text = "${item.deliveryFee}원"

            binding.tvItemOrderProductOption.text = if (optionPicked.option1 != null && optionPicked.option2 != null) {
                "${optionPicked.option1}, ${optionPicked.option2}"
            } else if (optionPicked.option1 != null && optionPicked.option2 == null) {
                "${optionPicked.option1}"
            } else if (optionPicked.option1 == null && optionPicked.option2 != null) {
                "${optionPicked.option2}"
            } else if (optionPicked.option1 == null && optionPicked.option2 == null) {
                "선택사항 없음"
            } else { "" }

            binding.btnItemCompleteCopy.setOnClickListener {
                val getAccount = binding.tvOrderCompleteBankNumber.text.toString()

                val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip : ClipData = ClipData.newPlainText("getAccount", getAccount)
                clipboard.setPrimaryClip(clip)

                Toast.makeText(context, "텍스트가 클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
            }

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