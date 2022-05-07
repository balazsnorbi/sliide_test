package com.norbert.balazs.sliidechallangeapp.domain.repository

import com.norbert.balazs.sliidechallangeapp.data.remote.dto.DeleteUserResponseDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.NewUserDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto

interface UserRepository {

    suspend fun getUsers(): List<UserDto>

    suspend fun createUser(user: NewUserDto)

    suspend fun deleteUser(userId: Int)
}