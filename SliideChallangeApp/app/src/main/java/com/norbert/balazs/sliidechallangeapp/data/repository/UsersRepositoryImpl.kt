package com.norbert.balazs.sliidechallangeapp.data.repository

import com.norbert.balazs.sliidechallangeapp.data.remote.UsersApi
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.NewUserDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class UsersRepositoryImpl @Inject constructor(
    @Named("UserApi") private val usersApi: UsersApi
) : UserRepository {

    override suspend fun getUsers(): List<UserDto> {
        return usersApi.getUsers()
    }

    override suspend fun createUser(user: NewUserDto): Response<Unit> {
        return usersApi.createUser(user)
    }

    override suspend fun deleteUser(userId: Int): Response<Unit> {
        return usersApi.deleteUser(userId)
    }
}