package com.norbert.balazs.sliidechallangeapp.domain.use_case

import app.cash.turbine.test
import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.data.remote.UsersApi
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.toUser
import com.norbert.balazs.sliidechallangeapp.data.repository.UsersRepositoryImpl
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito

class GetUsersUseCaseTest {

    @Test
    fun testGetUsersSucceeded() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val userDto = UserDto(
            "email",
            "gender",
            45,
            "name",
            "status"
        )

        val usersDto = listOf(
            userDto
        )

        val expectedUsers = listOf(userDto.toUser())

        Mockito.`when`(mockApi.getUsers()).thenReturn(usersDto)

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val useCase = GetUsersUseCase(userRepository)

        useCase.invoke().test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)
            val success = awaitItem()
            assertTrue(success is Resource.Success)
            assertTrue(expectedUsers == success.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testFail() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        Mockito.`when`(mockApi.getUsers()).thenReturn(null)

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val useCase = GetUsersUseCase(userRepository)

        useCase.invoke().test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)
            val error = awaitItem()
            assertTrue(error is Resource.Error)
            assertNull(error.data)
            cancelAndConsumeRemainingEvents()
        }
    }
}