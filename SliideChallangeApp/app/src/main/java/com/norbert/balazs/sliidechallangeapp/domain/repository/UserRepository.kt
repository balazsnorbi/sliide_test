package com.norbert.balazs.sliidechallangeapp.domain.repository

import com.norbert.balazs.sliidechallangeapp.data.remote.dto.NewUserDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto
import retrofit2.Response

interface UserRepository {

    suspend fun getUsers(): List<UserDto>

    suspend fun createUser(user: NewUserDto): Response<Unit>

    suspend fun deleteUser(userId: Int): Response<Unit>
}