package com.norbert.balazs.sliidechallangeapp.domain.use_case

import app.cash.turbine.test
import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.data.remote.UsersApi
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.NewUserDto
import com.norbert.balazs.sliidechallangeapp.data.repository.UsersRepositoryImpl
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

class CreateUserUseCaseTest {

    @Test
    fun testSuccess() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val newUserDto = NewUserDto(
            "name",
            "email",
            "gender",
            "status"
        )

        Mockito.`when`(mockApi.createUser(newUserDto))
            .thenReturn(retrofit2.Response.success(201, Unit))

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val useCase = CreateUserUseCase(userRepository)

        useCase.invoke("name", "email", "gender", "status").test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)
            val success = awaitItem()
            assertTrue(success is Resource.Success)
            assertNull(success.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testSuccessWithError() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val newUserDto = NewUserDto(
            "name",
            "email",
            "gender",
            "status"
        )

        Mockito.`when`(mockApi.createUser(newUserDto))
            .thenReturn(retrofit2.Response.success(Unit))

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val useCase = CreateUserUseCase(userRepository)

        useCase.invoke("name", "email", "gender", "status").test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)
            val error = awaitItem()
            assertTrue(error is Resource.Error)
            assertNull(error.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testApiFailed() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val newUserDto = NewUserDto(
            "name",
            "email",
            "gender",
            "status"
        )

        Mockito.`when`(mockApi.createUser(newUserDto))
            .thenReturn(null)

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val useCase = CreateUserUseCase(userRepository)

        useCase.invoke("name", "email", "gender", "status").test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)
            val error = awaitItem()
            assertTrue(error is Resource.Error)
            assertNull(error.data)
            assertEquals(error.message, "An unexpected error occurred")
            cancelAndConsumeRemainingEvents()
        }
    }
}