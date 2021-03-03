package com.wahyupermadie.myapplication.utils.extension

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wahyupermadie.myapplication.R

fun ImageView.loadImage(
    imgUrl: String,
    context: Context
) {
    Glide.with(context)
        .asBitmap()
        .load(imgUrl)
        .placeholder(R.drawable.iv_avatar)
        .into(this)
}