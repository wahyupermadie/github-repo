package com.wahyupermadie.myapplication.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.databinding.ItemUserBinding
import com.wahyupermadie.myapplication.presentation.search.SearchUserAdapter.ViewHolder
import com.wahyupermadie.myapplication.utils.extension.loadImage

class SearchUserAdapter(
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    private var users: List<User> = mutableListOf()
    private lateinit var onClickListener: OnClickListener
    fun setListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun addData(users: List<User>) {
        this.users = users
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position], onClickListener, position)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onClickListener: OnClickListener, position: Int) {
            binding.apply {
                root.setOnClickListener {
                    onClickListener.onItemClick(user)
                }
                tvName.text = user.name
                user.avatarUrl?.let { ivAvatar.loadImage(it, context) }
            }
        }
    }

    interface OnClickListener {

        fun onItemClick(user: User)
    }
}