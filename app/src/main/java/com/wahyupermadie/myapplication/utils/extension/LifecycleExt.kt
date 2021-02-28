package com.wahyupermadie.myapplication.utils.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.wahyupermadie.myapplication.utils.network.Event
import com.wahyupermadie.myapplication.utils.network.EventObserver

fun <T>LifecycleOwner.observe(data: LiveData<T>, value: (T) -> Unit) {
    data.observe(this, {
        if (it != null) {
            value.invoke(it)
        }
    })
}

fun <T>LifecycleOwner.observeEvent(data: LiveData<Event<T>>, value: (T) -> Unit) {
    data.observe(this, EventObserver {
        value.invoke(it)
    })
}