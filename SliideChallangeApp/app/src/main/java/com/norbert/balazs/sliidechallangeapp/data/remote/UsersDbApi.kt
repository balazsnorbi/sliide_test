package com.norbert.balazs.sliidechallangeapp.data.remote

import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto
import retrofit2.http.GET

interface UsersDbApi {

    @GET("v2/users")
    suspend fun getUsers(): List<UserDto>
}