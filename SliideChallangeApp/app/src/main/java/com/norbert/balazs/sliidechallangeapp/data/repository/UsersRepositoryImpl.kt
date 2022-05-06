package com.norbert.balazs.sliidechallangeapp.data.repository

import com.norbert.balazs.sliidechallangeapp.data.remote.UsersApi
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named

class UsersRepositoryImpl @Inject constructor(
    @Named("UserApi") private val usersApi: UsersApi
) : UserRepository {

    override suspend fun getUsers(): List<UserDto> {
        return usersApi.getUsers()
    }

    override suspend fun addUser() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }
}