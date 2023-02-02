package com.example.sejonggoodsmallproject.ui.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.databinding.ItemProductListBinding
import com.example.sejonggoodsmallproject.databinding.ItemRecentSearchBinding
import com.example.sejonggoodsmallproject.ui.view.ProductListAdapter

class RecentSearchedAdapter(private val list : List<RecentSearchModel>) : RecyclerView.Adapter<RecentSearchedAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemRecentSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearchModel) {
            binding.tvRecentSearchTitle.text = item.title
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentSearchedAdapter.CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = list.size
}