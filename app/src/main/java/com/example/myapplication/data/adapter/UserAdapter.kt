package com.example.myapplication.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.data.model.SearchResponse
import com.example.myapplication.databinding.ItemRowUserBinding

class UserAdapter (private val data:MutableList<SearchResponse.Item> = mutableListOf(),
private val listener: (SearchResponse.Item) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(data: MutableList<SearchResponse.Item>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: ItemRowUserBinding) : RecyclerView.ViewHolder(v.root){
        fun bind(item: SearchResponse.Item){
            v.imgAvatar.load(item.avatar_url)
            v.tvListUser.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }
}