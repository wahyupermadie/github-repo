package com.wahyupermadie.myapplication.data.usecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val blog: String? = null,
    val company: String? = null,
    val id: Int? = null,
    val followers: Int? = null,
    val avatarUrl: String? = null,
    val following: Int? = null,
    val publicRepos: Int? = null,
    val name: String? = null,
    val note: String? = null
) : Parcelable