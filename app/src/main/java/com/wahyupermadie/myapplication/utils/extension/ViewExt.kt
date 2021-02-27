package com.wahyupermadie.myapplication.utils.extension

import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView

fun View.hideView() {
    this.visibility = View.GONE
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

private val negative = floatArrayOf(
    -1.0f,     .0f,     .0f,    .0f,  255.0f,
    .0f,   -1.0f,     .0f,    .0f,  255.0f,
    .0f,     .0f,   -1.0f,    .0f,  255.0f,
    .0f,     .0f,     .0f,   1.0f,     .0f
)

fun ImageView.toNegative() {
    this.colorFilter = ColorMatrixColorFilter(negative)
}

fun Drawable.toNegative() {
    this.colorFilter = ColorMatrixColorFilter(negative)
}