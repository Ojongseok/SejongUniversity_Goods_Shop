package com.sejong.sejonggoodsmallproject.ui.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sejong.sejonggoodsmallproject.data.room.RecentSearchModel
import com.sejong.sejonggoodsmallproject.databinding.ItemRecentSearchBinding
import kotlinx.android.synthetic.main.item_recent_search.view.*

class RecentSearchedAdapter(private var list : List<RecentSearchModel>)
    : RecyclerView.Adapter<RecentSearchedAdapter.CustomViewHolder>() {
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

        holder.itemView.btn_recent_search_remove.setOnClickListener {
            itemClickListener.onClickRemove(it,position)
        }
        holder.itemView.tv_recent_search_title.setOnClickListener {
            itemClickListener.onClickTitle(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClickRemove(v: View, position: Int)
        fun onClickTitle(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = list.size

    fun setData(newData: List<RecentSearchModel>) {
        list = newData
        notifyDataSetChanged()
    }

    fun getItem(position: Int): RecentSearchModel {
        return list[position]
    }
    fun getTitle(position: Int) : String {
        return list[position].title
    }
}