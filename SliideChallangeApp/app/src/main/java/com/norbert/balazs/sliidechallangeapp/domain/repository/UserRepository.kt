package com.norbert.balazs.sliidechallangeapp.domain.repository

import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto

interface UserRepository {

    suspend fun getUsers(): List<UserDto>

    suspend fun addUser()

    suspend fun deleteUser()
}