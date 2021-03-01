package com.wahyupermadie.myapplication.utils.extension

import android.app.Activity
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.CheckResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun View.hideView() {
    this.visibility = View.GONE
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

private val negative = floatArrayOf(
    -1.0f, .0f, .0f, .0f, 255.0f,
    .0f, -1.0f, .0f, .0f, 255.0f,
    .0f, .0f, -1.0f, .0f, 255.0f,
    .0f, .0f, .0f, 1.0f, .0f
)

fun ImageView.toNegative() {
    this.colorFilter = ColorMatrixColorFilter(negative)
}

fun Drawable.toNegative() {
    this.colorFilter = ColorMatrixColorFilter(negative)
}

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                offer(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}