package com.norbert.balazs.sliidechallangeapp.presentation

import app.cash.turbine.test
import com.norbert.balazs.sliidechallangeapp.TestDispatcher
import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.data.remote.UsersApi
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.NewUserDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.UserDto
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.toUser
import com.norbert.balazs.sliidechallangeapp.data.repository.UsersRepositoryImpl
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import com.norbert.balazs.sliidechallangeapp.domain.use_case.CreateUserUseCase
import com.norbert.balazs.sliidechallangeapp.domain.use_case.DeleteUserUseCase
import com.norbert.balazs.sliidechallangeapp.domain.use_case.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() = runBlocking {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = MainViewModel(
            getDispatcherProvider(),
            getUserUseCase(),
            getCreateUserUseCase(),
            getDeleteUserUseCase()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() = runBlocking {
        assertEquals(viewModel.isFailed.value, false)
        assertEquals(viewModel.isLoading.value, false)
        assertEquals(viewModel.usersList.value, listOf(getUsersDto().toUser()))
    }

    @Test
    fun testCreateUser() = runBlocking {
        viewModel.createUser("name", "email", "gender", "status").test {
            val loading = awaitItem()
            assert(loading is Resource.Loading)
            assertNull(loading.data)
            assertNull(loading.message)
            val success = awaitItem()
            assert(success is Resource.Success)
            assertNull(loading.data)
            assertNull(loading.message)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testDeleteUser() = runBlocking {
        viewModel.deleteUser(getUserIdToDelete()).test {
            val loading = awaitItem()
            assert(loading is Resource.Loading)
            assertNull(loading.data)
            assertNull(loading.message)
            val success = awaitItem()
            assert(success is Resource.Success)
            assertNull(loading.data)
            assertNull(loading.message)
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun getDispatcherProvider() = TestDispatcher()

    private suspend fun getDeleteUserUseCase(): DeleteUserUseCase {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val userId = getUserIdToDelete()

        Mockito.`when`(mockApi.deleteUser(userId))
            .thenReturn(Response.success(204, Unit))

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        return DeleteUserUseCase(userRepository)
    }

    private suspend fun getCreateUserUseCase(): CreateUserUseCase {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val newUserDto = getNewUserDto()

        Mockito.`when`(mockApi.createUser(newUserDto))
            .thenReturn(Response.success(201, Unit))

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        return CreateUserUseCase(userRepository)
    }

    private suspend fun getUserUseCase(): GetUsersUseCase {
        val mockApi = Mockito.mock(UsersApi::class.java)

        val userDto = getUsersDto()

        val usersDto = listOf(
            userDto
        )

        Mockito.`when`(mockApi.getUsers()).thenReturn(usersDto)

        val userRepository: UserRepository = UsersRepositoryImpl(
            mockApi
        )

        return GetUsersUseCase(userRepository)
    }

    private fun getUsersDto() = UserDto(
        "email",
        "gender",
        45,
        "name",
        "status"
    )

    private fun getNewUserDto() = NewUserDto(
        "name",
        "email",
        "gender",
        "status"
    )

    private fun getUserIdToDelete() = 123
}