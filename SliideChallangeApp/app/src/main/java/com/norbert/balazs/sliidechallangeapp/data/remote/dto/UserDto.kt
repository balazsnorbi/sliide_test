package com.norbert.balazs.sliidechallangeapp.data.remote.dto

import com.norbert.balazs.sliidechallangeapp.domain.model.User

data class UserDto(
    val email: String,
    val gender: String,
    val id: Int,
    val name: String,
    val status: String
)

fun UserDto.toUser(): User {
    return User(
        id,
        email,
        name,
        System.currentTimeMillis()
    )
}