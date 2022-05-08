package com.norbert.balazs.sliidechallangeapp.data.repository

import com.norbert.balazs.sliidechallangeapp.data.remote.UsersApi
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.NewUserDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class UsersRepositoryImplTest {

    @Test
    fun testRepository() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val list = listOf(
            UserDto(
                "email",
                "gender",
                5,
                "name",
                "status"
            )
        )

        val newUserDto = NewUserDto(
            "name",
            "email",
            "gender",
            "active"
        )

        Mockito.`when`(mockApi.getUsers()).thenReturn(list)
        Mockito.`when`(mockApi.deleteUser(5)).thenReturn(retrofit2.Response.success(Unit))
        Mockito.`when`(mockApi.createUser(newUserDto)).thenReturn(retrofit2.Response.success(Unit))

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val usersResponse = userRepository.getUsers()

        Assert.assertEquals(usersResponse.size, 1)
        Assert.assertEquals(usersResponse[0].name, "name")
        Assert.assertEquals(usersResponse[0].email, "email")
        Assert.assertEquals(usersResponse[0].gender, "gender")
        Assert.assertEquals(usersResponse[0].status, "status")
        Assert.assertEquals(usersResponse[0].id, 5)

        userRepository.deleteUser(5)

        Assert.assertTrue(true)

        userRepository.createUser(newUserDto)

        Assert.assertTrue(true)
    }
}