package com.wahyupermadie.myapplication.utils.extension

import android.content.Context
import android.graphics.drawable.Drawable
import coil.ImageLoader
import coil.request.LoadRequest

inline fun Context.loadImage(
    imgUrl: String,
    crossinline onSuccess: (Drawable) -> Unit
) {
    val imageLoader = ImageLoader(this)
    val request = LoadRequest.Builder(this)
            .data(imgUrl)
            .target{ drawable ->
                onSuccess(drawable)

            }.build()
    imageLoader.execute(request)
}