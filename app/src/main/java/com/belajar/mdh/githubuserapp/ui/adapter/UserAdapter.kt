package com.belajar.mdh.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.belajar.mdh.githubuserapp.data.response.GetUserResponseItem
import com.belajar.mdhgithubuserapp.databinding.ItemUserBinding
import java.util.Locale


class UserAdapter(private val data: List<GetUserResponseItem>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // Replace the existing data with the new data
//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(newData: UserResponse) {
////        data()
////        data.addAll(newData)
//        notifyDataSetChanged()
//    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetUserResponseItem) {
            binding.image.load(item.avatarUrl) {
                transformations(CircleCropTransformation())
            }
            val name = item.login.toString().toUpperCase(Locale.ROOT)
            binding.username.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            item.id
        }
    }
}