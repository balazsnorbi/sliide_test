package com.norbert.balazs.sliidechallangeapp.data.remote

import com.norbert.balazs.sliidechallangeapp.data.remote.dto.NewUserDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsersApi {

    @GET("v2/users")
    suspend fun getUsers(): List<UserDto>

    @POST("v2/users")
    suspend fun createUser(@Body newUser: NewUserDto)
}