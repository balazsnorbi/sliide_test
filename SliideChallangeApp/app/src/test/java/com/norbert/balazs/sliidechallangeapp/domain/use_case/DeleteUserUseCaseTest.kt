package com.norbert.balazs.sliidechallangeapp.domain.use_case

import app.cash.turbine.test
import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.data.remote.UsersApi
import com.norbert.balazs.sliidechallangeapp.data.repository.UsersRepositoryImpl
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class DeleteUserUseCaseTest {

    @Test
    fun testDeleteSuccess() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val userId = 123

        Mockito.`when`(mockApi.deleteUser(userId))
            .thenReturn(Response.success(204, Unit))

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val useCase = DeleteUserUseCase(userRepository)

        useCase.invoke(userId).test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)
            val success = awaitItem()
            assertTrue(success is Resource.Success)
            assertNull(success.data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testDeleteSucceededWithDifferentCode() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val userId = 123

        Mockito.`when`(mockApi.deleteUser(userId))
            .thenReturn(Response.success(Unit))

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val useCase = DeleteUserUseCase(userRepository)

        useCase.invoke(userId).test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)
            val error = awaitItem()
            assertTrue(error is Resource.Error)
            assertEquals(error.message, "Call succeeded but something went wrong!")
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testDeleteFailed() = runBlocking {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val userId = 123

        Mockito.`when`(mockApi.deleteUser(userId))
            .thenReturn(null)

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        val useCase = DeleteUserUseCase(userRepository)

        useCase.invoke(userId).test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)
            val error = awaitItem()
            assertTrue(error is Resource.Error)
            assertEquals(error.message, "An unexpected error occurred")
            cancelAndConsumeRemainingEvents()
        }
    }
}