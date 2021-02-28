package com.wahyupermadie.myapplication.utils.extension

import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import com.wahyupermadie.myapplication.data.usecase.model.User

fun UserResponse.transform() : User {
    return User(
        this.blog,
        this.company,
        this.id,
        this.followers,
        this.avatarUrl,
        this.following,
        this.publicRepos,
        this.name ?: this.login,
        this.note
    )
}