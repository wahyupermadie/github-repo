package com.wahyupermadie.myapplication.presentation.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.databinding.ItemUserBinding
import com.wahyupermadie.myapplication.utils.extension.hideView
import com.wahyupermadie.myapplication.utils.extension.loadImage
import com.wahyupermadie.myapplication.utils.extension.showView
import com.wahyupermadie.myapplication.utils.extension.toNegative

class MainUserAdapter(
    private val context: Context,
    private val onClickListener: (User) -> Unit
) : PagingDataAdapter<User, MainUserAdapter.ViewHolder>(UserComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onClickListener, position)
        }
    }

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onClickListener: (User) -> Unit, position: Int) {
            itemView.setOnClickListener {
                onClickListener(user)
            }

            binding.apply {
                tvName.text = user.name
                user.avatarUrl?.let { img ->
                    ivAvatar.apply {
                        loadImage(img, context)
                        if ((position + 1) % 4 == 0) {
                            toNegative()
                        }
                    }
                }
                if (user.note.isNullOrEmpty()) ivNote.hideView() else ivNote.showView()
            }
        }
    }

    companion object {

        private val UserComparator = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}